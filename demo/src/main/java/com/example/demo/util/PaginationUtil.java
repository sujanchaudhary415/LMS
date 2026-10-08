package com.example.demo.util;

import com.example.demo.wrapper.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationUtil {
    public static <E,T>PagedResponse<T>build(Page<E>page, List<T>content){
        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
