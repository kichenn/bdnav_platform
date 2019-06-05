package com.bdxh.school.contoller;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.BlackUrlQueryDto;
import com.bdxh.school.dto.ModifyBlackUrlDto;
import com.bdxh.school.vo.BlackUrlShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.service.BlackUrlService;
import redis.clients.jedis.JedisCluster;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@RestController
@RequestMapping("/blackUrl")
@Slf4j
@Validated
@Api(value = "Url黑名单控制器", tags = "Url黑名单控制器")
public class BlackUrlController {

    @Autowired
    private BlackUrlService blackUrlService;

    /**
     * @Description: 增加url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/addBlack", method = RequestMethod.POST)
    @ApiOperation(value = "增加url黑名单", response = Boolean.class)
    public Object addBlack(@Validated @RequestBody AddBlackUrlDto addBlackUrlDto) {
        BlackUrl blackUrl = new BlackUrl();
        BeanUtils.copyProperties(addBlackUrlDto, blackUrl);
        blackUrl.setStatus(addBlackUrlDto.getBlackStatusEnum().getKey());
        try {
            blackUrlService.save(blackUrl);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //学校id，黑名单ip
                return WrapMapper.error("该学校黑名单已存在此条url，无需再添加！");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * @Description: 修改url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/modifyBlack", method = RequestMethod.POST)
    @ApiOperation(value = "修改url黑名单", response = Boolean.class)
    public Object modifyBlack(@Validated @RequestBody ModifyBlackUrlDto modifyBlackUrlDto) {
        BlackUrl blackUrl = new BlackUrl();
        BeanUtils.copyProperties(modifyBlackUrlDto, blackUrl);
        if (modifyBlackUrlDto.getBlackStatusEnum() != null) {
            blackUrl.setStatus(modifyBlackUrlDto.getBlackStatusEnum().getKey());
        }
        try {
            blackUrlService.update(blackUrl);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //学校id，黑名单ip
                return WrapMapper.error("该学校黑名单已存在此条url，无需再添加！");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * @Description: 删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/delBlackById", method = RequestMethod.POST)
    @ApiOperation(value = "删除url黑名单", response = Boolean.class)
    public Object delBlackById(@RequestParam("id") Long id) {
        return WrapMapper.ok(blackUrlService.deleteByKey(id) > 0);
    }


    /**
     * @Description: 批量删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/delBlackInIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除url黑名单", response = Boolean.class)
    public Object delBlackInIds(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(blackUrlService.batchDelBlackUrlInIds(ids));
    }

    /**
     * @Description: id查询url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/findBlackById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询url黑名单", response = BlackUrl.class)
    public Object findBlackById(@RequestParam("id") Long id) {
        return WrapMapper.ok(blackUrlService.selectByKey(id));
    }

    /**
     * @Description: 分页查询
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/findBlackInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询", response = BlackUrlShowVo.class)
    public Object findBlackInConditionPaging(@Validated @RequestBody BlackUrlQueryDto blackUrlQueryDto) {
        return WrapMapper.ok(blackUrlService.findBlackInConditionPaging(blackUrlQueryDto));
    }

    /**
     * @Description: 查询当前学校的黑名单列表
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/findBlackInList", method = RequestMethod.GET)
    @ApiOperation(value = " 查询当前学校的黑名单列表", response = BlackUrlShowVo.class)
    public Object findBlackInList(@RequestParam("schoolCode") String schoolCode) {
        List<String> urlList=new ArrayList<>();
       List<BlackUrl> bus= blackUrlService.findBlackInList(schoolCode);
        for (int i = 0; i < bus.size(); i++) {
            urlList.add(bus.get(i).getIp());
        }
        return WrapMapper.ok(urlList);
    }
}