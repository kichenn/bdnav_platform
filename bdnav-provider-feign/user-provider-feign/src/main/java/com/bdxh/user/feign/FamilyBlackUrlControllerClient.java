package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyBlackUrlDto;
import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.dto.ModifyFamilyBlackUrlDto;
import com.bdxh.user.fallback.FamilyBlackUrlControllerFallback;
import com.bdxh.user.vo.FamilyBlackUrlVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 家长端黑名单的feign
 * @author WanMing
 * @date 2019/6/25 16:25
 */
@Service
@FeignClient(value = "user-provider-cluster",fallback = FamilyBlackUrlControllerFallback.class)
public interface FamilyBlackUrlControllerClient {


    /**
     * 添加家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @RequestMapping(value = "/familyBlackUrl/addFamilyBlackUrl",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addFamilyBlackUrl( @RequestBody AddFamilyBlackUrlDto addFamilyBlackUrlDto);

    /**
     * 删除家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @RequestMapping(value = "/familyBlackUrl/delFamilyBlackUrl",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delFamilyBlackUrl(@RequestParam(name = "schoolCode")  String schoolCode,
                                    @RequestParam(name = "cardNumber") String cardNumber,
                                    @RequestParam(name = "id") String id);


    /**
     * 修改家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @RequestMapping(value = "/familyBlackUrl/modifyFamilyBlackUrl",method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyFamilyBlackUrl( @RequestBody ModifyFamilyBlackUrlDto modifyFamilyBlackUrlDto );


    /**
     * 根据条件分页查询家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @RequestMapping(value = "/familyBlackUrl/findFamilyBlackUrlByCondition",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<FamilyBlackUrlVo>> findFamilyBlackUrlByCondition(@RequestBody FamilyBlackUrlQueryDto familyBlackUrlQueryDto );

    /**
     * 查询家长对应孩子的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @RequestMapping(value = "/familyBlackUrl/findFamilyBlackUrlByStudent",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<FamilyBlackUrlVo>> findFamilyBlackUrlByStudent(@RequestParam("schoolCode" )String schoolCode,
                                                                @RequestParam("cardNumber")String cardNumber,
                                                                @RequestParam("studentNumber")String studentNumber);

}
