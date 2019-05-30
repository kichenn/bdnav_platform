package com.bdxh.client.controller.report;

import com.bdxh.user.feign.BaseUserControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description:  学校报表
* @Author: Kang
* @Date: 2019/5/23 14:53
*/
@RestController
@RequestMapping("/clientSchoolReportWeb")
@Slf4j
@Validated
@Api(value = "学校管理--报表信息", tags = "学校管理--报表信息API")
public class SchoolReportWebController {


    @Autowired
    private BaseUserControllerClient baseUserControllerClient;



    /**
     * 展示用户分类数量的信息
     * @param schoolCode 学校编号
     * @return 学校用户分类数量
     */
    @RequestMapping(value = "/querySchoolUserCategoryCount",method = RequestMethod.GET)
    @ApiOperation(value = "查询总用户分类数量")
    public Object querySchoolUserCategoryCount(@RequestParam(name = "schoolCode")String schoolCode){
        return baseUserControllerClient.querySchoolUserCategoryCount(schoolCode);
    }











}
