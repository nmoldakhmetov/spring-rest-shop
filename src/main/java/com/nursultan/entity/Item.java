package com.nursultan.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name= "item_name")
    private String itemName;

    @Column(name = "price")
    private int price;

    @Column(name = "item_quantity")
    private int quantity;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<Purchase> purchases = new ArrayList<>();



}
