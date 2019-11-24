package com.toll.exercice.microservice.web.controller;

import com.toll.exercice.microservice.dao.BillingDao;
import com.toll.exercice.microservice.dao.CarSlotDao;
import com.toll.exercice.microservice.dao.CarSlotTypeDao;
import com.toll.exercice.microservice.model.Billing;
import com.toll.exercice.microservice.model.CarSlot;
import com.toll.exercice.microservice.model.CarSlotType;
import com.toll.exercice.microservice.service.BillingService;
import com.toll.exercice.microservice.service.CarSlotService;
import com.toll.exercice.microservice.service.CarSlotTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class SlotController {

    @Autowired
    private CarSlotTypeDao carSlotTypeDao;

    @Autowired
    private CarSlotDao carSlotDao;

    @Autowired
    private BillingDao billingDao;

    @RequestMapping(value = "/v1/carSlots/{type}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/v1/car/{id}/{type}", method = RequestMethod.POST)
    public ResponseEntity<UUID> parkCar(@PathVariable int id, @PathVariable String type) {
        CarSlotTypeService carSlotTypeService = new CarSlotTypeService();
        CarSlotService carSlotService = new CarSlotService();

        CarSlot carSlot = carSlotDao.findBySlotNumber(id);
        if (carSlot == null || !carSlotService.isFree(carSlot) || !carSlotTypeService.isAcceptedCar(carSlot.getSlotType(), type)) {
            return ResponseEntity.badRequest().build();
        }

        Billing bill = carSlotService.enter(carSlot, type);

        carSlotDao.save(carSlot);
        billingDao.save(bill);
        return ResponseEntity.ok(bill.getCarId());
    }

    @RequestMapping(value = "/v1/bill/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Billing> bill(@PathVariable UUID uuid) {
        CarSlotService carSlotService = new CarSlotService();

        CarSlot carSlot = carSlotDao.findByCarNumber(uuid);
        if (carSlot == null) {
            return ResponseEntity.noContent().build();
        }


        Billing bill = carSlotService.bill(carSlot, uuid);

        billingDao.save(bill);
        carSlotDao.save(carSlot);
        return ResponseEntity.ok(bill);
    }

    @RequestMapping(value = "/v1/car/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> takeCar(@PathVariable UUID uuid) {
        CarSlotService carSlotService = new CarSlotService();
        BillingService billingService = new BillingService();

        CarSlot carSlot = carSlotDao.findByCarNumber(uuid);
        Billing bill = carSlot.getBill();

        if (bill == null) {
            return ResponseEntity.notFound().build();
        }

        if (!billingService.isStillValid(bill, LocalDateTime.now())) {
            return ResponseEntity.status(403).build();
        }

        carSlotService.leave(carSlot);
        billingDao.delete(bill);
        carSlotDao.save(carSlot);
        return ResponseEntity.accepted().build();
    }
}
