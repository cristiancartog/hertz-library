package com.hertz.library.api.client;

import com.hertz.library.api.model.Book;
import com.hertz.library.api.model.BooksResponse;
import com.hertz.library.api.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class LibraryWebClient {

    public static final String BOOKS_PATH = "/library/books";
    public static final String BOOKS_DELETE_PATH = "/library/books/{title}";

    private WebClient webClient;

    public LibraryWebClient(final String baseUrl) {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public BooksResponse getBooks() {
        return getBooks(null);
    }

    public BooksResponse getBooks(final Category category) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BOOKS_PATH)
                        .queryParam("category", category != null ? category.name() : null)
                        .build()
                )
                .retrieve()
                .bodyToMono(BooksResponse.class)
                .block();
    }

    public Book createBook(final Book book) {
        return webClient.post()
                .uri(BOOKS_PATH)
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    public void removeBook(final String title) {
        webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(BOOKS_DELETE_PATH)
                        .build(title)
                )
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
