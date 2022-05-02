package com.dmitrymilya.visa.shared.util;

import lombok.Data;

@Data
public class Page<T> {

    private T content;
    private long totalPages;
    private long totalElements;
    private int numberOfElements;

    public Page(T content, int numberOfElements) {
        this.content = content;
        this.numberOfElements = numberOfElements;
    }

    public static <T> Page<T> createPage(T content, int limit) {
        return new Page<>(content, limit);
    }

    public static int getOffset(int currentPage, int pageSize) {
        return currentPage <= 0 ? 0 : (currentPage - 1) * pageSize;
    }

    public Page<T> setTotal(long totalElements) {
        this.totalElements = totalElements;
        if (numberOfElements == 0) {
            this.totalPages = 0;
            return this;
        }
        this.totalPages = totalElements / numberOfElements + (totalElements % numberOfElements > 0 ? 1 : 0);
        return this;
    }

    public T getContent() {
        return content;
    }
}

