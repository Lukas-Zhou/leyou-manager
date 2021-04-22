package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    /**
     * 根据查询条件分页查询商品信息
     *
     * @param key      搜索条件
     * @param saleable 上下架
     * @param page     当前页
     * @param rows     每页大小
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "saleable", required = false) Boolean saleable,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows" , defaultValue = "5") Integer rows
    ){
        PageResult<SpuBo> pageResult = spuService.querySpuByPage(key,saleable,page,rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())){
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 新增商品
     * @param spuBo
     * @return
     */
    @PostMapping  //("spu")
    public ResponseEntity<Void> saveSpu(@RequestBody SpuBo spuBo) {
        spuService.saveSpu(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
