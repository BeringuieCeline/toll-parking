/**
 * Rest controller, entry point of the micro-service, contains interface to access to the service
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.web.controller;

import com.toll.exercice.microservice.dao.BillDao;
import com.toll.exercice.microservice.dao.CarSlotDao;
import com.toll.exercice.microservice.dao.CarSlotTypeDao;
import com.toll.exercice.microservice.model.Bill;
import com.toll.exercice.microservice.model.CarSlot;
import com.toll.exercice.microservice.model.CarSlotType;
import com.toll.exercice.microservice.service.BillService;
import com.toll.exercice.microservice.service.CarSlotService;
import com.toll.exercice.microservice.service.CarSlotTypeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/**
 * Controller that contain jnterface definition
 */
@RestController
@RequestMapping("/v1")
@Api(tags = {"Toll Parking API"})
@SwaggerDefinition(tags = {
        @Tag(name = "Toll Parking API", description = "Operations to access to a car slot")
})
public class SlotController {

    @Autowired
    private CarSlotTypeDao carSlotTypeDao;

    @Autowired
    private CarSlotDao carSlotDao;

    @Autowired
    private BillDao billDao;

    @GetMapping(value = "/carSlots/{type}")
    @ApiOperation(value = "View a list of available car slots for a specific type", response = CarSlot.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Wrong type specified"),
            @ApiResponse(code = 204, message = "No free slot found")
    }
    )
    public ResponseEntity<List<CarSlot>> carSlots(@PathVariable String type) {
        CarSlotType carSlotType = carSlotTypeDao.findByTypeName(type);
        if (carSlotType == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CarSlot> slots = carSlotDao.findBySlotTypeAndCarNumberIsNullOrderBySlotNumberAsc(carSlotType);

        if (slots == null || slots.size() == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(slots);
    }

    @PostMapping(value = "/car/{id}/{type}")
    @ApiOperation(value = "put a car in a specific slot", response = UUID.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car successfully parked"),
            @ApiResponse(code = 400, message = "Slot not exist or not free, or not correct type")
    }
    )
    public ResponseEntity<UUID> parkCar(@PathVariable int id, @PathVariable String type) {
        CarSlotTypeService carSlotTypeService = new CarSlotTypeService();
        CarSlotService carSlotService = new CarSlotService();

        CarSlot carSlot = carSlotDao.findBySlotNumber(id);
        if (carSlot == null || !carSlotService.isFree(carSlot) || !carSlotTypeService.isAcceptedCar(carSlot.getSlotType(), type)) {
            return ResponseEntity.badRequest().build();
        }

        Bill bill = carSlotService.enter(carSlot);

        carSlotDao.save(carSlot);
        billDao.save(bill);
        return ResponseEntity.ok(bill.getCarId());
    }

    @GetMapping(value = "/bill/{uuid}")
    @ApiOperation(value = "Retreive the bill before leaving the car slot", response = Bill.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved bill"),
            @ApiResponse(code = 204, message = "Car not found")
    }
    )
    public ResponseEntity<Bill> bill(@PathVariable UUID uuid) {
        CarSlotService carSlotService = new CarSlotService();

        CarSlot carSlot = carSlotDao.findByCarNumber(uuid);
        if (carSlot == null) {
            return ResponseEntity.noContent().build();
        }

        Bill bill = carSlotService.bill(carSlot);

        billDao.save(bill);
        carSlotDao.save(carSlot);
        return ResponseEntity.ok(bill);
    }

    @DeleteMapping(value = "/car/{uuid}")
    @ApiOperation(value = "Remove car from car slot", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car successfully remove from car slot"),
            @ApiResponse(code = 204, message = "Car not found"),
            @ApiResponse(code = 403, message = "Bill no more valid, you should re-bill")
    }
    )
    public ResponseEntity<Void> takeCar(@PathVariable UUID uuid) {
        CarSlotService carSlotService = new CarSlotService();
        BillService billingService = new BillService();

        CarSlot carSlot = carSlotDao.findByCarNumber(uuid);

        if (carSlot == null) {
            return ResponseEntity.notFound().build();
        }
        Bill bill = carSlot.getBill();

        if (!billingService.isStillValid(bill, LocalDateTime.now())) {
            return ResponseEntity.status(403).build();
        }

        carSlotService.leave(carSlot);
        billDao.delete(bill);
        carSlotDao.save(carSlot);
        return ResponseEntity.accepted().build();
    }
}
