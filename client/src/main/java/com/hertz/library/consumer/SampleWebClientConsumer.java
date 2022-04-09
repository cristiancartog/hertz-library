package com.hertz.library.consumer;

import com.hertz.library.api.client.LibraryWebClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleWebClientConsumer {

    public static void main(String[] args) {
        var client = new LibraryWebClient("http://localhost:8080");
        var books = client.getBooks();

        log.info("{}", books);
    }

}
