package com.pranay.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pranay.model.Products;
import com.pranay.service.CatogeroyService;
import com.pranay.service.ProductsService;

@RestController
@RequestMapping(path = "/api")
public class ProductsController {

	@Autowired
	private CatogeroyService cs;

	@Autowired
	private ProductsService ps;

	// METHOD TO ADD NEW PRODUCT
	@PostMapping("/products/{id}")
	public ResponseEntity<?> addNewProduct(@PathVariable Integer id, @RequestBody Products products, BindingResult br) {
		if (br.hasErrors()) {
			List<String> errors = br.getFieldErrors().stream().map(error -> {
				return error.getField() + "-" + error.getDefaultMessage();
			}).collect(Collectors.toList());
			String errorMessage = String.join(",", errors);
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
		} else {
			this.cs.addProduct(id, products);
			return new ResponseEntity<>("Product details added successfully", HttpStatus.OK);
		}
	}

	// METHOD TO GET ALL PRODUCTS
	@GetMapping("/products")
	public ResponseEntity<?> getAll(@RequestParam Integer page) {
		return new ResponseEntity<>(this.ps.getAllProducts(page), HttpStatus.OK);
	}

	// METHOD TO GET PRODUCTS BY ID
	@GetMapping("/products/{id}")
	public ResponseEntity<?> getProductsById(@PathVariable Integer id) {
		return new ResponseEntity<>(this.ps.getProductsyById(id), HttpStatus.OK);
	}

	// METHOD TO UPDATE PRODUCTS BY ID
	@PutMapping("products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Products products) {
		return new ResponseEntity<>(this.ps.updateProduct(id, products), HttpStatus.OK);
	}

	// METHOD TO DELETE PRODUCTS BY ID
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deletepProducts(@PathVariable Integer id) {
		Products exists = this.ps.getProductsyById(id);
		if (exists == null) {
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		} else {
			this.ps.deleteProductsById(id);
			return new ResponseEntity<>("Product details deleted successfully", HttpStatus.OK);
		}
	}

}
