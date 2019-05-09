package Demo;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.backend.configration.security.properties.SecurityConstant;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.HttpPoolClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 测试相关 方法
 * @Author: Kang
 * @Date: 2019/4/25 12:23
 */
@Slf4j
public class Tests {


    /**
     * ceshi  redis中存储的时间
     */
    @Test
    public void test1() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME * 60 * 1000);
        log.info("时间:{}", date);
    }

    /**
     * 删除监控对象
     */
    @Test
    public void test2() {
        String entityResult = FenceUtils.deleteNewEntity("569849809515970560_李文瀚");
        JSONObject entityResultJson = JSONObject.parseObject(entityResult);
        if (entityResultJson.getInteger("status") != 0) {
            throw new RuntimeException("删除围栏中监控对象失败,状态码" + entityResultJson.getInteger("status") + "，原因:" + entityResultJson.getString("message"));
        }
    }

    /**
     * 查询该围栏底下所有监控对象
     */
    @Test
    public void test3() {
        String url = "";
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);

        try {
            String result = HttpClientUtils.doGet(FenceConstant.SEL_ROUND_IN_ENTITY_URL, map);
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
