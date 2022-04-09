package com.hertz.library.api.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class Member {

    @NotEmpty
    private String name;

    private List<Book> books;

}
