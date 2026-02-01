package com.capstone2.book_store2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_item_model")
@Data
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private String title;
    private Double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private OrderModel order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId", insertable = false, updatable = false)
    private BookModel book;
}
