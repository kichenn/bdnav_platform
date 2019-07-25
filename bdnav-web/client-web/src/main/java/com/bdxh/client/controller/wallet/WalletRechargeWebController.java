package com.bdxh.client.controller.wallet;


import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelExportUtils;
import com.bdxh.common.helper.excel.bean.WalletConsumerExcelReportBean;
import com.bdxh.common.helper.excel.bean.WalletRechargeExcelReportBean;
import com.bdxh.common.helper.excel.utils.DateUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.ChargeDeptTypeEnum;
import com.bdxh.school.feign.SchoolDeviceControllerClient;
import com.bdxh.school.vo.ChargeDeptAndDeviceVo;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.dto.QueryWalletRechargeExcelDto;
import com.bdxh.wallet.enums.RechargeStatusEnum;
import com.bdxh.wallet.enums.RechargeTypeEnum;
import com.bdxh.wallet.enums.UserTypeEnum;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import com.bdxh.wallet.vo.BaseEchartsVo;
import com.bdxh.wallet.vo.WalletRechargeVo;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WanMing
 * @date 2019/7/17 11:12
 */
@RestController
@RequestMapping("/walletRechargeWeb")
@Slf4j
@Validated
@Api(value = "账户充值记录管理", tags = "账户充值记录管理API")
public class WalletRechargeWebController {


    private static final String EXCEL_NAME = "充值记录.xlsx";


    @Autowired
    private WalletRechargeControllerClient walletRechargeControllerClient;

    @Autowired
    private SchoolDeviceControllerClient schoolDeviceControllerClient;


    /**
     * 根据条件分页查询充值记录
     *
     * @Author: WanMing
     * @Date: 2019/7/15 11:13
     */
    @RequestMapping(value = "/findWalletRechargeByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件分页查询充值记录", response = WalletRechargeVo.class)
    public Object findWalletRechargeByCondition(@RequestBody QueryWalletRechargeDto queryWalletRechargeDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        queryWalletRechargeDto.setSchoolCode(user.getSchoolCode());
        PageInfo<WalletRechargeVo> pageInfo = walletRechargeControllerClient.findWalletRechargeByCondition(queryWalletRechargeDto).getResult();
        List<WalletRechargeVo> walletRechargeVos = pageInfo.getList();
        if (CollectionUtils.isEmpty(walletRechargeVos)) {
            //无数据
            return WrapMapper.ok(pageInfo);
        }
        //设置部门信息
        List<ChargeDeptAndDeviceVo> deptAndDeviceVos = schoolDeviceControllerClient.findChargeDeptAndDeviceRelation(user.getSchoolCode(), ChargeDeptTypeEnum.RECHARGE_DEPT_KEY).getResult();
        if (CollectionUtils.isNotEmpty(deptAndDeviceVos)) {
            Map<String, String> deptAndDeviceVoMap = deptAndDeviceVos.stream().collect(Collectors.toMap(ChargeDeptAndDeviceVo::getDeviceId, ChargeDeptAndDeviceVo::getChargeDeptName));
            walletRechargeVos.forEach(walletRechargeVo -> {
                walletRechargeVo.setWindowName(deptAndDeviceVoMap.get(walletRechargeVo.getDeviceNumber()));
            });
        }

        return WrapMapper.ok(pageInfo);
    }

