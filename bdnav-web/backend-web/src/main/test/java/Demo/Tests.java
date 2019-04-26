package Demo;

import com.bdxh.backend.configration.security.properties.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

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
}
