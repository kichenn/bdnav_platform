package com.bdxh.client.controller.wallet;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.user.vo.TeacherVo;
import com.bdxh.wallet.dto.AddWalletAccount;
import com.bdxh.wallet.dto.BusinessCardDto;
import com.bdxh.wallet.dto.ModifyWalletAccount;
import com.bdxh.wallet.dto.QueryWalletAccount;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/walletAccountWeb")
@Slf4j
@Validated
@Api(value = "钱包账户控制器", tags = "钱包账户控制器")
public class WalletAccountWebController {

    @Autowired
    private WalletAccountControllerClient walletAccountControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private TeacherControllerClient teacherControllerClient;





    @ApiOperation(value = "删除钱包账户",response = Boolean.class)
    @RequestMapping(value = "/delWalletAccount", method = RequestMethod.GET)
    public Object delWalletAccount(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) {
        try {
            return walletAccountControllerClient.delWalletAccount(schoolCode,cardNumber,id);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "添加钱包账号",response = Boolean.class)
    @RequestMapping(value = "/addWalletAccount", method = RequestMethod.POST)
    public Object addWalletAccount(@RequestBody AddWalletAccount addWalletAccount) {
        try {
            SchoolUser user = SecurityUtils.getCurrentUser();
            addWalletAccount.setOperator(user.getId());
            addWalletAccount.setOperatorName(user.getUserName());
            return walletAccountControllerClient.addWalletAccount(addWalletAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value="修改钱包账号",response = Boolean.class)
    @RequestMapping(value = "/modifyPhysicalCard", method = RequestMethod.POST)
    public Object modifyPhysicalCard(@RequestBody ModifyWalletAccount modifyWalletAccount) {
        try {
            SchoolUser user = SecurityUtils.getCurrentUser();
            modifyWalletAccount.setOperator(user.getId());
            modifyWalletAccount.setOperatorName(user.getUserName());
            return walletAccountControllerClient.modifyWalletAccount(modifyWalletAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "分页查询钱包账号",response = WalletAccount.class)
    @RequestMapping(value = "/findWalletAccountInCondition", method = RequestMethod.POST)
    public Object findWalletAccountInCondition(@RequestBody QueryWalletAccount queryWalletAccount) {
        try {
        /*    SchoolUser user = SecurityUtils.getCurrentUser();
            queryWalletAccount.setSchoolCode(user.getSchoolCode());*/
            return walletAccountControllerClient.findWalletAccountInCondition(queryWalletAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "个人名片展示")
    @RequestMapping(value = "/businessCardToShow", method = RequestMethod.POST)
    public Object businessCardToShow(@RequestBody BusinessCardDto businessCardDto) {
        try {
               SchoolUser user = SecurityUtils.getCurrentUser();
               businessCardDto.setSchoolCode(user.getSchoolCode());
             if (businessCardDto.getUserTypeEnum().getKey()==1){
                 StudentVo sv=studentControllerClient.queryStudentInfo(businessCardDto.getSchoolCode(),businessCardDto.getCardNumber()).getResult();
                 return WrapMapper.ok(sv);
             }else{
                 TeacherVo tv=teacherControllerClient.queryTeacherInfo(businessCardDto.getSchoolCode(),businessCardDto.getCardNumber()).getResult();
                 return WrapMapper.ok(tv);
             }
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
