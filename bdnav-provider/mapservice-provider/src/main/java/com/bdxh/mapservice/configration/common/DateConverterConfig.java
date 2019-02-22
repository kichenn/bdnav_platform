package com.bdxh.mapservice.configration.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 日期转换
 * @author: xuyuan
 * @create: 2019-01-21 16:45
 **/
@Component
@Slf4j
public class DateConverterConfig implements Converter<String, Date> {

    private static final List<String> formarts = new ArrayList<>(3);

    static{
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm:ss");
        formarts.add("yyyyMMddHHmmss");
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)){
            return null;
        }
        source = source.trim();
        if(source.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return parseDate(source, formarts.get(0));
        }
        else if(source.matches("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$")){
            return parseDate(source, formarts.get(1));
        }
        else if(source.matches("^\\d{14}$")){
            return parseDate(source, formarts.get(2));
        }else {
            log.error("Invalid date value '" + source + "'");
            throw new IllegalArgumentException("Invalid date value '" + source + "'");
        }
    }

    /**
     * 字符串转日期
     * @param dateStr
     * @param format
     * @return
     */
    public  Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
        }
        return date;
    }

}
