package com.nursultan.service;


import com.nursultan.dao.ItemRepository;
import com.nursultan.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> getAllItems() {
       return itemRepository.findAll();
    }

    public Optional<Item> getItemById(int id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);

    }


    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItemById(int id) {
        itemRepository.deleteById(id);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

}
