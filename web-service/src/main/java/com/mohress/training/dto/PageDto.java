package com.mohress.training.dto;

import lombok.Data;

@Data
public class PageDto {

    private Integer page;
    private Integer pageSize;

    public PageDto() {
    }

    public PageDto(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
