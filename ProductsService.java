package com.pranay.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.pranay.model.Category;
import com.pranay.model.Products;
import com.pranay.repository.ProductsRepository;

@Service
public class ProductsService {

	@Autowired
	private ProductsRepository pr;

	@Autowired
	private CatogeroyService cs;

	// METHOD TO GET ALL PRODUCTS
	public List<Products> getAllProducts(Integer page) {
		Pageable paging = PageRequest.ofSize(page);
		Page<Products> pagedResult = this.pr.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Products>();
		}
	}
	

	// METHOD TO GET PRODUCT BY ID
	public Products getProductsyById(Integer id) {
		return this.pr.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found !");
		});
	}

	// METHOD TO UPDATE PRODUCT BY ID
	public Products updateProduct(@PathVariable Integer id, @RequestBody Products requestedProduct) {
		return this.pr.findById(id).map(product -> {
			product.setName(requestedProduct.getName());
			product.setPrice(requestedProduct.getPrice());
			return this.pr.save(product);
		}).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		});
	}

	// METHOD TO DELETE PRODUCT BY ID
	public void deleteProductsById(Integer id) {
		this.pr.deleteById(id);
	}
	// METHOD TO ADD NEW PRODUCT
	public Products addProduct(Integer id, Products products) {
		Category foundCategory = this.cs.getCategoryById(id);
		products.setCategory(foundCategory);
		Products savedProduct = this.pr.save(products);
		return savedProduct;
	}
}
