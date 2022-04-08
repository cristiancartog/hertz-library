package com.hertz.service.hertzlibrary.service;

import com.hertz.service.hertzlibrary.model.Book;
import com.hertz.service.hertzlibrary.model.Category;
import com.hertz.service.hertzlibrary.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public List<Book> getAllBooks() {
        return booksRepository.allBooks();
    }

    public List<Book> getBooksInCategory(final Category category) {
        return booksRepository.findBooksInCategory(category);
    }

    public Book addBookToLibrary(final Book book) {
        return booksRepository.save(book);
    }

    public void removeBookFromLibrary(final String title) {
        var bookAvailableForRent = booksRepository.isBookAvailable(title);

        if (!bookAvailableForRent) {
            throw new IllegalArgumentException("Book is currently lent, cannot be removed from library");
        }

        booksRepository.deleteById(title);
    }

}
