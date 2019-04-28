package yingyan;

import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.school.dto.FenceEntityDto;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 添加轨迹信息，用于测试围栏报警
 * @Author: Kang
 * @Date: 2019/4/24 17:37
 */
public class LocusTest {

    /**
     * 上传单个轨迹点 URL POST请求
     */
    private static String addPoint = "http://yingyan.baidu.com/api/v3/track/addpoint";

    /**
     * 根据坐标查询监控对象相对围栏的状态 URL GET请求
     */
    private static String status = "http://yingyan.baidu.com/api/v3/fence/querystatusbylocation";

    /**
     * 查询某监控对象的围栏报警信息
     */
    private static String historyalarm = "http://yingyan.baidu.com/api/v3/fence/historyalarm";

    /**
     * 查询监控对象状态
     */
    @Test
    public void selStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("monitored_person", "恐龙学");
        map.put("latitude", 10);
        map.put("longitude", 80);
        map.put("coord_type", "bd09ll");
        String result = "";
        try {
            result = HttpClientUtils.doGet(status, map);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询某监控对象的围栏报警信息
     */
    @Test
    public void selHistoryalarm() {
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("monitored_person", "恐龙学");
        String result = "";
        try {
            result = HttpClientUtils.doGet(historyalarm, map);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加轨迹点
     */
    @Test
    public void addPoint() {
        //获取系统当前 unix时间戳
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String t = df.format(d);
        long epoch = 0;
        try {
            epoch = df.parse(t).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("entity_name", "恐龙学");
        map.put("latitude", 25);
        map.put("longitude", 117);
        map.put("loc_time", epoch);
        map.put("coord_type_input", "bd09ll");
        String result = "";
        try {
            result = HttpClientUtils.doPost(addPoint, map);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试list切割
     */
    @Test
    public void testList() {

        List<String> strs = new ArrayList<>();
        strs.add("张三");
        strs.add("李四");
        strs.add("王五");
        strs.add("赵六");
        strs.add("田七");
        String s = String.join(",", strs);
        System.err.println(s);
    }

    @Test
    public void test1() {
        List<FenceEntityDto> modifyFenceEntitys = new ArrayList<>();
        FenceEntityDto dto = new FenceEntityDto();
        dto.setId(new Long("1"));
        dto.setName("测试");
        modifyFenceEntitys.add(dto);
        List<String> fenceEntityNames = modifyFenceEntitys.stream().map(e -> {
            return e.getId() + "_" + e.getName();
        }).collect(Collectors.toList());

        System.out.println(String.join(",", fenceEntityNames));
    }
}
