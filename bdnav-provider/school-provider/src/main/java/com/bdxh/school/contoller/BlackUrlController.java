package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.ModifyBlackUrlDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.service.BlackUrlService;

import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Controller
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
    @ResponseBody
    public Object addSchool(@Validated @RequestBody AddBlackUrlDto addBlackUrlDto) {
        BlackUrl blackUrl = new BlackUrl();
        BeanUtils.copyProperties(addBlackUrlDto, blackUrl);
        blackUrl.setStatus(addBlackUrlDto.getBlackStatusEnum().getKey());
        return WrapMapper.ok(blackUrlService.save(blackUrl) > 0);
    }

    /**
     * @Description: 修改url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/modifyBlack", method = RequestMethod.POST)
    @ApiOperation(value = "修改url黑名单", response = Boolean.class)
    @ResponseBody
    public Object modifyBlack(@Validated @RequestBody ModifyBlackUrlDto modifyBlackUrlDto) {
        BlackUrl blackUrl = new BlackUrl();
        BeanUtils.copyProperties(modifyBlackUrlDto, blackUrl);
        if (modifyBlackUrlDto.getBlackStatusEnum() != null) {
            blackUrl.setStatus(modifyBlackUrlDto.getBlackStatusEnum().getKey());
        }
        return WrapMapper.ok(blackUrlService.update(blackUrl) > 0);
    }

    /**
     * @Description: 删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/delBlackById", method = RequestMethod.POST)
    @ApiOperation(value = "删除url黑名单", response = Boolean.class)
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public Object findBlackById(@RequestParam("id") Long id) {
        return WrapMapper.ok(blackUrlService.selectByKey(id));
    }

    /**
     * @Description: schoolId查询url黑星名单（分页）
     * @Author: Kang
     * @Date: 2019/4/11 10:33
     */
    @RequestMapping(value = "/findBlackBySchoolIdInConditionPaging", method = RequestMethod.GET)
    @ApiOperation(value = "schoolId查询url黑名单（分页）", response = BlackUrl.class)
    @ResponseBody
    public Object findBlackBySchoolIdInConditionPaging(@RequestParam("id") Long id) {
        return WrapMapper.ok();
    }

    /**
     * @Description: 分页查询
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/findBlackInConditionPaging", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询", response = BlackUrl.class)
    @ResponseBody
    public Object findBlackInConditionPaging(@Validated @RequestBody BlackUrl blackUrl) {
        return WrapMapper.ok();
    }
}