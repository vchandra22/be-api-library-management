package com.enigma.library_management.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    private String userId;
}
