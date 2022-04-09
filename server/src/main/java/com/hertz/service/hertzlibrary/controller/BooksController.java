package com.hertz.service.hertzlibrary.controller;

import com.hertz.library.api.model.BooksResponse;
import com.hertz.library.api.model.Category;
import com.hertz.service.hertzlibrary.model.BookBo;
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

import static com.hertz.service.hertzlibrary.util.CollectionsUtils.convert;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "library/books")
public class BooksController {

    private final BooksService booksService;

    @GetMapping
    public ResponseEntity<BooksResponse> getBooks() {
        var bookBos = booksService.getAllBooks();
        var books = convert(bookBos, BookBo::toBook);

        return ok(new BooksResponse(books));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<BooksResponse> getBooksInCategory(
            @PathVariable("category") final Category category
    ) {

        var bookBos = booksService.getBooksInCategory(category);
        var books = convert(bookBos, BookBo::toBook);

        return ok(new BooksResponse(books));
    }

    @PostMapping
    public ResponseEntity<BookBo> addNewBook(@RequestBody final BookBo book) {
        return ok(booksService.addBookToLibrary(book));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> removeBook(
            @PathVariable("title") final String title
    ) {
        booksService.removeBookFromLibrary(title);
        return noContent().build();
    }

}