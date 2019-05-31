package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.feign.VisitLogsControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 收费服务
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation("家长查询单个孩子浏览网站日志接口")
    @RequestMapping(value="/queryVisitLogByCardNumber",method = RequestMethod.POST)
    public Object queryVisitLogByCardNumber(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
                                            @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber){
        try {
            return visitLogsControllerClient.queryVisitLogByCardNumber(schoolCode,cardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error();
        }
    }
}