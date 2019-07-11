package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSchoolPosDeviceChargeDto;
import com.bdxh.school.entity.SchoolPosDeviceCharge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.service.SchoolPosDeviceChargeService;

/**
* @Description: 学校部门的消费机管理控制器
* @Author WanMing
* @Date 2019-07-11 10:58:36
*/
@RestController
@RequestMapping("/schoolPosDeviceCharge")
@Slf4j
@Validated
@Api(value = "学校部门的消费机管理", tags = "学校部门的消费机管理API")
public class SchoolPosDeviceChargeController {






}