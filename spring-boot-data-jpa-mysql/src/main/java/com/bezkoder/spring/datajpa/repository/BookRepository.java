package com.bezkoder.spring.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bezkoder.spring.datajpa.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitleContaining(String title);
}
