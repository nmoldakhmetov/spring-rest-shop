package com.nursultan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;

    @Column(name = "purchase_data")
    private LocalDateTime purchaseData;

    @Column(name = "purchase_price")
    private int priceAtPurchase;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Purchase(LocalDateTime purchaseData, int priceAtPurchase, User user, Item item) {
        this.purchaseData = purchaseData;
        this.priceAtPurchase = priceAtPurchase;
        this.user = user;
        this.item = item;
    }

}
