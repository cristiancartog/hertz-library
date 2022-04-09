package com.hertz.library.api.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BookReturnRequest {

    @NotEmpty
    private String title;

}
