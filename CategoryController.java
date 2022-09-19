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
import com.pranay.model.Category;
import com.pranay.service.CatogeroyService;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {

	@Autowired
	private CatogeroyService cs;

	// METHOD TO GET ALL CATEGORY
	@GetMapping("/categories")
	public ResponseEntity<?> getAll(@RequestParam Integer page) {
		return new ResponseEntity<>(this.cs.getAllCatogries(page), HttpStatus.OK);
	}

	// METHOD TO ADD NEW CATEGORY
	@PostMapping("/categories")
	public ResponseEntity<?> createCategory(@RequestBody Category category, BindingResult br) {
		if (br.hasErrors()) {
			List<String> errors = br.getFieldErrors().stream().map(error -> {
				return error.getField() + "-" + error.getDefaultMessage();
			}).collect(Collectors.toList());
			String errorMessage = String.join(",", errors);
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
		} else {
			this.cs.addNewCategory(category);
			return new ResponseEntity<>("New category added successfully", HttpStatus.OK);
		}
	}

	// METHOD TO GET CATEGORY BY ID
	@GetMapping("/categories/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
		return new ResponseEntity<>(this.cs.getCategoryById(id), HttpStatus.OK);
	}

	// METHOD TO UPDATE CATEGORY BY ID
	@PutMapping("/categories/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
		return new ResponseEntity<>(this.cs.updateCategoryById(id, category), HttpStatus.OK);
	}

	// METHOD TO DELETE CATEGORY BY ID
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		Category exists = this.cs.getCategoryById(id);
		if (exists == null) {
			return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
		} else {
			this.cs.deleteCategoryById(id);
			return new ResponseEntity<>("Category details deleted successfully", HttpStatus.OK);
		}
	}

}
