package com.enigma.library_management.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryRequest {
    private String name;
    private String address;
}
