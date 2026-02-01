package com.capstone2.book_store2.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class cartItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Integer bookId;
    private String title;
    private Double price;
    private int quantity;


}
