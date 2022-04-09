package com.hertz.service.hertzlibrary.repository;

import com.hertz.library.api.model.Category;
import com.hertz.service.hertzlibrary.model.BookBo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hertz.library.api.model.Category.ADVENTURE;
import static com.hertz.library.api.model.Category.DYSTOPIAN;
import static com.hertz.library.api.model.Category.HORROR;
import static com.hertz.library.api.model.Category.ROMANCE;
import static com.hertz.library.api.model.Category.SCIENCE_FICTION;
import static java.util.stream.Collectors.toList;

/**
 * Replacement for an actual repository.
 * To be read as
 * public class BooksRepository implements CrudRepository<Book, String>
 */
@Service
public class BooksRepository {

    private List<BookBo> books; // the books "table"

    @PostConstruct
    public void init() { // initial data for easier manual testing
        books = new ArrayList<>();
        books.add(BookBo.builder().title("Introduction to Algorithms").author("Thomas Cormen").categories(List.of(HORROR)).build());
        books.add(BookBo.builder().title("1984").author("George Orwell").categories(List.of(SCIENCE_FICTION, DYSTOPIAN)).build());
        books.add(BookBo.builder().title("The Magus").author("John Fowles").categories(List.of(ROMANCE, ADVENTURE)).build());

        books.add(BookBo.builder().title("b1").author("author1").categories(List.of(ADVENTURE, ROMANCE)).build());
        books.add(BookBo.builder().title("b2").author("author2").categories(List.of(ROMANCE, ADVENTURE)).build());
        books.add(BookBo.builder().title("b3").author("author3").categories(List.of(ROMANCE, DYSTOPIAN, HORROR)).build());
        books.add(BookBo.builder().title("b4").author("author4").categories(List.of(HORROR)).build());
        books.add(BookBo.builder().title("b5").author("author5").categories(List.of(ROMANCE)).build());
        books.add(BookBo.builder().title("b6").author("author6").categories(List.of(DYSTOPIAN, SCIENCE_FICTION)).build());
    }

    public List<BookBo> allBooks() {
        return new ArrayList<>(books);
    }

    public List<BookBo> findBooksInCategory(final Category category) {
        return books
                .stream()
                .filter(book -> book.getCategories().contains(category))
                .collect(toList());
    }

    public Optional<BookBo> findBook(final String title) {
        return books
                .stream()
                .filter(book -> Objects.equals(title, book.getTitle()))
                .findFirst();
    }

    public BookBo save(final BookBo book) {
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

    public void deleteById(final String title) {
        books.removeIf(b -> Objects.equals(b.getTitle(), title));
    }

}
