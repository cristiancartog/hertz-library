package com.hertz.service.hertzlibrary.controller;

import com.hertz.library.api.model.BookBorrowRequest;
import com.hertz.library.api.model.BookReturnRequest;
import com.hertz.library.api.model.BooksResponse;
import com.hertz.service.hertzlibrary.model.BookBo;
import com.hertz.service.hertzlibrary.service.MembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hertz.service.hertzlibrary.util.CollectionsUtils.convert;
import static com.hertz.service.hertzlibrary.util.TokenDecoderUtil.userName;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("library/members")
public class MembersController {

    private final MembersService membersService;

    @PostMapping("/books/borrowings")
    public ResponseEntity<BooksResponse> borrowBook(
            @RequestHeader("token") final String token,
            @RequestBody final BookBorrowRequest borrowRequest
    ) {

        var memberName = userName(token);
        var bookBos = membersService.borrowBook(memberName, borrowRequest.getTitle());
        var books = convert(bookBos, BookBo::toBook);

        return ok(new BooksResponse(books));
    }

    @PostMapping("/books/returns")
    public ResponseEntity<BooksResponse> returnBook(
            @RequestHeader("token") final String token,
            @RequestBody final BookReturnRequest returnRequest
    ) {

        var memberName = userName(token);
        var bookBos = membersService.returnBook(memberName, returnRequest.getTitle());
        var books = convert(bookBos, BookBo::toBook);

        return ok(new BooksResponse(books));
    }

}
