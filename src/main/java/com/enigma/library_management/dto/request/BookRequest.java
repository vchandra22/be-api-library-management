package com.enigma.library_management.dto.request;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private String title;
    private String author;
    private String publisher;
    private Date year;
    private String library;
    private List<String> category;
}
