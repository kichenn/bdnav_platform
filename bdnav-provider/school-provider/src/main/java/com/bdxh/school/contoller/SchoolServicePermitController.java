package com.bdxh.school.contoller;


import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSchoolServicePermitDto;
import com.bdxh.school.dto.ModifySchoolServicePermitDto;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.service.SchoolServicePermitService;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 学校服务的许可权限控制器
 * @Author wanMing
 * @Date 2019-05-28 10:24:19
 */
@RestController
@RequestMapping("/schoolServicePermit")
@Slf4j
@Validated
@Api(value = "学校服务的许可权限控制器", tags = "学校服务的许可权限控制器API")
public class SchoolServicePermitController {


    @Autowired
    private SchoolServicePermitService schoolServicePermitService;


    /**
     * 增加学校服务许可权限
     *
     * @Author: WanMing
     * @Date: 2019/5/28 11:11
     */
    @RequestMapping(value = "/addSchoolServicePermit", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校服务的许可权限", response = Boolean.class)
    public Object addSchoolServicePermit(@Validated @RequestBody AddSchoolServicePermitDto addSchoolServicePermitDto) {
        //保证一个学校只能存在一条服务许可记录,数据库创建唯一索引
        //数据拷贝
        SchoolServicePermit schoolServicePermit = new SchoolServicePermit();
        BeanUtils.copyProperties(addSchoolServicePermitDto, schoolServicePermit);
        schoolServicePermit.setStatus(addSchoolServicePermitDto.getStatus().getKey());
        //添加
        try {
            return WrapMapper.ok(schoolServicePermitService.save(schoolServicePermit) > 0);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                return WrapMapper.error("此学校许可记录已存在");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    /**
     * 修改学校服务许可权限
     *
     * @Author: WanMing
     * @Date: 2019/5/28 14:26
     */
    @RequestMapping(value = "/modifySchoolServicePermit", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校服务的许可权限", response = Boolean.class)
    public Object modifySchoolServicePermit(@Validated @RequestBody ModifySchoolServicePermitDto modifySchoolServicePermitDto) {
        //保证一个学校只能存在一条服务许可记录数据库创建唯一索引
        //信息拷贝
        SchoolServicePermit schoolServicePermit = new SchoolServicePermit();
        BeanUtils.copyProperties(modifySchoolServicePermitDto, schoolServicePermit);
        schoolServicePermit.setStatus(modifySchoolServicePermitDto.getStatus() != null ? modifySchoolServicePermitDto.getStatus().getKey() : null);
        //修改
        try {
            return WrapMapper.ok(schoolServicePermitService.update(schoolServicePermit) > 0);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                return WrapMapper.error("此学校许可记录已存在");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }


    /**
     * 分页查询学校服务许可权限信息
     *
     * @Author: WanMing
     * @Date: 2019/5/28 17:07
     */
    @RequestMapping(value = "/findSchoolServicePermitInConditionPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询学校服务许可权限信息", response = SchoolServicePermitShowVo.class)
    public Object findSchoolServicePermitInConditionPage(@Validated @RequestBody SchoolServicePermitQueryDto schoolServicePermitQueryDto) {
        //分页查询
        return WrapMapper.ok(schoolServicePermitService.findSchoolServicePermitInConditionPage(schoolServicePermitQueryDto));
    }


    /**
     * 根据id查询学校服务许可权限信息
     *
     * @Author: WanMing
     * @Date: 2019/5/28 17:13
     */
    @RequestMapping(value = "/findSchoolServicePermitById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询学校服务许可权限信息", response = SchoolServicePermit.class)
    public Object findSchoolServicePermitById(@RequestParam(name = "id") Long id) {
        return WrapMapper.ok(schoolServicePermitService.findSchoolServicePermitById(id));
    }

}