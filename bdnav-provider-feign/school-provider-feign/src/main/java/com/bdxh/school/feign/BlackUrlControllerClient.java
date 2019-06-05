package com.bdxh.school.feign;


import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.BlackUrlQueryDto;
import com.bdxh.school.dto.ModifyBlackUrlDto;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.fallback.BlackUrlControllerClientFallback;
import com.bdxh.school.vo.BlackUrlShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校Url
 * @Author: Kang
 * @Date: 2019/4/11 11:37
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = BlackUrlControllerClientFallback.class)
public interface BlackUrlControllerClient {

    /**
     * @Description: 增加url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/blackUrl/addBlack", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addBlack(@Validated @RequestBody AddBlackUrlDto addBlackUrlDto);

    /**
     * @Description: 修改url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/blackUrl/modifyBlack", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyBlack(@Validated @RequestBody ModifyBlackUrlDto modifyBlackUrlDto);

    /**
     * @Description: 删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/blackUrl/delBlackById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBlackById(@RequestParam("id") Long id);


    /**
     * @Description: 批量删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/blackUrl/delBlackInIds", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBlackInIds(@RequestParam("ids") List<Long> ids);

    /**
     * @Description: id查询url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/blackUrl/findBlackById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<BlackUrl> findBlackById(@RequestParam("id") Long id);

    /**
     * @Description: 分页查询
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/blackUrl/findBlackInConditionPaging", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<BlackUrlShowVo>> findBlackInConditionPaging(@Validated @RequestBody BlackUrlQueryDto blackUrlQueryDto);

    /**
     * 查询当前学校的黑名单
     * @param schoolCode
     * @return
     */
    @RequestMapping(value = "/blackUrl/findBlackInList", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> findBlackInList(@RequestParam("schoolCode") String schoolCode);

}
