package com.bdxh.client.controller.wallet;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelExportUtils;
import com.bdxh.common.helper.excel.bean.WalletConsumerExcelReportBean;
import com.bdxh.common.helper.excel.utils.DateUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.ChargeDeptTypeEnum;
import com.bdxh.school.enums.ConsumerTypeEnum;
import com.bdxh.school.feign.SchoolDeviceControllerClient;
import com.bdxh.school.vo.ChargeDeptAndDeviceVo;
import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.dto.QueryWalletConsumerExcelDto;
import com.bdxh.wallet.feign.WalletConsumerControllerClient;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**账户消费记录管理Web层
 * @author WanMing
 * @date 2019/7/16 10:58
 */
@RestController
@RequestMapping("/walletConsumerWeb")
@Slf4j
@Validated
@Api(value = "账户消费记录管理", tags = "账户消费记录管理API")
public class WalletConsumerWebController {

    /**
     * the param  is excel name
     */
    private static final String EXCEL_NAME = "消费记录.xlsx";

    @Autowired
    private WalletConsumerControllerClient walletConsumerControllerClient;

    @Autowired
    private SchoolDeviceControllerClient schoolDeviceControllerClient;

    /**
     * 根据条件查询账户的消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 17:22
     */
    @RequestMapping(value = "/findWalletConsumerByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询账户的消费记录", response = WalletConsumerVo.class)
    public Object findWalletConsumerByCondition(@RequestBody QueryWalletConsumerDto queryWalletConsumerDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        queryWalletConsumerDto.setSchoolCode(/*user.getSchoolCode()*/"1013371381");
        PageInfo<WalletConsumerVo> pageInfo = walletConsumerControllerClient.findWalletConsumerByCondition(queryWalletConsumerDto).getResult();
        List<WalletConsumerVo> walletConsumerVos = pageInfo.getList();
        if (CollectionUtils.isEmpty(walletConsumerVos)) {
            //无数据
            return WrapMapper.ok(walletConsumerVos);
        }
        //查询pos机及相关信息
        List<ChargeDeptAndDeviceVo> result = schoolDeviceControllerClient.findChargeDeptAndDeviceRelation(/*user.getSchoolCode()*/"1013371381", ChargeDeptTypeEnum.CONSUMER_DEPT_KEY).getResult();
        Map<String, ChargeDeptAndDeviceVo> deviceVoMap = result.stream().collect(Collectors.toMap(ChargeDeptAndDeviceVo::getDeviceId, Function.identity()));
        walletConsumerVos.forEach(walletConsumerVo -> {
            ChargeDeptAndDeviceVo chargeDeptAndDeviceVo = deviceVoMap.get(walletConsumerVo.getDeviceNumber());
            if (null != chargeDeptAndDeviceVo) {
                walletConsumerVo.setChargeDeptName(chargeDeptAndDeviceVo.getChargeDeptName());
                walletConsumerVo.setDeviceName(chargeDeptAndDeviceVo.getDeviceName());
            }

        });

        return WrapMapper.ok(pageInfo);
    }


    /**
     * 导出消费记录到excel文件
     *
     * @Author: WanMing
     * @Date: 2019/7/12 18:33
     */

    @RequestMapping(value = "/exportWalletConsumerList", method = RequestMethod.POST)
    @ApiOperation(value = "导出消费记录")
    public Object exportWalletConsumerList(@RequestBody QueryWalletConsumerExcelDto queryWalletConsumerExcelDto) {
        Long startTime = System.currentTimeMillis();
        SchoolUser user = SecurityUtils.getCurrentUser();
        queryWalletConsumerExcelDto.setSchoolCode(/*user.getSchoolCode()*/"1013371381");
        List<WalletConsumerVo> walletConsumerVos = walletConsumerControllerClient.findWalletConsumerList(queryWalletConsumerExcelDto).getResult();
        if (CollectionUtils.isEmpty(walletConsumerVos)) {
            //无数据
            return WrapMapper.ok("无数据,请重新选择");
        }
        //查询pos机及相关信息
        List<ChargeDeptAndDeviceVo> result = schoolDeviceControllerClient.findChargeDeptAndDeviceRelation(/*user.getSchoolCode()*/"1013371381", ChargeDeptTypeEnum.CONSUMER_DEPT_KEY).getResult();
        Map<String, ChargeDeptAndDeviceVo> deviceVoMap = result.stream().collect(Collectors.toMap(ChargeDeptAndDeviceVo::getDeviceId, Function.identity()));
        walletConsumerVos.forEach(walletConsumerVo -> {
            ChargeDeptAndDeviceVo chargeDeptAndDeviceVo = deviceVoMap.get(walletConsumerVo.getDeviceNumber());
            if (null != chargeDeptAndDeviceVo) {
                walletConsumerVo.setChargeDeptName(chargeDeptAndDeviceVo.getChargeDeptName());
                walletConsumerVo.setDeviceName(chargeDeptAndDeviceVo.getDeviceName());
            }

        });
        //转excel Bean 处理时间 状态
        List<WalletConsumerExcelReportBean> excelReportBeans = walletConsumerVos.stream().map(walletConsumerVo -> {
            WalletConsumerExcelReportBean excelReportBean = new WalletConsumerExcelReportBean();
            BeanUtils.copyProperties(walletConsumerVo, excelReportBean);
            excelReportBean.setConsumerStatus(ConsumerTypeEnum.getValue(walletConsumerVo.getConsumerStatus()));
            excelReportBean.setConsumerTime(DateUtils.date2Str(walletConsumerVo.getConsumerTime(), DateUtils.DATE_FORMAT_SEC));
            excelReportBean.setCreateDate(DateUtils.date2Str(walletConsumerVo.getCreateDate(), DateUtils.DATE_FORMAT_SEC));
            return excelReportBean;
        }).collect(Collectors.toList());

        //导出
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode(EXCEL_NAME, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
            ExcelExportUtils.getInstance().exportObjects2Excel(excelReportBeans, WalletConsumerExcelReportBean.class, true, "消费记录", true, outputStream);
            Long endTime = System.currentTimeMillis();
            log.info("消费记录导出成功,耗时:{}", endTime - startTime);
            return WrapMapper.ok("消费记录导出成功");
        } catch (Exception e) {
            log.error("导出失败"+e.getMessage());
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }
}
