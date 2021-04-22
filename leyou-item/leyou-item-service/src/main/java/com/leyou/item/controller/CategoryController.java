package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据 ParentId 查询子类目
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryById(@RequestParam("pid") Long pid) {
        if (pid == null || pid.longValue() < 0) {
            return ResponseEntity.badRequest().build(); // 响应 400
        }
        List<Category> categories = categoryService.queryCategoryById(pid);
        if(CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.notFound().build(); // 响应 404
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据品牌 Id 查询品牌分类
     * @param bid
     * @return
     */
    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBrandId(@PathVariable("bid") Long bid) {
        if (bid == null || bid.longValue() < 0) {
            return ResponseEntity.badRequest().build(); // 响应 400
        }
        List<Category> categories = categoryService.queryCategoryByBrandId(bid);
        if(CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.notFound().build(); // 响应 404
        }
        return ResponseEntity.ok(categories);
    }

}


