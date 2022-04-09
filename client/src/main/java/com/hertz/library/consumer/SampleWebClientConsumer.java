package com.hertz.library.consumer;

import com.hertz.library.api.client.LibraryWebClient;
import com.hertz.library.api.model.Book;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.hertz.library.api.model.Category.HORROR;

@Slf4j
public class SampleWebClientConsumer {

    // get this by configuration value injection
    public static final String BASE_URL = "http://localhost:8080";

    public static void main(String[] args) {
        var client = new LibraryWebClient(BASE_URL);

        log.info("");

        log.info("All books:");
        client.getBooks()
                .getBooks()
                .forEach(book -> log.info(book.getTitle()));
        log.info("");

        log.info("All HORROR books:");
        client.getBooks(HORROR)
                .getBooks()
                .forEach(book -> log.info(book.getTitle()));
        log.info("");

        log.info("Creating new book:");
        var book = new Book();
        book.setTitle("some-title");
        book.setAuthor("some-author");
        book.setCategories(List.of(HORROR));

        book = client.createBook(book);
        log.info("{}", book);
        log.info("");

        log.info("Deleting the 'some-title' book:");
        client.removeBook("some-title");
        log.info("Deleted");
    }

}
