package com.bharath.springweb.controllers;

import com.bharath.springweb.entities.Product;
import com.bharath.springweb.repositories.ProductRepository;
import io.swagger.annotations.ApiOperation;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository repository;

    @ApiOperation(value = "Retrieve all the products",
            notes = "All the producs",
            response = Product.class,
            responseContainer = "List",
            produces = "application/json")
    @RequestMapping(value = "/products/", method = RequestMethod.GET)
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @Cacheable("product-cache")
    public Product getProducts(@PathVariable("id") int id) {
        LOGGER.info("Finding product by ID: "+id);
        LOGGER.error("Finding product by ID: "+id);
        return repository.findById(id).get();
    }

    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public Product createProducts(@Valid @RequestBody  Product product) {
        return repository.save(product);
    }

    @RequestMapping(value = "/products/", method = RequestMethod.PUT)
    public Product updateProducts(@RequestBody Product product) {
        return repository.save(product);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @CacheEvict("product-cache")
    public void deleteProduct(@PathVariable("id") int id) {
        repository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
