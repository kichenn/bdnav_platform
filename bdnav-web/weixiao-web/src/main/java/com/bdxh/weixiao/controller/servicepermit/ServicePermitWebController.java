package com.bdxh.weixiao.controller.servicepermit;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.entity.Product;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.servicepermit.dto.AddNoTrialServiceUserDto;
import com.bdxh.servicepermit.dto.AddServiceUserDto;
import com.bdxh.servicepermit.dto.WeiXiaoAddServiceUserDto;
import com.bdxh.servicepermit.enums.ServiceProductEnum;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.vo.FamilyVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 家长商品服务权限相关信息
 * @Author: Kang
 * @Date: 2019/6/13 15:02
 */
@Slf4j
@RequestMapping(value = "/servicePermitWeb")
@RestController
@Api(value = "家长商品服务许可----用户服务许可API", tags = "家长商品服务许可----用户服务许可API")
@Validated
public class ServicePermitWebController {

    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;

    @Autowired
    private ProductControllerClient productControllerClient;

    @ApiOperation("微校用户服务许可---------鉴定是否有试用资格")
    @RequestMapping(value = "/findServicePermitByCondition", method = RequestMethod.GET)
    public Object findServicePermitByCondition(@RequestParam("studentCardNumber") String studentCardNumber,@RequestParam("serviceProductEnum") ServiceProductEnum serviceProductEnum) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        Product product=productControllerClient.findProductByName(serviceProductEnum.getValue()).getResult();
        Preconditions.checkArgument(product != null, "该商品不存在，请检查");
        return serviceUserControllerClient.findServicePermitByCondition(userInfo.getSchoolCode(), studentCardNumber, userInfo.getFamilyCardNumber(),product.getId());
    }

    @ApiOperation("微校用户服务许可---------领取试用资格")
    @RequestMapping(value = "/createOnTrialService", method = RequestMethod.POST)
    public Object createOnTrialService(@Validated @RequestBody AddNoTrialServiceUserDto addNoTrialServiceUserDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        //学校id查询学校
        //家长id查询家长
        addNoTrialServiceUserDto.setCardNumber(userInfo.getFamilyCardNumber());
        addNoTrialServiceUserDto.setFamilyId(userInfo.getFamilyId());
        addNoTrialServiceUserDto.setFamilyName(userInfo.getFamilyName());
        addNoTrialServiceUserDto.setSchoolCode(userInfo.getSchoolCode());
        addNoTrialServiceUserDto.setSchoolId(userInfo.getSchoolId());
        addNoTrialServiceUserDto.setSchoolName(userInfo.getSchoolName());
        return serviceUserControllerClient.createOnTrialService(addNoTrialServiceUserDto);
    }
}