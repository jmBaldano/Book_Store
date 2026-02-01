package com.capstone2.book_store2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_item_model")
@Data
public class orderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer bookId;
    private String title;
    private Double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private orderModel order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId", insertable = false, updatable = false)
    private bookModel book;
}
