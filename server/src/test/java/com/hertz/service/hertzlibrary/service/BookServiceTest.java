package com.hertz.service.hertzlibrary.service;

import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static com.hertz.library.api.model.Category.FANTASY;
import static com.hertz.library.api.model.Category.ROMANCE;
import static com.hertz.library.api.model.Category.SCIENCE_FICTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {BooksService.class, BooksRepository.class})
class BookServiceTest {

    @Autowired
    private BooksService booksService;

    @Autowired
    private BooksRepository booksRepository;

    @BeforeEach
    void setUp() {
        booksRepository.init();
    }

    @Test
    void testGettingBooksByCategory() {
        var book1 = BookBo.builder().author("a1").title("t1").categories(List.of(FANTASY, SCIENCE_FICTION)).build();
        var book2 = BookBo.builder().author("a2").title("t2").categories(List.of(SCIENCE_FICTION)).build();

        booksService.addBookToLibrary(book1);
        booksService.addBookToLibrary(book2);

        var sfBooks = booksService.getBooksInCategory(SCIENCE_FICTION);
        assertNotNull(sfBooks);
        assertEquals(2, sfBooks.size());

        var fantasyBooks = booksService.getBooksInCategory(FANTASY);
        assertNotNull(fantasyBooks);
        assertEquals(1, fantasyBooks.size());

        var romanceBooks = booksService.getBooksInCategory(ROMANCE);
        assertNotNull(romanceBooks);
        assertEquals(0, romanceBooks.size());

        var nullCategoryBooks = booksService.getBooksInCategory(null);
        assertNotNull(nullCategoryBooks);
        assertEquals(0, nullCategoryBooks.size());
    }

    @Test
    void testSavingAndDeletingBook() {
        var book1 = BookBo.builder().author("a1").title("t1").categories(List.of(FANTASY, SCIENCE_FICTION)).build();
        var book2 = BookBo.builder().author("a2").title("t2").categories(List.of(SCIENCE_FICTION)).build();

        booksService.addBookToLibrary(book1);
        booksService.addBookToLibrary(book2);

        var allBooks = booksService.getAllBooks();
        assertNotNull(allBooks);
        assertEquals(2, allBooks.size());
        assertTrue(allBooks.stream().anyMatch(book -> Objects.equals(book.getTitle(), "t1")));
        assertTrue(allBooks.stream().anyMatch(book -> Objects.equals(book.getTitle(), "t2")));

        booksService.removeBookFromLibrary("t2");
        allBooks = booksService.getAllBooks();
        assertNotNull(allBooks);
        assertEquals(1, allBooks.size());
        assertTrue(allBooks.stream().anyMatch(book -> Objects.equals(book.getTitle(), "t1")));
        assertFalse(allBooks.stream().anyMatch(book -> Objects.equals(book.getTitle(), "t2")));
    }
}
