package com.bdxh.backend.controller.appburied;

import com.bdxh.appburied.dto.AddApplyLogDto;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@RestController
@RequestMapping("/applyLogWeb")
@Slf4j
@Validated
@Api(value = "应用审核日志信息", tags = "应用审核日志信息交互API")
public class ApplyLogWebController {

    @Autowired
    private ApplyLogControllerClient applyLogControllerClient;


    @RequestMapping(value = "/findApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP审核日志信息", response = AppStatus.class)
    public Object findApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto findApplyLogDto) {
        Wrapper wrapper= applyLogControllerClient.findApplyLogById(findApplyLogDto);
        return wrapper;
    }

    @RequestMapping(value = "/findApplyLogInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页审核App状态日志查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody ApplyLogQueryDto applyLogQueryDto) {
        return applyLogControllerClient.findApplyLogInContionPaging(applyLogQueryDto);
    }
}