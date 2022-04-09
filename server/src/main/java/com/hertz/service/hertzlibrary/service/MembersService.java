package com.hertz.service.hertzlibrary.service;

import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.model.MemberBo;
import com.hertz.service.hertzlibrary.repository.BooksRepository;
import com.hertz.service.hertzlibrary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembersService {

    @Value("${max-books-per-member}")
    private int maxBooksPerMember;

    private final MemberRepository memberRepository;
    private final BooksRepository booksRepository;

    public MemberBo rentBook(final String name, final String title) {
        var book = getBook(title);
        var member = getMember(name);

        if (canRentNewBook(member, book)) {
            book.setRenterName(member.getName());
            member.addBook(book);

            return member;
        } else {
            throw new IllegalStateException("Member has already rented the maximum amount of books");
        }
    }

    private boolean canRentNewBook(final MemberBo member, final BookBo book) {
        return book.getRenterName() == null && member.bookCount() < maxBooksPerMember;
    }

    public MemberBo returnBook(final String name, final String title) {
        var book = getBook(title);
        var member = getMember(name);

        var bookRemoved = member.removeBook(book);
        if (!bookRemoved) {
            throw new IllegalArgumentException("The book had not been borrowed by this member");
        }

        book.setRenterName(null);

        return member;
    }

    private BookBo getBook(final String title) {
        var bookOpt = booksRepository.findBook(title);

        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }

        return bookOpt.get();
    }

    private MemberBo getMember(final String name) {
        var memberOpt = memberRepository.findMember(name);
        if (memberOpt.isEmpty()) {
            throw new IllegalArgumentException("Member not found");
        }
        return memberOpt.get();
    }

}
