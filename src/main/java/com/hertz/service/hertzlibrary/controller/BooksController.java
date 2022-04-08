package com.hertz.service.hertzlibrary.controller;

import com.hertz.service.hertzlibrary.model.Book;
import com.hertz.service.hertzlibrary.model.Category;
import com.hertz.service.hertzlibrary.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "library/books")
public class BooksController {

    private final BooksService booksService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ok(booksService.getAllBooks());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getBooksInCategory(@PathVariable("category") final String categoryRepr) {

        var category = convertToCategory(categoryRepr);

        if (category != null) {
            return ok(booksService.getBooksInCategory(category));
        }

        return badRequest().body(null);
    }

    @PostMapping
    public ResponseEntity<Book> addNewBook(@RequestBody final Book book) {
        return ok(booksService.addBookToLibrary(book));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> removeBook(@PathVariable("title") final String title) {
        booksService.removeBookFromLibrary(title);
        return noContent().build();
    }

    private Category convertToCategory(final String categoryRepr) {
        try {
            return Category.valueOf(categoryRepr.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.debug("Category '{}' is not supported", categoryRepr);
        }

        return null;
    }

}
