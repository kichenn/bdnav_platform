package com.bdxh.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: HttpClient工具类
 * @author: xuyuan
 * @create: 2019-02-18 20:03
 **/
public class HttpClientUtils {

    private static final int DEFAULT_POOL_MAX_TOTAL = 2000;
    private static final int DEFAULT_POOL_MAX_PER_ROUTE = 200;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 10000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 5000;

    private PoolingHttpClientConnectionManager gcm;

    private CloseableHttpClient httpClient;

    private IdleConnectionMonitorThread idleThread;

    // 连接池的最大连接数
    private final int maxTotal;
    // 连接池按route配置的最大连接数
    private final int maxPerRoute;

    // tcp connect的超时时间
    private final int connectTimeout;
    // 从连接池获取连接的超时时间
    private final int connectRequestTimeout;
    // tcp io的读写超时时间
    private final int socketTimeout;

    public HttpClientUtils() {
        this(
                HttpClientUtils.DEFAULT_POOL_MAX_TOTAL,
                HttpClientUtils.DEFAULT_POOL_MAX_PER_ROUTE,
                HttpClientUtils.DEFAULT_CONNECT_TIMEOUT,
                HttpClientUtils.DEFAULT_CONNECT_REQUEST_TIMEOUT,
                HttpClientUtils.DEFAULT_SOCKET_TIMEOUT
        );
    }

    public HttpClientUtils(int maxTotal, int maxPerRoute, int connectTimeout, int connectRequestTimeout, int socketTimeout) {
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.connectTimeout = connectTimeout;
        this.connectRequestTimeout = connectRequestTimeout;
        this.socketTimeout = socketTimeout;
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        this.gcm = new PoolingHttpClientConnectionManager(registry);
        this.gcm.setMaxTotal(this.maxTotal);
        this.gcm.setDefaultMaxPerRoute(this.maxPerRoute);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(this.connectTimeout)                     // 设置连接超时
                .setSocketTimeout(this.socketTimeout)                       // 设置读取超时
                .setConnectionRequestTimeout(this.connectRequestTimeout)    // 设置从连接池获取连接实例的超时
                .build();
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClient = httpClientBuilder
                .setConnectionManager(this.gcm)
                .setDefaultRequestConfig(requestConfig)
                .build();
        idleThread = new IdleConnectionMonitorThread(this.gcm);
        idleThread.start();
    }

    public String doGet(String url) {
        return this.doGet(url, Collections.EMPTY_MAP, Collections.EMPTY_MAP);
    }

    public String doGet(String url, Map<String, Object> params) {
        return this.doGet(url, Collections.EMPTY_MAP, params);
    }

    public String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        //设置请求参数
        String paramUrl = getUrlWithParams(url, params);
        HttpGet httpGet = new HttpGet(paramUrl);
        //设置headers
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response != null && response.getStatusLine() != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entityRes = response.getEntity();
                    if (entityRes != null) {
                        return EntityUtils.toString(entityRes, "UTF-8");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String doPost(String url, Map<String, Object> params) {
        return this.doPost(url, Collections.EMPTY_MAP, params);
    }

    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        //设置headers
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //设置请求参数
        if (params != null && params.size() > 0) {
            HttpEntity entityReq = getUrlEncodedFormEntity(params);
            httpPost.setEntity(entityReq);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine() != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = response.getEntity();
                    if (httpEntity != null) {
                        return EntityUtils.toString(httpEntity, "UTF-8");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private HttpEntity getUrlEncodedFormEntity(Map<String, Object> params) {
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        return new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8"));
    }

    private String getUrlWithParams(String url, Map<String, Object> params) {
        boolean first = true;
        StringBuilder sb = new StringBuilder(url);
        for (String key : params.keySet()) {
            char ch = '&';
            if (first == true) {
                ch = '?';
                first = false;
            }
            String value = params.get(key).toString();
            try {
                String encoderValue = URLEncoder.encode(value, "UTF-8");
                sb.append(ch).append(key).append("=").append(encoderValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void shutdown() {
        idleThread.shutdown();
    }

    //监控异常连接
    private class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;

        private volatile boolean exitFlag = false;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            this.connMgr = connMgr;
            setDaemon(true);
        }

        @Override
        public void run() {
            while (!this.exitFlag) {
                synchronized (this) {
                    try {
                        this.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //关闭失效的连接
                connMgr.closeExpiredConnections();
                //关闭30秒内不活动的连接
                connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
            }
        }

        public void shutdown() {
            this.exitFlag = true;
            synchronized (this) {
                notify();
            }
        }

    }
}
