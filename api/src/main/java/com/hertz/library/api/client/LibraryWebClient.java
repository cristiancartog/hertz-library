package com.hertz.library.api.client;

import com.hertz.library.api.model.BooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class LibraryWebClient {

    private WebClient webClient;

    public LibraryWebClient(final String baseUrl) {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public BooksResponse getBooks() {
        return webClient.get()
                .uri("/library/books")
                .retrieve()
                .bodyToMono(BooksResponse.class)
                .block();
    }

}
