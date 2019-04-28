package com.bdxh.school.contoller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.AddSchoolFenceDto;
import com.bdxh.school.dto.ModifySchoolFenceDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.BlackUrl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.service.SchoolFenceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@RestController
@RequestMapping("/schoolFence")
@Slf4j
@Validated
@Api(value = "学校围栏", tags = "学校围栏交互API")
public class SchoolFenceController {

    @Autowired
    private SchoolFenceService schoolFenceService;

    @RequestMapping(value = "/addFence", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校围栏", response = Boolean.class)
    public Object addFence(@Validated @RequestBody AddSchoolFenceDto addSchoolFenceDto) {
        SchoolFence schoolFence = new SchoolFence();
        BeanUtils.copyProperties(addSchoolFenceDto, schoolFence);
        //设置状态值
        schoolFence.setGroupType(addSchoolFenceDto.getGroupTypeEnum().getKey());
        schoolFence.setRecursionPermission(addSchoolFenceDto.getRecursionPermissionStatusEnum().getKey());
        schoolFence.setStatus(addSchoolFenceDto.getBlackStatusEnum().getKey());
        try {
            Boolean result = schoolFenceService.addFence(schoolFence, addSchoolFenceDto.getFenceEntityDtos());
            return WrapMapper.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/modifyFence", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校围栏", response = Boolean.class)
    public Object modifyFence(@Validated @RequestBody ModifySchoolFenceDto modifySchoolFenceDto) {
        SchoolFence schoolFence = new SchoolFence();
        BeanUtils.copyProperties(modifySchoolFenceDto, schoolFence);
        //设置状态值
        if (modifySchoolFenceDto.getGroupTypeEnum() != null) {
            schoolFence.setGroupType(modifySchoolFenceDto.getGroupTypeEnum().getKey());
        }
        if (modifySchoolFenceDto.getRecursionPermissionStatusEnum() != null) {
            schoolFence.setRecursionPermission(modifySchoolFenceDto.getRecursionPermissionStatusEnum().getKey());
        }
        if (modifySchoolFenceDto.getBlackStatusEnum() != null) {
            schoolFence.setStatus(modifySchoolFenceDto.getBlackStatusEnum().getKey());
        }

        try {
            Boolean result = schoolFenceService.modifyFence(schoolFence, modifySchoolFenceDto.getFenceEntityDtos());
            return WrapMapper.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/delFenceById", method = RequestMethod.POST)
    @ApiOperation(value = "删除学校围栏", response = Boolean.class)
    public Object delFenceById(@RequestParam("id") Long id) {
        try {
            Boolean result = schoolFenceService.delFence(id);
            return WrapMapper.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/findFenceById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询学校围栏", response = SchoolFence.class)
    public Object findFenceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolFenceService.selectByKey(id));
    }

    @RequestMapping(value = "/findFenceInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页学校围栏查询", response = SchoolFence.class)
    public Object findFenceInConditionPaging(@Validated @RequestBody SchoolFenceQueryDto schoolFenceQueryDto) {
        return WrapMapper.ok(schoolFenceService.findFenceInConditionPaging(schoolFenceQueryDto));
    }

    @RequestMapping(value = "/fencePush", method = RequestMethod.POST)
    @ApiOperation(value = "围栏报警推送消息", response = SchoolFence.class)
    public void fencePush(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        System.err.println("报警小推送。。。。。。。。");
/*//       验证此方法。方法验证完成注释，开始获取推送信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", 1);
        jsonObject.put("service_id", FenceConstant.SERVICE_ID);
        String str = jsonObject.toJSONString();
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
        httpServletResponse.setHeader("SignId","baidu_yingyan");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));*/
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("type", 2);
//        jsonObject.put("service_id", FenceConstant.SERVICE_ID);
//        String str = jsonObject.toJSONString();
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
//        httpServletResponse.setHeader("SignId", "baidu_yingyan");
//        httpServletResponse.setCharacterEncoding("utf-8");
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));

        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[4096];

        InputStream is = request.getInputStream();
        for (int n; (n = is.read(b)) != -1; ) {
            sb.append(new String(b, 0, n));
        }
        log.info("百度回调内容：" + sb.toString());

        JSONObject resultJson = (JSONObject) JSON.parse(sb.toString());
        if (resultJson != null) {
            //获取围栏报警信息
            JSONArray jsonArray = resultJson.getJSONArray("content");

//            for (int i=0)
        }


        JSONObject statusjson = new JSONObject();
        statusjson.put("status", 0);
        statusjson.put("message", "成功");
        httpServletResponse.setHeader("SignId", "baidu_yingyan");
        httpServletResponse.getWriter().write(statusjson.toJSONString());
    }
}