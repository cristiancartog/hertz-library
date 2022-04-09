package com.hertz.library.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Book {

    private String title;
    private String author;
    private List<Category> categories;

}
