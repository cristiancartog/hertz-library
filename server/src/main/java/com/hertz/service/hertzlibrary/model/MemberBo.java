package com.hertz.service.hertzlibrary.model;

import com.hertz.library.api.model.Member;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.hertz.service.hertzlibrary.util.CollectionsUtils.convert;

@Data
@Builder
public class MemberBo {

    private String name; // for simplicity, we'll assume each person on the planet has a distinct name
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

    public static MemberBo ofMember(final Member member) {
        return MemberBo.builder()
                .name(member.getName())
                .books(new HashSet<>(convert(member.getBooks(), BookBo::ofBook)))
                .build();
    }

    public Member toMember() {
        var member = new Member();

        member.setName(name);
        member.setBooks(new ArrayList<>(convert(books, BookBo::toBook)));

        return member;
    }

}
