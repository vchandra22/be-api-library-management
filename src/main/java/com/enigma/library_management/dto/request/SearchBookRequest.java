package com.enigma.library_management.dto.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SearchBookRequest extends PagingRequest {
    private String title;
    private String author;
}
