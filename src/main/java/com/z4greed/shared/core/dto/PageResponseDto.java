package com.z4greed.shared.core.dto;

import java.util.List;
import org.springframework.data.domain.Page;

// Página con forma estable para el front, sin exponer la estructura interna de Spring Data.
public record PageResponseDto<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages) {

    public static <T> PageResponseDto<T> from(Page<T> page) {
        return new PageResponseDto<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
