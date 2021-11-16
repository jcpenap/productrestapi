package com.bharath.springweb;

import com.bharath.springweb.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExploringSpringWebAndSpringBootApplicationTests {

	@Value("${productrestapi.services.url}")
	private String baseURL;

	@Test
	void testGetProduct() {
		System.out.println(baseURL);
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(baseURL + "7", Product.class);
		assertNotNull(product);
		assertEquals("Iphone", product.getName());
	}

	@Test
	void testCreateProduct() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = new Product();
		product.setName("Samsung Mobile");
		product.setDescription("It's owesome");
		product.setPrice(4000);
		Product newProduct = restTemplate.postForObject(baseURL, product, Product.class);
		assertNotNull(newProduct);
		assertNotNull(newProduct.getId());
		assertEquals("Samsung Mobile", newProduct.getName());
	}

	@Test
	void testUpdateProduct() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(baseURL + "7", Product.class);
		product.setPrice(5000);
		restTemplate.put(baseURL, product);
	}
}
