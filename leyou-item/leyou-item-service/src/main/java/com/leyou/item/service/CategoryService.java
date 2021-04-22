package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据 ParentId 查询子类目
     * @param pid
     * @return
     */
    public List<Category> queryCategoryById(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categories = categoryMapper.select(category);
        return categories;
    }
    /**
     * 根据品牌 Id 查询品牌分类
     * @param bid
     * @return
     */
    public List<Category> queryCategoryByBrandId(Long bid) {
        List<Category> categories = categoryMapper.queryCategoryByBrandId(bid);
        return categories;
    }

    /**
     * 查询分类名称
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids){
        ArrayList<String> names = new ArrayList<>();
        for (Long id:ids){
            Category category = categoryMapper.selectByPrimaryKey(id);
            names.add(category.getName());
        }
        return names;
    }

}


