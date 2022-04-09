package com.hertz.service.hertzlibrary.controller;

import com.hertz.library.api.model.Book;
import com.hertz.library.api.model.BooksResponse;
import com.hertz.library.api.model.Category;
import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import static com.hertz.service.hertzlibrary.util.CollectionsUtils.convert;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/library/books")
@Validated
public class BooksController {

    private final BooksService booksService;

    @GetMapping
    public ResponseEntity<BooksResponse> getBooks(
            @RequestParam(required = false) final Category category
    ) {
        var bookBos = category == null
                ? booksService.getAllBooks()
                : booksService.getBooksInCategory(category);
        var books = convert(bookBos, BookBo::toBook);

        return ok(new BooksResponse(books));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid final Book book) {
        var bookBo = booksService.addBookToLibrary(BookBo.ofBook(book));

        return ok(bookBo.toBook());
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> removeBook(
            @PathVariable("title") @Size(min = 2) final String title
    ) {
        booksService.removeBookFromLibrary(title);

        return noContent().build();
    }

}
