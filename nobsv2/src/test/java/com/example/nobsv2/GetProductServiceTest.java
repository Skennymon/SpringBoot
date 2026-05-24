package com.example.nobsv2;

import com.example.nobsv2.exceptions.ProductNotFoundException;
import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.GetProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetProductServiceTest {

    @Mock //what to mock the response of -> need this dependency to run the test
    private ProductRepository productRepository;

    @InjectMocks //the thing we are testing
    private GetProductService getProductService;

    @BeforeEach //things we need before the test runs to set up properly
    public void setup() {
        //initializes the repo & the service
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_product_exists_when_get_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Product Name");
        product.setDescription("Product Description which is at least 20 chars");
        product.setPrice(9.99);

        //Kinda confusing but this when is still technically setting stuff up. This basically tells the unit test what to return when it calls productRepository.findById(1) since we aren't actually interacting with the database here.
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        //When
        ResponseEntity<ProductDTO> response = getProductService.execute(1);

        //Then
        assertEquals(ResponseEntity.ok(new ProductDTO(product)), response);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void given_product_does_not_exist_when_get_product_service_throw_product_not_found_exception() {
        //Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ProductNotFoundException.class, () -> getProductService.execute(1));
        verify(productRepository, times(1)).findById(1);
    }
}
