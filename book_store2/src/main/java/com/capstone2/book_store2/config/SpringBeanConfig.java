package com.capstone2.book_store2.config;

import com.capstone2.book_store2.Model.bookModel;
import com.capstone2.book_store2.Model.categoryModel;
import com.capstone2.book_store2.Repository.bookRepository;
import com.capstone2.book_store2.Repository.categoryRepository;
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

    // Simple data loader for beginners: seeds a few categories and books if DB is empty
    @Bean
    public CommandLineRunner dataLoader(categoryRepository catRepo, bookRepository bookRepo) {
        return args -> {
            if (catRepo.count() == 0) {
                categoryModel fiction = new categoryModel(1, "Fiction");
                categoryModel nonf = new categoryModel(2, "Non-fiction");
                categoryModel science = new categoryModel(3, "Science");
                catRepo.saveAll(List.of(fiction, nonf, science));
            }

            if (bookRepo.count() == 0) {
                var cats = catRepo.findAll();
                categoryModel fiction = cats.stream().filter(c -> "Fiction".equals(c.getName())).findFirst().orElse(null);
                categoryModel nonf = cats.stream().filter(c -> "Non-fiction".equals(c.getName())).findFirst().orElse(null);
                categoryModel science = cats.stream().filter(c -> "Science".equals(c.getName())).findFirst().orElse(null);

                bookRepo.saveAll(List.of(
                        new bookModel(null, "The Great Gatsby", "F. Scott Fitzgerald", 10.99, "Classic novel", fiction),
                        new bookModel(null, "Sapiens", "Yuval Noah Harari", 15.99, "A brief history of humankind", nonf),
                        new bookModel(null, "Brief Answers to the Big Questions", "Stephen Hawking", 12.50, "Science essays", science)
                ));
            }
        };
    }

}
