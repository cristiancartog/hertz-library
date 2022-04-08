package com.hertz.service.hertzlibrary.repository;

import com.hertz.service.hertzlibrary.model.Book;
import com.hertz.service.hertzlibrary.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hertz.service.hertzlibrary.model.Category.ADVENTURE;
import static com.hertz.service.hertzlibrary.model.Category.DYSTOPIAN;
import static com.hertz.service.hertzlibrary.model.Category.HORROR;
import static com.hertz.service.hertzlibrary.model.Category.ROMANCE;
import static com.hertz.service.hertzlibrary.model.Category.SCIENCE_FICTION;
import static java.util.stream.Collectors.toList;

/**
 * Replacement for an actual repository.
 * To be read as
 * public class BooksRepository implements CrudRepository<Book, String>
 */
@Service
public class BooksRepository {

    private List<Book> books; // the books "table"

    @PostConstruct
    public void init() { // initial data for easier manual testing
        books = new ArrayList<>();
        books.add(new Book("Introduction to Algorithms", "Thomas Cormen", List.of(HORROR), null));
        books.add(new Book("1984", "George Orwell", List.of(SCIENCE_FICTION, DYSTOPIAN), null));
        books.add(new Book("The Magus", "John Fowles", List.of(ROMANCE, ADVENTURE), null));

        books.add(new Book("b1", "author1", List.of(ADVENTURE, ROMANCE), null));
        books.add(new Book("b2", "author2", List.of(ROMANCE, ADVENTURE), null));
        books.add(new Book("b3", "author3", List.of(ROMANCE, DYSTOPIAN, HORROR), null));
        books.add(new Book("b4", "author4", List.of(HORROR), null));
        books.add(new Book("b5", "author5", List.of(ROMANCE), null));
    }

    public List<Book> allBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> findBooksInCategory(final Category category) {
        return books.stream()
                .filter(book -> book.getCategories().contains(category))
                .collect(toList());
    }

    public Optional<Book> findBook(final String title) {
        return books.stream()
                .filter(book -> Objects.equals(title, book.getTitle()))
                .findFirst();
    }

    public Book save(final Book book) {
        boolean bookExists = books.stream()
                .anyMatch(b -> Objects.equals(book.getTitle(), b.getTitle()));

        if (bookExists) {
            throw new IllegalArgumentException("Book is already in the library");
        }

        // after persisting a new record we would normally get the ID of that record
        // the persistence framework sets it on a new instance of the persisted object and returns that object
        // here, for simplicity, we'll just recycle the book provided as parameter
        books.add(book);

        return book;
    }

    public boolean isBookAvailable(final String title) {
        return books
                .stream()
                .filter(b -> Objects.equals(b.getTitle(), title))
                .anyMatch(b -> b.getRenterName() == null);
    }

    public void deleteById(final String title) {
        books.removeIf(b -> Objects.equals(b.getTitle(), title));
    }

}
