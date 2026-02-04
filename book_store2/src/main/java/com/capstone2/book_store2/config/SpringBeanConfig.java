package com.capstone2.book_store2.config;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Model.CategoryModel;
import com.capstone2.book_store2.Repository.BookRepository;
import com.capstone2.book_store2.Repository.CategoryRepository;
import com.capstone2.book_store2.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            // Categories
            if (catRepo.count() == 0) {
                List<CategoryModel> categories = List.of(
                        new CategoryModel(null, "Fiction"),
                        new CategoryModel(null, "Non-fiction"),
                        new CategoryModel(null, "Science"),
                        new CategoryModel(null, "Romance"),
                        new CategoryModel(null, "Thrill"),
                        new CategoryModel(null, "Adventure")
                );
                catRepo.saveAll(categories);
            }

            //Books
            if (bookRepo.count() == 0) {
                // Fetch categories from DB
                var cats = catRepo.findAll();

                CategoryModel fiction = cats.stream().filter(c -> "Fiction".equals(c.getName())).findFirst().orElse(null);
                CategoryModel nonf = cats.stream().filter(c -> "Non-fiction".equals(c.getName())).findFirst().orElse(null);
                CategoryModel science = cats.stream().filter(c -> "Science".equals(c.getName())).findFirst().orElse(null);
                CategoryModel romance = cats.stream().filter(c -> "Romance".equals(c.getName())).findFirst().orElse(null);
                CategoryModel thrill = cats.stream().filter(c -> "Thrill".equals(c.getName())).findFirst().orElse(null);
                CategoryModel adventure = cats.stream().filter(c -> "Adventure".equals(c.getName())).findFirst().orElse(null);

                bookRepo.saveAll(List.of(
                        new BookModel(null, "The Great Gatsby", "F. Scott Fitzgerald", 150.99,
                                "A classic novel set in the Roaring Twenties, following the mysterious millionaire Jay Gatsby and his obsessive love for Daisy Buchanan, exploring themes of wealth, ambition, and the American Dream.", fiction),
                        new BookModel(null, "Sapiens", "Yuval Noah Harari", 250.99,
                                "A thought-provoking exploration of the history of our species, from the emergence of Homo sapiens to modern society, examining how biology, culture, and technology shaped humanity.", nonf),
                        new BookModel(null, "Brief Answers to the Big Questions", "Stephen Hawking", 120.50,
                                "A collection of insights from the world-renowned physicist addressing fundamental questions about the universe, the future of humanity, and our place in the cosmos.", science),
                        new BookModel(null, "Pride and Prejudice", "Jane Austen", 120.50,
                                "A classic novel about love, social class, and misunderstandings between Elizabeth Bennet and Mr. Darcy in 19th-century England.", romance),
                        new BookModel(null, "Gone Girl", "Gillian Flynn", 240.50,
                                "A dark psychological thriller about a husband who becomes the prime suspect when his wife mysteriously disappears on their fifth wedding anniversary.", thrill),
                        new BookModel(null, "The Hobbit", "J.R.R. Tolkien", 310.50,
                                "The epic journey of Bilbo Baggins, a hobbit who embarks on a quest with dwarves to reclaim their homeland from the dragon Smaug.", adventure)
                ));
            }
        };
    }

}