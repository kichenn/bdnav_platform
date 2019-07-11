package com.bdxh.common.helper.weixiao.qrcode;

import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.weixiao.qrcode.constant.CodeConstant;
import com.bdxh.common.helper.weixiao.qrcode.request.CampusCodeRequest;
import com.bdxh.common.utils.HttpClientUtils;
import org.apache.commons.beanutils.BeanMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 校园码相关
 * @Author: Kang
 * @Date: 2019/7/11 14:46
 */
public class QRCodeUtils {

    /**
     * @Description: 解码接口
     * @Author: Kang
     * @Date: 2019/7/11 14:46
     */
    public static String campusCode(CampusCodeRequest campusCodeRequest) {
        Map<String, Object> map = toMap(campusCodeRequest);
        String result = "";
        try {
            result = HttpClientUtils.doPost(CodeConstant.CAMPUS_CODE_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private static Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        } else if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        BeanMap beanMap = new BeanMap(obj);
        Iterator<String> it = beanMap.keyIterator();
        while (it.hasNext()) {
            String name = it.next();
            Object value = beanMap.get(name);
            // 转换时会将类名也转换成属性，此处去掉
            if (value != null && !name.equals("class")) {
                map.put(name, value);
            }
        }
        return map;
    }
}
