package com.hertz.service.hertzlibrary.controller.model;

import com.hertz.service.hertzlibrary.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksResponse {

    private List<Book> books;

}
