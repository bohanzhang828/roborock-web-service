package com.roborock.springboot.server.bean;

import lombok.Data;

@Data
public class PageRequest<T> {

    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 具体查询条件
     * */
    private T content;

}
