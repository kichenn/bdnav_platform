package com.bdxh.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: HttpPoolClient工具类
 * @author: xuyuan
 * @create: 2019-02-18 20:03
 **/
@Slf4j
public class HttpPoolClientUtils {

    private static final String ENCODING = "UTF-8";

    private PoolingHttpClientConnectionManager connectionManager;

    private CloseableHttpClient httpClient;

    private IdleConnectionMonitorThread monitorThread;

    /**
     * 最大连接数
     */
    private int maxTotal;

    /**
     * 并发数
     */
    private int maxPerRoute;

    /**
     * 获取连接超时时间
     */
    private int connectRequestTimeout;

    /**
     * 连接超时时间
     */
    private int connectTimeout;

    /**
     * 读取超时时间
     */
    private int socketTimeout;

    /**
     * httpclient连接池参数
     *
     * @param maxTotal
     * @param maxPerRoute
     * @param connectRequestTimeout
     * @param connectTimeout
     * @param socketTimeout
     */
    public HttpPoolClientUtils(int maxTotal, int maxPerRoute, int connectRequestTimeout, int connectTimeout, int socketTimeout) {
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.connectRequestTimeout = connectRequestTimeout;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }

    /**
     * 发送get请求
     * @param url 请求地址 参数?key1=value1&key2=value2 此时参数不会编码
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        return doGet(url,null);
    }

    /**
     * 发送get请求
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        //设置请求参数
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        //设置请求头
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, Object>> entrySet = headers.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                httpGet.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        String result = null;
        //执行请求
        //响应结果Content-Type: text/plain;charset=UTF-8
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    //获取结果
                    result = EntityUtils.toString(httpEntity, ENCODING);
                }
            }
        }
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送post请求
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, Object> params) throws Exception {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        //创建http对象
        HttpPost httpPost = new HttpPost(url);
        //设置请求头 默认Content-Type：text/plain;charset=UTF-8
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, Object>> entrySet = headers.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //设置请求参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            //请求参数Content-Type：application/x-www-form-urlencoded; charset=UTF-8
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
        String result = null;
        //执行请求
        //响应结果Content-Type: text/plain;charset=UTF-8
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    //获取结果
                    result = EntityUtils.toString(httpEntity, ENCODING);
                }
            }
        }
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送post请求 JSON数据
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public String doPostJson(String url, String param) throws Exception {
        //创建http对象
        HttpPost httpPost = new HttpPost(url);
        //设置请求参数Content-Type: application/json; charset=UTF-8
        StringEntity stringEntity = new StringEntity(param, ENCODING);
        stringEntity.setContentEncoding(ENCODING);
        stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8"));
        httpPost.setEntity(stringEntity);
        String result = null;
        //执行请求
        //响应结果Content-Type: application/json; charset=UTF-8 String时Content-Type: text/plain;charset=UTF-8
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    //获取结果
                    result = EntityUtils.toString(httpEntity, ENCODING);
                }
            }
        }
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送post请求 xml数据
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public String doPostXml(String url, String param) throws Exception {
        //创建http对象
        HttpPost httpPost = new HttpPost(url);
        //设置请求参数Content-Type: application/xml; charset=UTF-8
        StringEntity stringEntity = new StringEntity(param, ENCODING);
        stringEntity.setContentEncoding(ENCODING);
        stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/xml; charset=UTF-8"));
        httpPost.setEntity(stringEntity);
        String result = null;
        //执行请求
        //响应结果Content-Type: application/json; charset=UTF-8 String时Content-Type: text/plain;charset=UTF-8
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    //获取结果
                    result = EntityUtils.toString(httpEntity, ENCODING);
                }
            }
        }
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 初始化方法
     */
    public void init() {
        log.info("httpPoolClientUtils初始化开始--------------------------");
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(this.maxTotal);
        connectionManager.setDefaultMaxPerRoute(this.maxPerRoute);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(this.connectRequestTimeout)
                .setConnectTimeout(this.connectTimeout)
                .setSocketTimeout(this.socketTimeout)
                .build();
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClient = httpClientBuilder
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        monitorThread = new IdleConnectionMonitorThread(connectionManager);
        monitorThread.start();
        log.info("httpPoolClientUtils初始化结束--------------------------");
    }

    /**
     * 销毁方法
     */
    public void shutdown() {
        log.info("httpPoolClientUtils销毁开始--------------------------");
        try {
            monitorThread.shutdown();
            httpClient.close();
            connectionManager.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("httpPoolClientUtils销毁结束--------------------------");
    }

    /**
     * 清理失效连接
     */
    class IdleConnectionMonitorThread extends Thread {

        private HttpClientConnectionManager connectionManager;

        private volatile boolean shutdown = false;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        //等待5s
                        wait(5000);
                        log.info("httpPoolClientUtils开始清理失效连接-----------------------");
                        //关闭失效的连接
                        connectionManager.closeExpiredConnections();
                        //关闭30秒内不活动的连接
                        connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }

}
