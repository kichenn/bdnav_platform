package com.bdxh.weixiao.controller.user;

import com.bdxh.account.entity.Account;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.BlackUrlControllerClient;
import com.bdxh.user.feign.VisitLogsControllerClient;
import com.bdxh.user.vo.VisitLogsVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 19:37
 **/
@Slf4j
@RequestMapping(value = "/visitLogWeb")
@RestController
@Api(value = "上网行为管理----上网行为管理控制器", tags = "上网行为管理----上网行为管理控制器")
@Validated
public class VisitLogsWebController {
    @Autowired
    private VisitLogsControllerClient visitLogsControllerClient;

    @Autowired
    private BlackUrlControllerClient blackUrlControllerClient;

    /**
     * 收费服务
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "家长查询单个孩子浏览网站日志接口",response = VisitLogsVo.class)
    @RequestMapping(value="/queryVisitLogByCardNumber",method = RequestMethod.POST)
    public Object queryVisitLogByCardNumber(@RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber){
        try {
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            return visitLogsControllerClient.queryVisitLogByCardNumber(userInfo.getSchoolCode(),cardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error();
        }
    }


    @RequestMapping(value = "/addBlacklist", method = RequestMethod.POST)
    @ApiOperation(value = "家长添加url黑名单", response = Boolean.class)
    public Object addBlacklist(@Validated @RequestBody AddBlackUrlDto addBlackUrlDto) {
        addBlackUrlDto.setUrlType(Long.valueOf(2));//标识为家长添加的黑名单
        Wrapper wrapMapper = blackUrlControllerClient.addBlack(addBlackUrlDto);
        return wrapMapper;
    }


}