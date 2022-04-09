package com.hertz.service.hertzlibrary.service;

import com.hertz.library.api.model.Category;
import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public List<BookBo> getAllBooks() {
        return booksRepository.allBooks();
    }

    public List<BookBo> getBooksInCategory(final Category category) {
        return booksRepository.findBooksInCategory(category);
    }

    public BookBo addBookToLibrary(final BookBo book) {
        return booksRepository.save(book);
    }

    public void removeBookFromLibrary(final String title) {
        var bookOpt = booksRepository.findBook(title);

        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book was not found");
        }

        var book = bookOpt.get();

        if (book.getRenterName() != null) {
            throw new IllegalArgumentException("Book is currently lent, cannot be removed from library");
        }

        booksRepository.deleteById(title);
    }

}
