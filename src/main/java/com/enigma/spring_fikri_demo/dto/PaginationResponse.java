package com.enigma.spring_fikri_demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse {
    private Integer currentPage;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageSize;
}
