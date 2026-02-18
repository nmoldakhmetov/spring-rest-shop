package com.nursultan.controller;


import com.nursultan.entity.Item;
import com.nursultan.entity.Purchase;
import com.nursultan.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    public Purchase addNew(@RequestBody Purchase purchase) {
        return purchaseService.createPurchase(purchase);
    }


    @GetMapping
    public List<Purchase> getAll() {
        return purchaseService.getAllPurchases();
    }


    @GetMapping("{id}")
    public Optional getById(@PathVariable int id) {
        return purchaseService.getPurchaseById(id);
    }


    @PutMapping
    public Purchase update(@RequestBody Purchase purchase) {
        return purchaseService.updatePurchase(purchase);
    }

    @DeleteMapping
    public void delete(@RequestBody Purchase purchase) {
        purchaseService.deletePurchase(purchase);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable int id) {
        purchaseService.deletePurchaseById(id);
    }

    @PostMapping("{userId}/{itemId}")
    public void buyItem(@PathVariable int userId, @PathVariable int itemId) {
        purchaseService.buy(userId, itemId);
    }

    @GetMapping("/user/{id}")
    public List<Purchase> getAllUserPurchases(@PathVariable int id) {
        return purchaseService.findAllByUserId(id);
    }
}
