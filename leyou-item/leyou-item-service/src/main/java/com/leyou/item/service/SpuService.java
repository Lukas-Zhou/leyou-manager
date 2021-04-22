package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
     * 根据查询条件分页查询商品信息
     *
     * @param key      搜索条件
     * @param saleable 上下架
     * @param page     当前页
     * @param rows     每页大小
     * @return
     */
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        // 初始化example对象
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        // 添加搜索条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        // 添加上下架
        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }

        // 添加分页
        PageHelper.startPage(page,rows);

        // 执行查询，获取Spu集合
        List<Spu> spus = spuMapper.selectByExample(example);

        // 将Spu集合包装成pageInfo
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);

        // 将Spu集合转化为SpuBo集合
        ArrayList<SpuBo> spuBos = new ArrayList<>();
        for (Spu spu:spus){
            SpuBo spuBo = new SpuBo();
            // 复制共同的属性到SpuBo对象中
            BeanUtils.copyProperties(spu,spuBo);
            // 查询分类名称，并添加到SpuBo对象中
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            // 添加SpuBo到SpuBo集合
            spuBos.add(spuBo);
        }

        // 返回PageResult<SpuBo>
        return new PageResult<SpuBo>(spuPageInfo.getTotal(),spuBos);
    }

    /**
     * 新增商品
     * @param spuBo
     * @return
     */
    @Transactional
    public void saveSpu(SpuBo spuBo) {
        // 先新增 Spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insertSelective(spuBo);

        // 再新增 SpuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.insertSelective(spuDetail);

        // 再新增 Sku
        List<Sku> skus = spuBo.getSkus();
        for (Sku sku : skus) {
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insertSelective(sku);
            // 新增 Stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insertSelective(stock);
        }
    }


}
