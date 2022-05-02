package com.dmitrymilya.visa.shared.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Базовый филтр для пагинации и сортировки.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageFilter {
    private Integer page = 1;
    private Integer size = 10;

    public Integer getOffset() {
        return Page.getOffset(page, size);
    }
}
