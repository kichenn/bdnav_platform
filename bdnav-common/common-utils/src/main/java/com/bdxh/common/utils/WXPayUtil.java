package com.bdxh.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WXPayUtil {

	/**
	 * XML格式字符串转换为Map
	 *
	 * @param strXML XML字符串
	 * @return XML数据转换后的Map
	 * @throws Exception
	 */
	public static SortedMap<String, String> xmlToMap(String strXML) throws Exception {
		SortedMap<String, String> data = new TreeMap<>();
		DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
		org.w3c.dom.Document doc = documentBuilder.parse(stream);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		for (int idx = 0; idx < nodeList.getLength(); ++idx) {
			Node node = nodeList.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				org.w3c.dom.Element element = (org.w3c.dom.Element) node;
				data.put(element.getNodeName(), element.getTextContent());
			}
		}
		stream.close();
		return data;
	}

	/**
	 * 将Map转换为XML格式的字符串
	 *
	 * @param data Map类型数据
	 * @return XML格式的字符串
	 * @throws Exception
	 */
	public static String mapToXml(Map<String, String> data) throws Exception {
		org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
		org.w3c.dom.Element root = document.createElement("xml");
		document.appendChild(root);
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (StringUtils.isNotEmpty(value)){
				value = value.trim();
				org.w3c.dom.Element filed = document.createElement(key);
				filed.appendChild(document.createTextNode(value));
				root.appendChild(filed);
			}
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(document);
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
		String output = writer.getBuffer().toString();
		writer.close();
		return output;
	}

}
