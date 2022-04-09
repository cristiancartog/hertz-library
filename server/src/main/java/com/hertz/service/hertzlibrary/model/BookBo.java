package com.hertz.service.hertzlibrary.model;

import com.hertz.library.api.model.Book;
import com.hertz.library.api.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class BookBo {

    private String title;
    private String author;
    private List<Category> categories;

    @Setter
    private String renterName;

    public static BookBo ofBook(final Book book) {
        return BookBo.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .categories(book.getCategories())
                .build();
    }

    public Book toBook() {
        var book = new Book();

        book.setAuthor(author);
        book.setTitle(title);
        book.setCategories(categories);

        return book;
    }

}
