package com.enigma.library_management.dto.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PagingRequest {
    private Integer page;
    private Integer size;
    private String sort;
}
