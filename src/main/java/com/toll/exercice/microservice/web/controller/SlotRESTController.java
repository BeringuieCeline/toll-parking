package com.toll.exercice.microservice.web.controller;
import com.toll.exercice.microservice.dao.BillingDao;
import com.toll.exercice.microservice.dao.CarSlotDao;
import com.toll.exercice.microservice.dao.CarSlotTypeDao;
import com.toll.exercice.microservice.models.Billing;
import com.toll.exercice.microservice.models.CarSlot;
import com.toll.exercice.microservice.models.CarSlotType;
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
public class SlotRESTController {

    @Autowired
    private CarSlotTypeDao carSlotTypeDao;

    @Autowired
    private CarSlotDao carSlotDao;

    @Autowired
    private BillingDao billingDao;

    @RequestMapping(value="v1/carSlots/{type}", method=RequestMethod.GET)
    public ResponseEntity<List<CarSlot>> carSlots(@PathVariable String type)
    {
        CarSlotType carSlotType = carSlotTypeDao.findByTypeName(type);
        if(carSlotType == null)
            return ResponseEntity.badRequest().build();

        List<CarSlot> slots = carSlotDao.findBySlotTypeAndCarNumberIsNullOrderBySlotNumberAsc(carSlotType);

        if(slots == null || slots.size() == 0)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(slots);
    }

    @RequestMapping(value="v1/parkCarSlot/{id}/{type}", method=RequestMethod.POST)
    public ResponseEntity<Billing> parkCarSlot(@PathVariable int id, @PathVariable String type)
    {
        CarSlot carSlot = carSlotDao.findBySlotNumber(id);
        if(carSlot == null || !carSlot.getSlotType().isAcceptedCar(type))
            return ResponseEntity.badRequest().build();

        Billing bill = carSlot.enter(type);

        carSlotDao.save(carSlot);
        billingDao.save(bill);
        return ResponseEntity.ok(bill);
    }

    @RequestMapping(value="v1/bill/{uuid}", method=RequestMethod.GET)
    public ResponseEntity<Billing> bill(@PathVariable UUID uuid)
    {
        CarSlot carSlot = carSlotDao.findByCarNumber(uuid);
        if(carSlot == null)
            return ResponseEntity.noContent().build();


        Billing bill = carSlot.bill(uuid);

        billingDao.save(bill);
        carSlotDao.save(carSlot);
        return ResponseEntity.ok(bill);
    }

    @RequestMapping(value="v1/takeBackCar/{uuid}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> takeBackCar(@PathVariable UUID uuid)
    {
        CarSlot carSlot = carSlotDao.findByCarNumber(uuid);
        Billing bill = carSlot.getBill();

        if(bill == null)
            return ResponseEntity.notFound().build();

        if(!bill.isStillValid(LocalDateTime.now()))
            return ResponseEntity.status(403).build();

        carSlot.leave();
        billingDao.delete(bill);
        carSlotDao.save(carSlot);
        return ResponseEntity.ok().build();
    }
}
