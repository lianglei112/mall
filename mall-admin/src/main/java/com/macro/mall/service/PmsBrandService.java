package com.macro.mall.service;

import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.model.PmsBrand;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 10:04
 * @description 商品品牌管理service层
 */
public interface PmsBrandService {

    List<PmsBrand> listAll();

    int create(PmsBrandParam pmsBrandParam);

    List<PmsBrand> list(String keyword, Integer showStatus, Integer pageNum, Integer pageSize);

    PmsBrand getItem(Long id);

    int update(Long id,PmsBrandParam pmsBrandParam);

    int delete(Long id);

    int deleteBatch(List<Long> ids);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);

}
