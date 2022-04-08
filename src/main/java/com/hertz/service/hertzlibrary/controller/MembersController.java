package com.hertz.service.hertzlibrary.controller;

import com.hertz.service.hertzlibrary.model.Member;
import com.hertz.service.hertzlibrary.service.MembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hertz.service.hertzlibrary.TokenDecoderUtil.userName;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("library/members")
public class MembersController {

    private final MembersService membersService;

    @PostMapping("/books/{title}")
    public ResponseEntity<Member> rentBook(
            @RequestHeader("token") final String token,
            @PathVariable("title") final String title) {
        var memberName = userName(token);

        return ok(membersService.rentBook(memberName, title));
    }

    @DeleteMapping("/books/{title}")
    public ResponseEntity<Member> returnBook(
            @RequestHeader("token") final String token,
            @PathVariable("title") final String title) {
        var memberName = userName(token);

        return ok(membersService.returnBook(memberName, title));
    }

}
