package com.bdxh.appmarket.fallback;

import com.bdxh.appmarket.dto.AddImageDto;
import com.bdxh.appmarket.dto.ImageQueryDto;
import com.bdxh.appmarket.dto.UpdateImageDto;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.feign.AppImageControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 应用图片管理feign降级服务
 * @author: xuyuan
 * @create: 2019-04-11 17:12
 **/
@Component
public class AppImageControllerClientFallback implements AppImageControllerClient {

    @Override
    public Wrapper addImage(AddImageDto addImageDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delImage(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateImage(UpdateImageDto updateImageDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AppImage> queryImage(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<AppImage>> queryImageList(ImageQueryDto imageQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<AppImage>> queryImageListPage(ImageQueryDto imageQueryDto) {
        return WrapMapper.error();
    }

}
