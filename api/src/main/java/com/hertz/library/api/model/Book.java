package com.hertz.library.api.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Book {

    @Size(min = 2)
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private List<Category> categories;

}
