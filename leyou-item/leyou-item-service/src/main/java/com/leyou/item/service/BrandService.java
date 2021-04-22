package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key    搜索关键词
     * @param page   当前页
     * @param rows   每页大小
     * @param sortBy 排序字段
     * @param desc   是否为降序
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        // 初始化 example 对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据 name 模糊查询，或根据 letter 查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        // 设置分页条件
        PageHelper.startPage(page, rows);

        // 添加排序
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = brandMapper.selectByExample(example);
        // 包装成 pageInfo
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);

        // 包装成分页结果集返回
        return new PageResult<Brand>(brandPageInfo.getTotal(), brandPageInfo.getList());
    }



    /**
     * 新增品牌
     *
     * @param cids
     * @param brand
     */
    @Transactional
    public void saveBrand(List<Long> cids, Brand brand) {
        brandMapper.insertSelective(brand);
        for (Long cid : cids) {
            brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }

    /**
     * 更新品牌
     * @param cids
     * @param brand
     */
    @Transactional
    public void updateBrand(List<Long> cids, Brand brand) {
        // 先更新 Brand
        brandMapper.updateByPrimaryKey(brand);
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(brand.getId());
        // 再新增中间表
        for (Long cid : cids) {
            brandMapper.insertCategoryAndBrand(cid, brand.getId());
        }
    }

    /**
     * 删除品牌
     * @param bid
     */
    @Transactional
    public void deleteBrand(Long bid) {
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(bid);
        // 删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }

    /**
     * 根据分类 id 查询品牌信息
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid) {
        List<Brand> brands = brandMapper.queryBrandsByCid(cid);
        return brands;
    }



}