    /**
     * 导出充值记录列表
     *
     * @Author: WanMing
     * @Date: 2019/7/22 14:25
     */
    @RequestMapping(value = "/exportWalletRechargeList", method = RequestMethod.GET)
    @ApiOperation(value = "导出充值记录列表", response = WalletRechargeVo.class)
    public Object exportWalletRechargeList(@RequestParam("exportWay") Byte exportWay
            , @RequestParam(value = "orderNos", required = false) List<Long> orderNos
            , @RequestParam(value = "rechargeType", required = false) Byte rechargeType
            , @RequestParam(value = "windowId", required = false) Long windowId
            , @RequestParam(value = "startTime", required = false) Date startDate
            , @RequestParam(value = "endDate", required = false) Date endDate) {
        long startTime = System.currentTimeMillis();

        QueryWalletRechargeExcelDto rechargeExcelDto = new QueryWalletRechargeExcelDto();
        rechargeExcelDto.setExportWay(exportWay);
        rechargeExcelDto.setOrderNos(orderNos);
        rechargeExcelDto.setWindowId(windowId);
        rechargeExcelDto.setStartTime(startDate);
        rechargeExcelDto.setEndTime(endDate);

        SchoolUser user = SecurityUtils.getCurrentUser();
        rechargeExcelDto.setSchoolCode(user.getSchoolCode());
        List<WalletRechargeVo> walletRechargeVos = walletRechargeControllerClient.findWalletRechargeList(rechargeExcelDto).getResult();
        if (CollectionUtils.isEmpty(walletRechargeVos)) {
            //无数据
            return WrapMapper.ok("无数据");
        }
        //设置部门信息
        List<ChargeDeptAndDeviceVo> deptAndDeviceVos = schoolDeviceControllerClient.findChargeDeptAndDeviceRelation(user.getSchoolCode(), ChargeDeptTypeEnum.RECHARGE_DEPT_KEY).getResult();
        if (CollectionUtils.isNotEmpty(deptAndDeviceVos)) {
            Map<String, String> deptAndDeviceVoMap = deptAndDeviceVos.stream().collect(Collectors.toMap(ChargeDeptAndDeviceVo::getDeviceId, ChargeDeptAndDeviceVo::getChargeDeptName));
            walletRechargeVos.forEach(walletRechargeVo -> {
                walletRechargeVo.setWindowName(deptAndDeviceVoMap.get(walletRechargeVo.getDeviceNumber()));
            });
        }

        //转Excel bean 处理时间 和状态信息
        List<WalletRechargeExcelReportBean> excelReportBeans = walletRechargeVos.stream().map(walletRechargeVo -> {
            WalletRechargeExcelReportBean excelReportBean = new WalletRechargeExcelReportBean();
            BeanUtils.copyProperties(walletRechargeVo, excelReportBean);
            excelReportBean.setCreateDate(DateUtils.date2Str(walletRechargeVo.getCreateDate(), DateUtils.DATE_FORMAT_SEC));
            excelReportBean.setRechargeTime(DateUtils.date2Str(walletRechargeVo.getRechargeTime(), DateUtils.DATE_FORMAT_SEC));
            excelReportBean.setFamilyName(walletRechargeVo.getFamilyName() == null ? "无" : walletRechargeVo.getFamilyName());
            excelReportBean.setFamilyNumber(walletRechargeVo.getFamilyNumber() == null ? "无" : walletRechargeVo.getFamilyNumber());
            excelReportBean.setRechargeStatus(RechargeStatusEnum.getValue(walletRechargeVo.getRechargeStatus()));
            excelReportBean.setRechargeType(RechargeTypeEnum.getValue(walletRechargeVo.getRechargeType()));
            excelReportBean.setUserType(UserTypeEnum.getValue(walletRechargeVo.getUserType())==null?"无":UserTypeEnum.getValue(walletRechargeVo.getUserType()));
            excelReportBean.setWindowName(excelReportBean.getWindowName()==null?"无":excelReportBean.getWindowName());
            return excelReportBean;

        }).collect(Collectors.toList());

        //导出
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode(EXCEL_NAME, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
            ExcelExportUtils.getInstance().exportObjects2Excel(excelReportBeans, WalletRechargeExcelReportBean.class, true, "充值记录", true, outputStream);
            long endTime = System.currentTimeMillis();
            log.info("充值记录导出成功,耗时:{}", endTime - startTime);
            return WrapMapper.ok("充值记录导出成功");
        } catch (Exception e) {
            log.error("导出失败" + e.getMessage());
            e.printStackTrace();
        }
        return WrapMapper.ok();

    }

    /**
     * 查询不同充值类型下充值成功的总金额
     *
     * @Author: WanMing
     * @Date: 2019/7/15 11:13
     */
    @RequestMapping(value = "/findWalletRechargeTypeMoneySum", method = RequestMethod.GET)
    @ApiOperation(value = "查询不同充值类型下充值成功的总金额", response = BaseEchartsVo.class)
    public Object findWalletRechargeTypeMoneySum() {
        SchoolUser user = SecurityUtils.getCurrentUser();
        return walletRechargeControllerClient.findWalletRechargeTypeMoneySum(user.getSchoolCode());
    }
}
