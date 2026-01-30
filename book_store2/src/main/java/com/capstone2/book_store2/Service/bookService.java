package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Repository.bookRepository;
import com.capstone2.book_store2.Model.bookModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class bookService {


    private final bookRepository bookRepository;


    public List<bookModel> getAllBooks(){
            return bookRepository.findAll();
    }

    public List<bookModel> findByCategoryId(Integer categoryId) {
        if (categoryId == null || categoryId < 0) {
            return bookRepository.findAll();//DOUBLE CHECK
        }
            return bookRepository.findByCategory_Id(categoryId);
    }

    public List<bookModel> findByCategoryName(String name){
        if(name == null || name.isBlank() ||name.equalsIgnoreCase("ALL")){
            return bookRepository.findAll();
        }
        return bookRepository.findByCategory_NameIgnoreCase(name);
    }




}
