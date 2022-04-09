package com.hertz.service.hertzlibrary.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
public class MemberBo {

    private String name; // for simplicity, assume each person on the planet has a distinct name

    @Builder.Default
    private Set<BookBo> books = new HashSet<>();

    public int bookCount() {
        return books.size();
    }

    public void addBook(final BookBo book) {
        books.add(book);
    }

    public boolean removeBook(final BookBo book) {
        return books.removeIf(
                b -> Objects.equals(book.getTitle(), b.getTitle())
        );
    }

}
