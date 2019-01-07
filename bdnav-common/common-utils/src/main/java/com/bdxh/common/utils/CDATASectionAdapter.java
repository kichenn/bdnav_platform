package com.bdxh.common.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @description: JAXB工具类
 * @author: xuyuan
 * @create: 2019-01-02 16:51
 **/
public class CDATASectionAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) {
        return v;
    }

    @Override
    public String marshal(String v) {
        if(v != null){
            return "<![CDATA["+v+"]]>";
        }
        return null;
    }
}
