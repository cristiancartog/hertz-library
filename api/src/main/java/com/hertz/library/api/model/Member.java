package com.hertz.library.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Member {

    private String name;

    private List<Book> books;

}
