package com.hertz.service.hertzlibrary.service;

import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.model.MemberBo;
import com.hertz.service.hertzlibrary.repository.BooksRepository;
import com.hertz.service.hertzlibrary.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MembersService.class)
class MemberServiceTest {

    @Autowired
    private MembersService membersService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private BooksRepository booksRepository;

    @Test
    void testSuccessfulBorrowingAndReturning() {
        var memberName = "aaa";
        var bookTitle1 = "t1";
        var bookTitle2 = "t2";

        var member = MemberBo.builder().name(memberName).build();
        var book1 = BookBo.builder().title(bookTitle1).build();
        var book2 = BookBo.builder().title(bookTitle2).build();

        when(memberRepository.findMember(memberName)).thenReturn(Optional.of(member));
        when(booksRepository.findBook(bookTitle1)).thenReturn(Optional.of(book1));
        when(booksRepository.findBook(bookTitle2)).thenReturn(Optional.of(book2));

        var books = membersService.borrowBook(memberName, bookTitle1);
        assertEquals(1, books.size());

        books = membersService.borrowBook(memberName, bookTitle2);
        assertEquals(2, books.size());

        books = membersService.returnBook(memberName, bookTitle2);
        assertEquals(1, books.size());

        books = membersService.returnBook(memberName, bookTitle1);
        assertEquals(0, books.size());
    }

    @Test
    void testLimitExceededOnBorrowing() {
        var memberName = "aaa";
        var bookTitle1 = "t1";
        var bookTitle2 = "t2";
        var bookTitle3 = "t3";
        var bookTitle4 = "t4";

        var book1 = BookBo.builder().title(bookTitle1).build();
        var book2 = BookBo.builder().title(bookTitle2).build();
        var book3 = BookBo.builder().title(bookTitle3).build();
        var book4 = BookBo.builder().title(bookTitle4).build();
        var member = MemberBo.builder().name(memberName).books(Set.of(book1, book2, book3)).build();

        when(memberRepository.findMember(memberName)).thenReturn(Optional.of(member));
        when(booksRepository.findBook(bookTitle1)).thenReturn(Optional.of(book1));
        when(booksRepository.findBook(bookTitle2)).thenReturn(Optional.of(book2));
        when(booksRepository.findBook(bookTitle3)).thenReturn(Optional.of(book3));
        when(booksRepository.findBook(bookTitle4)).thenReturn(Optional.of(book4));

        assertThrows(IllegalStateException.class, () -> membersService.borrowBook(memberName, bookTitle4));
    }

    @Test
    void testFailureToObtaineBorrowedBook() {
        var memberName = "aaa";
        var bookTitle = "t1";

        var book = BookBo.builder().title(bookTitle).renterName("someone-else").build();
        var member = MemberBo.builder().name(memberName).build();

        when(memberRepository.findMember(memberName)).thenReturn(Optional.of(member));
        when(booksRepository.findBook(bookTitle)).thenReturn(Optional.of(book));

        assertThrows(IllegalArgumentException.class, () -> membersService.borrowBook(memberName, bookTitle));
    }

}
