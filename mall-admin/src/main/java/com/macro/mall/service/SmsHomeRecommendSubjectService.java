package com.macro.mall.service;

import com.macro.mall.model.SmsHomeRecommendSubject;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:26
 * @deprecated 首页专题推荐管理Service层
 */
public interface SmsHomeRecommendSubjectService {

    int create(List<SmsHomeRecommendSubject> homeRecommendSubjectList);

    int updateSort(Long id, Integer sort);

    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
