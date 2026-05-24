package com.example.nobsv2.product;

import com.example.nobsv2.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //spring data jpa
    List<Product> findByNameContaining(String name);

    //JPQL typically used for more complex queries
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword")
    List<Product> findByNameOrDescriptionContaining(@Param("keyword") String name);
}
