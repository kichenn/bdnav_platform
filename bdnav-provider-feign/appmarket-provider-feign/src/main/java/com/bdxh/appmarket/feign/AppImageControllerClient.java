package com.bdxh.appmarket.feign;

import com.bdxh.appmarket.dto.AddImageDto;
import com.bdxh.appmarket.dto.ImageQueryDto;
import com.bdxh.appmarket.dto.UpdateImageDto;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.fallback.AppImageControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 应用图片管理feign客户端
 * @author: xuyuan
 * @create: 2019-04-11 17:12
 **/
@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = AppImageControllerClientFallback.class)
public interface AppImageControllerClient {

    @RequestMapping(value = "/appImage/addImage",method = RequestMethod.POST)
    Wrapper addImage(@RequestBody AddImageDto addImageDto);

    @RequestMapping(value = "/appImage/delImage",method = RequestMethod.POST)
    Wrapper delImage(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/appImage/updateImage",method = RequestMethod.POST)
    Wrapper updateImage(@RequestBody UpdateImageDto updateImageDto);

    @RequestMapping(value = "/appImage/queryImage",method = RequestMethod.GET)
    Wrapper<AppImage> queryImage(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/appImage/queryImageList",method = RequestMethod.POST)
    Wrapper<List<AppImage>> queryImageList(@RequestBody ImageQueryDto imageQueryDto);

    @RequestMapping(value = "/appImage/queryImageListPage",method = RequestMethod.POST)
    Wrapper<PageInfo<AppImage>> queryImageListPage(@RequestBody ImageQueryDto imageQueryDto);

}
