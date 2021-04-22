package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key    搜索关键词
     * @param page   当前页
     * @param rows   每页大小
     * @param sortBy 排序字段
     * @param desc   是否为降序
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc
    ) {
        PageResult<Brand> brandPageResult = brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(brandPageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }

    /**
     * 新增品牌
     * @param cids
     * @param brand
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(@RequestParam("cids") List<Long> cids, Brand brand) {
        this.brandService.saveBrand(cids,brand);
        // 响应 201
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 更新品牌
     * @param cids
     * @param brand
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(@RequestParam("cids") List<Long> cids, Brand brand) {
        this.brandService.updateBrand(cids,brand);
        // 响应 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除品牌
     * @param bid
     * @return
     */
    @DeleteMapping("/bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid) {
        this.brandService.deleteBrand(bid);
        // 响应 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据分类 id 查询品牌信息
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid) {
        List<Brand> brands = brandService.queryBrandsByCid(cid);
        if(CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }




}

