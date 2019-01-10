package com.bdxh.oneCard.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.oneCard.fallback.OneCardControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@FeignClient(value = "oneCard-provider-cluster",fallback= OneCardControllerFallback.class)
@RequestMapping("/oneCardNumber")
public interface OneCardControllerClient {
    /**
     * 获取学校基本信息
     * @param schoolCode
     * @return
     */
    @RequestMapping("/changeRechargeLog")
    @ResponseBody
    Wrapper querySchoolLog(@RequestParam(name="schoolCode")String schoolCode);
}
