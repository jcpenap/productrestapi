package com.bharath.springweb.controllers;

import com.bharath.springweb.entities.Product;
import com.bharath.springweb.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository repository;

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
    public Product createProducts(@RequestBody  Product product) {
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
}
