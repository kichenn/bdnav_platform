package com.bdxh.wallet.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.AddWalletConsumerDto;
import com.bdxh.wallet.dto.ModifyWalletConsumerDto;
import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.dto.WalletConsumerExcelDto;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.service.WalletConsumerService;
import com.bdxh.wallet.vo.WalletConsumerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @Description: 账户消费记录管理控制器
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@RestController
@RequestMapping("/walletConsumer")
@Slf4j
@Validated
@Api(value = "账户消费记录管理", tags = "账户消费记录管理API")
public class WalletConsumerController {

    @Autowired
    private WalletConsumerService walletConsumerService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    /**
     * 添加账户消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 15:06
     */
    @RequestMapping(value = "/addWalletConsumer", method = RequestMethod.POST)
    @ApiOperation(value = "添加账户消费记录", response = Boolean.class)
    public Object addWalletConsumer(@Validated @RequestBody AddWalletConsumerDto addWalletConsumerDto) {
        //数据拷贝
        WalletConsumer walletConsumer = new WalletConsumer();
        BeanUtils.copyProperties(addWalletConsumerDto, walletConsumer);
        walletConsumer.setId(snowflakeIdWorker.nextId());
        walletConsumer.setOrderNo(snowflakeIdWorker.nextId() + "");
        return WrapMapper.ok(walletConsumerService.save(walletConsumer) > 0);
    }

    /**
     * 删除账户消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 16:06
     */
    @RequestMapping(value = "/delWalletConsumer", method = RequestMethod.GET)
    @ApiOperation(value = "删除账户消费记录", response = Boolean.class)
    public Object delWalletConsumer(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber
            , @RequestParam("id") Long id) {
        return WrapMapper.ok(walletConsumerService.delWalletConsumer(schoolCode, cardNumber, id));
    }


    /**
     * 修改账户消费记录状态
     *
     * @Author: WanMing
     * @Date: 2019/7/12 16:22
     */
    @RequestMapping(value = "/modifyWalletConsumer", method = RequestMethod.POST)
    @ApiOperation(value = "修改账户消费记录状态", response = Boolean.class)
    public Object modifyWalletConsumer(@Validated @RequestBody ModifyWalletConsumerDto modifyWalletConsumerDto) {
        //数据拷贝
        WalletConsumer walletConsumer = new WalletConsumer();
        BeanUtils.copyProperties(modifyWalletConsumerDto, walletConsumer);
        return WrapMapper.ok(walletConsumerService.update(walletConsumer) > 0);
    }

    /**
     * 根据条件查询账户的消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 17:22
     */
    @RequestMapping(value = "/findWalletConsumerByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询账户的消费记录", response = WalletConsumerVo.class)
    public Object findWalletConsumerByCondition(@Validated @RequestBody QueryWalletConsumerDto queryWalletConsumerDto) {
        return WrapMapper.ok(walletConsumerService.findWalletConsumerByCondition(queryWalletConsumerDto));
    }


    /**
     * 根据id查询单条消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 16:06
     */
    @RequestMapping(value = "/findWalletConsumerById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询单条消费记录", response = WalletConsumer.class)
    public Object findWalletConsumerById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber
            , @RequestParam("id") String id){
        return WrapMapper.ok(walletConsumerService.findWalletConsumerById(schoolCode,cardNumber,id));
    }

    /**
     * 导出消费记录到excel文件
     * @Author: WanMing
     * @Date: 2019/7/12 18:33
     */
    @RequestMapping(value = "/exportWalletConsumerList", method = RequestMethod.GET)
    @ApiOperation(value = "导出消费记录")
    public void exportWalletConsumerList(@Validated @RequestBody WalletConsumerExcelDto walletConsumerExcelDto ){

    }

    @PostMapping("/cardStatus")
    @ApiOperation(value = "挂失或者解挂", response = Boolean.class)
    public Object cardStatus(@RequestParam("physicalNumber") String physicalNumber, @RequestParam("physicalStatus") Byte physicalStatus) {
        return WrapMapper.ok();
    }


}