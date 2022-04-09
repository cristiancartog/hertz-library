package com.hertz.service.hertzlibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.service.BooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.hertz.library.api.model.Category.DYSTOPIAN;
import static com.hertz.library.api.model.Category.HORROR;
import static com.hertz.library.api.model.Category.ROMANCE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksController.class)
class BooksControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private BooksService booksService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGettingAllBooks() throws Exception {
        var book1 = BookBo.builder().title("b1").author("a1").categories(List.of(ROMANCE)).build();
        var book2 = BookBo.builder().title("b2").author("a2").categories(List.of(DYSTOPIAN, HORROR)).build();

        when(booksService.getAllBooks())
                .thenReturn(List.of(book1, book2));

        mockMvc.perform(
                        get("/library/books").accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(2)))
                .andExpect(jsonPath("$.books.[0].title", equalTo("b1")))
                .andExpect(jsonPath("$.books.[1].title", equalTo("b2")));
    }

    @Test
    void testGettingBooksInCategory() throws Exception {
        var book = BookBo.builder().title("b1").author("a1").categories(List.of(ROMANCE)).build();

        when(booksService.getAllBooks())
                .thenReturn(List.of(book));

        mockMvc.perform(
                        get("/library/books").param("cateogry", HORROR.name())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(1)))
                .andExpect(jsonPath("$.books.[0].title", equalTo("b1")));
    }

    @Test
    void testGettingBooksWithWrongCategory() throws Exception {
        mockMvc.perform(
                        get("/library/books").param("category", "asad")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testPostingBookSuccessfully() throws Exception {
        var book = BookBo.builder().title("b1").author("a1").categories(List.of(ROMANCE)).build();

        when(booksService.addBookToLibrary(any()))
                .thenReturn(book);

        mockMvc.perform(
                        post("/library/books")
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(book))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", equalTo("b1")));
    }

    @Test
    void testPostingBookWithNoAuthor() throws Exception {
        var book = BookBo.builder().title("b1").categories(List.of(ROMANCE)).build();

        mockMvc.perform(
                        post("/library/books")
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(book))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode", equalTo(BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", notNullValue()));
    }

    @Test
    void testPostingBookWithShortTitle() throws Exception {
        var book = BookBo.builder().title("b").author("a").categories(List.of(ROMANCE)).build();

        mockMvc.perform(
                        post("/library/books")
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(book))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode", equalTo(BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", notNullValue()));
    }

    @Test
    void testSuccessfulBookRemoval() throws Exception {
        mockMvc.perform(
                        delete("/library/books/b1")
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void testBookRemovalWithNonExistingBook() throws Exception {
        doThrow(new IllegalArgumentException("Book does not exist"))
                .when(booksService)
                .removeBookFromLibrary(any());

        mockMvc.perform(
                        delete("/library/books/b1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", equalTo(BAD_REQUEST.value())))
                .andExpect(jsonPath("$.reason", notNullValue()));
    }

}
