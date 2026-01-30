package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.categoryModel;
import com.capstone2.book_store2.Repository.categoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class categoryService {

    @Autowired
    private categoryRepository categoryRepository;

    public List<categoryModel> findAll() {
        return categoryRepository.findAll();
    }

    public categoryModel findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public categoryModel findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).orElse(null);
    }
}