package com.nursultan.service;


import com.nursultan.dao.ItemRepository;
import com.nursultan.dao.PurchaseRepository;
import com.nursultan.dao.UserRepository;
import com.nursultan.entity.Item;
import com.nursultan.entity.Purchase;
import com.nursultan.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;


    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> getPurchaseById(int id) {
        return purchaseRepository.findById(id);
    }

    public Purchase createPurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public void deletePurchase(Purchase purchase) {
        purchaseRepository.delete(purchase);
    }

    public void deletePurchaseById(int id) {
        purchaseRepository.deleteById(id);
    }

    public Purchase updatePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
   }

    public List<Purchase> findAllByUserId (int id ) {
        return purchaseRepository.findAllByUserId(id);
    }

    @Transactional
    public void buy (int userId, int itemId) {

        User user = userRepository.findById(userId).orElseThrow(() ->  new RuntimeException("User not found" + userId));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found" + itemId));



        if (user.getBalance() - item.getPrice() >= 0 && item.getQuantity() > 0) {
            LocalDateTime localDate = LocalDateTime.now();
            Purchase purchase = new Purchase(localDate, item.getPrice(), user, item);

            user.setBalance(user.getBalance() - item.getPrice());
            item.setQuantity(item.getQuantity() - 1);

            userRepository.save(user);
            itemRepository.save(item);


            purchaseRepository.save(purchase);


        } else {
            throw new RuntimeException("You missed something");
        }
    }


}
