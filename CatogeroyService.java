package com.pranay.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pranay.model.Category;
import com.pranay.model.Products;
import com.pranay.repository.CategoryRepository;
import com.pranay.repository.ProductsRepository;

@Service
public class CatogeroyService {

	@Autowired
	private CategoryRepository cr;

	@Autowired
	private ProductsRepository pr;

	// 1) METHOD TO ADD NEW CATEGORY
	public Category addNewCategory(Category category) {
		return this.cr.save(category);
	}

	// METHOD TO GET PAGINATED CATEGORIES
	public List<Category> getAllCatogries(Integer page) {
		Pageable paging = PageRequest.ofSize(page);
		Page<Category> pagedResult = this.cr.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Category>();
		}
	}

	// METHOD TO GET CATEGORY BY ID
	public Category getCategoryById(Integer id) {
		return this.cr.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found !");
		});
	}

	// METHOD TO UPDATE CATEGORY BY ID
	public Category updateCategoryById(Integer id, Category category) {
		this.cr.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		});
		category.setId(id);
		return this.cr.save(category);
	}

	// METHOD TO DELETE CATEGORY BY ID
	public void deleteCategoryById(Integer id) {
		this.cr.deleteById(id);
	}

	// METHOD TO ADD NEW PRODUCT
	public Products addProduct(Integer id, Products products) {
		Category foundCategory = this.getCategoryById(id);
		products.setCategory(foundCategory);
		Products savedProduct = this.pr.save(products);
		return savedProduct;
	}
	

	// METHOD TO GET ALL PRODUCTS
	public List<Products> getProducts(Integer id) {
		Category foundCategory = this.getCategoryById(id);
		return foundCategory.getProducts();
	}

}
