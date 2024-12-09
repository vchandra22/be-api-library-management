package com.enigma.library_management.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingResponse {
    private Integer page;
    private Integer size;
    private Integer totalPage;
    private Long totalItems;
}
