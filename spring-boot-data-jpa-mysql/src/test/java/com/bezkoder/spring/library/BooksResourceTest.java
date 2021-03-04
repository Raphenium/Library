package com.bezkoder.spring.library;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bezkoder.spring.datajpa.SpringBootDataJpaApplication;
import com.bezkoder.spring.datajpa.controller.BookController;
import com.bezkoder.spring.datajpa.model.Book;
import com.bezkoder.spring.datajpa.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BooksResourceTest {

    private MockMvc mockMvc;
    
    @Autowired
    private BookController bookController;
    @Autowired
    private BookRepository booksRepo;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(booksRepo).build();
        ReflectionTestUtils.setField(booksRepo, "booksRepo", bookController);
    }

    @Test
    public void createBook() throws Exception{
        
        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void searchBooksByTitleTest() throws Exception {
        Book book = new Book("isbn2","Test2","Test Author","","","","","");
        booksRepo.save(book);
        MvcResult result = mockMvc.perform(get("/api/books?title=test2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("Test2")))
                .andReturn();
    }

    @Test
    public void getBooksTest() throws Exception {
        Book book1 = new Book("isbn3","Test3","Test Author","","","","","");
        booksRepo.save(book1);
        Book book2 = new Book("isbn4","Test4","Test Author","","","","","");
        booksRepo.save(book2);
        MvcResult result = mockMvc.perform(get("/api/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", hasItems("Test4", "Test3")))
                .andReturn();
    }
}

