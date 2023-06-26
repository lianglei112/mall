package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.*;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.service.PmsProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 16:27
 * @deprecated 商品操作service实现层
 */
@Slf4j
@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsProductDao pmsProductDao;

    @Autowired
    private PmsMemberPriceDao pmsMemberPriceDao;

    @Autowired
    private PmsMemberPriceMapper pmsMemberPriceMapper;

    @Autowired
    private PmsProductLadderDao pmsProductLadderDao;

    @Autowired
    private PmsProductLadderMapper pmsProductLadderMapper;

    @Autowired
    private PmsProductFullReductionDao pmsProductFullReductionDao;

    @Autowired
    private PmsProductFullReductionMapper pmsProductFullReductionMapper;

    @Autowired
    private PmsSkuStockDao pmsSkuStockDao;

    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @Autowired
    private PmsProductAttributeValueDao pmsProductAttributeValueDao;

    @Autowired
    private PmsProductAttributeValueMapper pmsProductAttributeValueMapper;

    @Autowired
    private CmsSubjectProductRelationDao cmsSubjectProductRelationDao;

    @Autowired
    private CmsSubjectProductRelationMapper cmsSubjectProductRelationMapper;

    @Autowired
    private CmsPrefrenceAreaProductRelationDao cmsPrefrenceAreaProductRelationDao;

    @Autowired
    private CmsPrefrenceAreaProductRelationMapper cmsPrefrenceAreaProductRelationMapper;

    @Autowired
    private PmsProductVertifyRecordMapper pmsProductVertifyRecordMapper;

    @Autowired
    private PmsProductVertifyRecordDao pmsProductVertifyRecordDao;

    /**
     * 创建商品
     *
     * @param pmsProductParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int create(PmsProductParam pmsProductParam) {
        int count;
        //创建商品
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setId(null);
        pmsProductMapper.insertSelective(pmsProduct);
        //根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = pmsProduct.getId();
        //会员价格插入操作
        relateAndInsertList(pmsMemberPriceDao, pmsProductParam.getPmsMemberPriceList(), productId);
        //阶梯价格插入操作
        relateAndInsertList(pmsProductLadderDao, pmsProductParam.getPmsProductLadderList(), productId);
        //满减价格插入操作
        relateAndInsertList(pmsProductFullReductionDao, pmsProductParam.getPmsProductFullReductionList(), productId);
        //处理sku编码（sku编码生成规则  八位日期 + 四位商品编码 + 四位索引编码（遍历自增生成））
        handleSkuStockCode(pmsProductParam.getPmsSkuStockList(), productId);
        //添加sku库存信息
        relateAndInsertList(pmsSkuStockDao, pmsProductParam.getPmsSkuStockList(), productId);
        //添加商品参数，添加自定义商品规格
        relateAndInsertList(pmsProductAttributeValueDao, pmsProductParam.getPmsProductAttributeValueList(), productId);
        //关联专题
        relateAndInsertList(cmsSubjectProductRelationDao, pmsProductParam.getCmsSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsertList(cmsPrefrenceAreaProductRelationDao, pmsProductParam.getCmsPrefrenceAreaProductRelationList(), productId);
        //最终返回结果
        count = 1;
        return count;
    }

    @Override
    public List<PmsProduct> getList(PmsProductQueryParam productQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (StringUtils.isNotEmpty(productQueryParam.getKeyword())) {
            criteria.andKeywordsLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnLike("%" + productQueryParam.getProductSn() + "%");
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdNotEqualTo(productQueryParam.getProductCategoryId());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        return pmsProductMapper.selectByExample(example);
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return pmsProductDao.getUpdateInfo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int update(Long id, PmsProductParam pmsProductParam) {
        int count;
        //更新商品信息
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setId(id);
        pmsProductMapper.updateByPrimaryKeySelective(pmsProduct);
        //更新会员价格
        PmsMemberPriceExample memberPriceExample = new PmsMemberPriceExample();
        memberPriceExample.createCriteria().andProductIdEqualTo(id);
        pmsMemberPriceMapper.deleteByExample(memberPriceExample);
        relateAndInsertList(pmsMemberPriceDao, pmsProductParam.getPmsMemberPriceList(), id);
        //更新阶梯价格
        PmsProductLadderExample productLadderExample = new PmsProductLadderExample();
        productLadderExample.createCriteria().andProductIdEqualTo(id);
        pmsProductLadderMapper.deleteByExample(productLadderExample);
        relateAndInsertList(pmsProductLadderDao, pmsProductParam.getPmsProductLadderList(), id);
        //更新满减价格
        PmsProductFullReductionExample productFullReductionExample = new PmsProductFullReductionExample();
        productFullReductionExample.createCriteria().andProductIdEqualTo(id);
        pmsProductFullReductionMapper.deleteByExample(productFullReductionExample);
        relateAndInsertList(pmsProductFullReductionDao, pmsProductParam.getPmsProductFullReductionList(), id);
        //修改sku库存信息
        handleUpdateSkuStockList(id, pmsProductParam);
        //修改商品参数，添加自定义商品规格
        PmsProductAttributeValueExample attributeValueExample = new PmsProductAttributeValueExample();
        attributeValueExample.createCriteria().andProductIdEqualTo(id);
        pmsProductAttributeValueMapper.deleteByExample(attributeValueExample);
        relateAndInsertList(pmsProductAttributeValueDao, pmsProductParam.getPmsProductAttributeValueList(), id);
        //更新关联主题相关信息
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        cmsSubjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        relateAndInsertList(cmsSubjectProductRelationDao, pmsProductParam.getCmsSubjectProductRelationList(), id);
        //更新关联优选相关信息
        CmsPrefrenceAreaProductRelationExample areaProductRelationExample = new CmsPrefrenceAreaProductRelationExample();
        areaProductRelationExample.createCriteria().andProductIdEqualTo(id);
        cmsPrefrenceAreaProductRelationMapper.deleteByExample(areaProductRelationExample);
        relateAndInsertList(cmsPrefrenceAreaProductRelationDao, pmsProductParam.getCmsPrefrenceAreaProductRelationList(), id);
        count = 1;
        return count;
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
            example.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return pmsProductMapper.selectByExample(example);
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setPublishStatus(publishStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setNewStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct, example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        int count = pmsProductMapper.updateByExampleSelective(pmsProduct, example);
        //修改完审核状态后插入审核记录
        for (Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setStatus(verifyStatus);
            record.setDetail(detail);
            record.setVertifyMan("test");
            list.add(record);
        }
        pmsProductVertifyRecordDao.insertList(list);
        return count;
    }

    /**
     * 修改sku库存相关信息
     *
     * @param id
     * @param pmsProductParam
     */
    private void handleUpdateSkuStockList(Long id, PmsProductParam pmsProductParam) {
        //1、获取当前sku信息
        List<PmsSkuStock> currSkuList = pmsProductParam.getPmsSkuStockList();
        //2、当前没有sku信息直接删除
        //TODO 这里有疑问，前端不传递，直接删除，那订单和购物车的sku信息还是一样失效啊
        if (CollectionUtils.isEmpty(currSkuList)) {
            PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
            skuStockExample.createCriteria().andProductIdEqualTo(id);
            pmsSkuStockMapper.deleteByExample(skuStockExample);
            return;
        }
        //3、获取该商品初始sku库存列表
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        List<PmsSkuStock> oriStuList = pmsSkuStockMapper.selectByExample(skuStockExample);
        //4、获取前端需要新增的sku信息
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        //5、获取前端需要更新的sku信息
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        //提取要更新sku的id字段为一个集合
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //6、获取前端需要删除的sku信息
        List<PmsSkuStock> removeSkuList = oriStuList.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        //7、执行新增操作
        if (!CollectionUtils.isEmpty(insertSkuList)) {
            relateAndInsertList(pmsSkuStockDao, insertSkuList, id);
        }
        //8、执行更新操作
        if (!CollectionUtils.isEmpty(updateSkuList)) {
            for (PmsSkuStock skuStock : updateSkuList) {
                pmsSkuStockMapper.updateByPrimaryKeySelective(skuStock);
            }
        }
        //9、执行删除操作
        if (!CollectionUtils.isEmpty(removeSkuList)) {
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            PmsSkuStockExample pmsSkuStockExample = new PmsSkuStockExample();
            pmsSkuStockExample.createCriteria().andIdIn(removeSkuIds);
            pmsSkuStockMapper.deleteByExample(pmsSkuStockExample);
        }
    }

    /**
     * 处理sku和商品时间的关系
     *
     * @param pmsSkuStockList
     * @param productId
     */
    private void handleSkuStockCode(List<PmsSkuStock> pmsSkuStockList, Long productId) {
        if (CollectionUtils.isEmpty(pmsSkuStockList)) return;
        for (int i = 0; i < pmsSkuStockList.size(); i++) {
            PmsSkuStock skuStock = pmsSkuStockList.get(i);
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

    /**
     * 执行商品的其他附属属性插入
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            //进行数据的校验判空，如果为空则不执行后续的操作
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                //通过反射获取对应的方法，设置主键为空！
                Method setId = item.getClass().getMethod("setId", Long.class);
                //调用该方法插入值
                setId.invoke(item, (Long) null);
                //通过反射获取对应的方法，关联商品id
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                //调用该方法插入值
                setProductId.invoke(item, productId);
            }
            //通过反射获取对应的方法
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            //调用该方法插入值
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.error("创建产品出错：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
