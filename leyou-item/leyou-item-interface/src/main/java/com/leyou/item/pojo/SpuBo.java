package com.leyou.item.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SpuBo extends Spu{
    private String cname;
    private String bname;
    private SpuDetail spuDetail;
    private List<Sku> skus;

    // getter、setter、toString 方法省略
}