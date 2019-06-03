package com.bdxh.backend.controller.report;

import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.feign.BaseUserControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
* @Description:  学校报表
* @Author: Kang
* @Date: 2019/5/23 14:53
*/
@RestController
@RequestMapping("/schoolReportWebController")
@Validated
@Slf4j
@Api(value = "学校报表信息", tags = "学校报表信息API")
public class SchoolWebReportWebController {


    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private BaseUserControllerClient baseUserControllerClient;




    /**
     * 展示用户分类数据量的信息
     * @Author: WanMing
     * @return 学校用户分类数据量
     */
    @RequestMapping(value = "/querySchoolUserCategoryCount",method = RequestMethod.GET)
    @ApiOperation(value = "查询用户分类数量")
    public Object querySchoolUserCategoryCount(){
        return baseUserControllerClient.querySchoolUserCategoryCount(null);
    }

    /**
     * 展示不同地区下学校的数量
     * @Author: WanMing
     * @return 不同地区的学校数量
     */
    @RequestMapping(value = "/querySchoolNumByArea",method = RequestMethod.GET)
    @ApiOperation(value = "查询不同地区下学校的数量")
    public Object querySchoolNumByArea(){
        return schoolControllerClient.querySchoolNumByArea();
    }



}
