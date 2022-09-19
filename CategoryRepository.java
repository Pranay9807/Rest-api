package com.pranay.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pranay.model.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
