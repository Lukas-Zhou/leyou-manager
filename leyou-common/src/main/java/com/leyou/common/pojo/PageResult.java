package com.leyou.common.pojo;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private Long total; //总条数
    private List<T> items; //当前页数据
    private Integer totalPage; //总页数

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, List<T> items, Integer totalPage) {
        this.total = total;
        this.items = items;
        this.totalPage = totalPage;
    }

    // getter 和 setter 方法、toString 方法省略
}


