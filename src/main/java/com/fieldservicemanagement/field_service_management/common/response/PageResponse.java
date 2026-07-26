package com.fieldservicemanagement.field_service_management.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private int page;
    private int size;
    private long totalElements;
    private int totalPage;
    private List<T> content;

    public PageResponse(int page, int size, Long totalElements, List<T> content) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPage = (int) Math.ceil(totalElements.doubleValue() / size);
        this.content = content;
    }

    public static <T> PageResponse<T> defaultPage() {
        return new PageResponse<>(0, 0, 0L, 0, List.of());
    }
}
