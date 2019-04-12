package com.bdxh.appmarket.persistence;

import com.bdxh.appmarket.entity.AppImage;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface AppImageMapper extends Mapper<AppImage> {

    /**
     * 根据应用id删除图片
     * @param appId
     */
    void deleteByAppId(Long appId);

    /**
     * 根据条件查询应用图片列表
     * @param param
     * @return
     */
    List<AppImage> getByCondition(Map<String,Object> param);

    /**
     * 批量插入应用图片
     * @param appImages
     */
    void batchSaveImage(List<AppImage> appImages);

}