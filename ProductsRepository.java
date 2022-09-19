package com.pranay.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pranay.model.Products;

@Repository
public interface ProductsRepository extends PagingAndSortingRepository<Products, Integer> {

}
