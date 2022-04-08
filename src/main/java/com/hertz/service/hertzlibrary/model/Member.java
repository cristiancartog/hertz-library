package com.hertz.service.hertzlibrary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class Member {

    private String name; // for simplicity, we'll assume each person on the planet has a distinct name

    public Member(final String name) {
        this.name = name;
    }

    private Set<Book> books = new HashSet<>();

    public int bookCount() {
        return books.size();
    }

    public void addBook(final Book book) {
        books.add(book);
    }

    public boolean removeBook(final Book book) {
        return books.removeIf(
                b -> Objects.equals(book.getTitle(), b.getTitle())
        );
    }

}
