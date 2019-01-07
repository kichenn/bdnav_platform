package com.bdxh.common.utils;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @description: XML对象转换类
 * @author: xuyuan
 * @create: 2018-12-17 16:57
 **/
public class XmlUtils {

    public static <T> String toXML(T t) {
        try {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xm头声明信息
            //不进行转义字符的处理
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                public void escape(char[] ch, int start,int length, boolean isAttVal, Writer writer) throws IOException {
                    writer.write(ch, start, length);
                }
            });
            StringWriter writer = new StringWriter();
            marshaller.marshal(t, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXML(String xml, Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
