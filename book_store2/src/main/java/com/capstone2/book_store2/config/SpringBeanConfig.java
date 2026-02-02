package com.capstone2.book_store2.config;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Model.CategoryModel;
import com.capstone2.book_store2.Repository.BookRepository;
import com.capstone2.book_store2.Repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class SpringBeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // add categories and books if DB is empty
    @Bean
    public CommandLineRunner dataLoader(CategoryRepository catRepo, BookRepository bookRepo) {
        return args -> {
            if (catRepo.count() == 0) {
                CategoryModel fiction = new CategoryModel(1, "Fiction");
                CategoryModel nonf = new CategoryModel(2, "Non-fiction");
                CategoryModel science = new CategoryModel(3, "Science");
                catRepo.saveAll(List.of(fiction, nonf, science));
            }

            if (bookRepo.count() == 0) {
                var cats = catRepo.findAll();
                CategoryModel fiction = cats.stream().filter(c -> "Fiction".equals(c.getName())).findFirst().orElse(null);
                CategoryModel nonf = cats.stream().filter(c -> "Non-fiction".equals(c.getName())).findFirst().orElse(null);
                CategoryModel science = cats.stream().filter(c -> "Science".equals(c.getName())).findFirst().orElse(null);

                bookRepo.saveAll(List.of(
                        new BookModel(null, "The Great Gatsby", "F. Scott Fitzgerald", 10.99, "Classic novel", fiction),
                        new BookModel(null, "Sapiens", "Yuval Noah Harari", 15.99, "A brief history of humankind", nonf),
                        new BookModel(null, "Brief Answers to the Big Questions", "Stephen Hawking", 12.50, "Science essays", science)
                ));
            }
        };
    }

}
