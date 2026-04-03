package com.example.nobsv2.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // maps java class to mySQL
@Data
@Table(name="product")

public class Product {
    @Id // the primary key for the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generates id

    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

}
