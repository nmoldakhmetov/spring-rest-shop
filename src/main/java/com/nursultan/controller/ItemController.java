package com.nursultan.controller;


import com.nursultan.dao.ItemRepository;
import com.nursultan.entity.Item;
import com.nursultan.service.ItemService;
import org.springframework.web.bind.annotation.*;


import com.nursultan.dao.UserRepository;
import com.nursultan.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping
    public Item addNew(@RequestBody Item item) {
        return itemService.createItem(item);
    }


    @GetMapping
    public List<Item> getAll() {
        return itemService.getAllItems();
    }


    @GetMapping("{id}")
    public Optional getById(@PathVariable int id) {
        return itemService.getItemById(id);
    }



    @PutMapping
    public Item update(@RequestBody Item item) {
        return itemService.updateItem(item);
    }

    @DeleteMapping
    public void delete(@RequestBody Item item) {
        itemService.deleteItem(item);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable int id) {
        itemService.deleteItemById(id);
    }
}
