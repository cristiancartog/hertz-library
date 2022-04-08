package com.hertz.service.hertzlibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String title;
    private String author;
    private List<Category> categories;

    private String renterName;

}
