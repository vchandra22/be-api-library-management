package com.enigma.library_management.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryResponse {
    private String id;
    private String name;
    private String address;
}
