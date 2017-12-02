package com.github.novel.common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {
    private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
    private static CloseableHttpClient httpClient = null;
    private static Integer CONNECT_TIMEOUT = 10000;
    private static Integer READ_TIMEOUT = 10000;

    static {
        poolingHttpClientConnectionManager.setMaxTotal(1025);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
    }

    private HttpClientUtils() {
    }

    /**
     * @param url
     * @param encode
     * @return
     */
    public static String invokeGet(String url, String encode) {
        return invokeGet(url, null, encode, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * @param url
     * @param params
     * @param encode
     * @return
     */
    public static String invokeGet(String url, Map<String, String> params, String encode) {
        return invokeGet(url, params, encode, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * json格式提交
     *
     * @param requestUrl
     * @param params
     * @param encode
     * @return
     */
    public static String invokePostByStringEntity(String requestUrl, Map<String, Object> params, String encode) {
        return invokePostByStringEntity(requestUrl, params, encode, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 当没有BA认证时，clientId、clientSecret都传null或空字符串
     *
     * @param requestUrl
     * @param params
     * @param encode
     * @return
     */
    public static String invokePost(String requestUrl, Map<String, Object> params, String encode) {
        return invokePost(requestUrl, params, encode, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * @param url
     * @param params
     * @param encode
     * @param connectTimeout
     * @param readTimeout
     * @return
     */
    public static String invokeGet(String url, Map<String, String> params, String encode, int connectTimeout, int readTimeout) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        String content;
        String requestUrl = buildRequestUrl(url, params);
        HttpGet httpGet = new HttpGet(requestUrl);
        httpGet.setConfig(requestConfig);
        content = doRequest(httpGet, encode);
        if (StringUtils.isBlank(content)) {
            retryTimes(httpGet, 3, encode);
        }
        return content;
    }

    /**
     * @param requestUrl
     * @param params
     * @param encode
     * @param connectTimeout
     * @param readTimeout
     * @return
     */
    public static String invokePost(String requestUrl, Map<String, Object> params, String encode, int connectTimeout, int readTimeout) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        String content;
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.setConfig(requestConfig);
        buildPostParams(httpPost, params, encode);
        content = doRequest(httpPost, encode);
        if (StringUtils.isBlank(content)) {
            retryTimes(httpPost, 3, encode);
        }
        return content;
    }

    /**
     * json格式提交
     *
     * @param requestUrl
     * @param params
     * @param encode
     * @param connectTimeout
     * @param readTimeout
     * @return
     */
    public static String invokePostByStringEntity(String requestUrl, Map<String, Object> params, String encode, int connectTimeout, int readTimeout) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        String responseString;
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.setConfig(requestConfig);
        StringEntity s = new StringEntity(JsonUtils.object2Json(params), encode);
        httpPost.setEntity(s);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        responseString = doRequest(httpPost, encode);
        if (StringUtils.isBlank(responseString)) {
            retryTimes(httpPost, 3, encode);
        }
        return responseString;
    }

    private static String doRequest(HttpRequestBase httpRequestBase, String encode) {
        String responseString = null;
        long startTime = System.currentTimeMillis();
        try {
            CloseableHttpResponse response = httpClient.execute(httpRequestBase);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                String result = StringUtils.isNotBlank(encode) ? EntityUtils.toString(entity, encode) : EntityUtils.toString(entity);
                if (entity != null) {
                    entity.getContent().close();
                }
                return result;
            }
        } catch (Exception e) {
            log.error(String.format("HttpClientUtils.doRequest invoke get error, url:%s", httpRequestBase.getURI()), e);
            responseString = "";
        } finally {
            httpRequestBase.releaseConnection();
            log.info(String.format("HttpClientUtils.doRequest invoke  method {}  url {} cost {}ms", httpRequestBase.getMethod(),
                                   httpRequestBase.getURI()), System.currentTimeMillis() - startTime);
        }
        return responseString;
    }

    private static String retryTimes(HttpRequestBase httpRequestBase, Integer times, String encode) {
        String content = null;
        int i = 0;
        while (StringUtils.isBlank(content) && i < times) {
            content = doRequest(httpRequestBase, encode);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info("重试第 {}次 uri {}", i, httpRequestBase.getURI());
            }
        }
        return content;
    }

    private static String buildRequestUrl(String url, Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            return url;
        }
        StringBuilder requestUrl = new StringBuilder();
        requestUrl.append(url);
        try {
            int i = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (i == 0) {
                    requestUrl.append("?");
                }
                requestUrl.append(entry.getKey());
                requestUrl.append("=");
                String value = entry.getValue();
                requestUrl.append(URLEncoder.encode(value, "UTF-8"));
                requestUrl.append("&");
                i++;
            }
        } catch (UnsupportedEncodingException e) {
            log.error("encode http get params error, params is " + params, e);
            return "";
        }
        requestUrl.deleteCharAt(requestUrl.length() - 1);
        return requestUrl.toString();
    }

    /**
     * @param httpPost
     * @param params   参数内部会转成json格式，最外层是表单post
     * @param encode
     */
    private static void buildPostParams(HttpPost httpPost, Map<String, Object> params, String encode) {
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            try {
                for (String key : params.keySet()) {
                    nameValuePairs.add(new BasicNameValuePair(key, JsonUtils.object2Json(params.get(key))));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encode));
            } catch (UnsupportedEncodingException e) {
                log.error("HttpClientUtils.buildPostParams error, params = " + params, e);
            }
        }

    }

    public static String invokeUploadFile(String requestUrl, Map<String, Object> params, File file, String encode, int connectTimeout, int readTimeout) {
        FileBody fileBody = new FileBody(file);

        return uploadContentBody(requestUrl, params, encode, connectTimeout, readTimeout, fileBody);
    }

    private static String uploadContentBody(String requestUrl, Map<String, Object> params, String encode, int connectTimeout, int readTimeout, ContentBody contentBody) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        String responseString;
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.setConfig(requestConfig);
        buildPostParams(httpPost, params, encode);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addPart("file", contentBody);
        httpPost.setEntity(multipartEntityBuilder.build());

        responseString = doRequest(httpPost, encode);

        return responseString;
    }

    public static String invokeUploadFile(String requestUrl, Map<String, Object> params, byte[] bytes, String encode, int connectTimeout, int readTimeout, String clientId,
                                          String clientSecret, String fileName) {
        ByteArrayBody byteArrayBody = new ByteArrayBody(bytes, ContentType.DEFAULT_BINARY, fileName);
        return uploadContentBody(requestUrl, params, encode, connectTimeout, readTimeout, byteArrayBody);
    }

    public static boolean downloadRemoteFile(String fileRemotePUrl, String localFilePath, String localFileName, int connectTimeout, int readTimeout) {
        boolean result = false;
        // new一个URL对象
        URL url = null;
        FileOutputStream outStream = null;
        try {
            url = new URL(fileRemotePUrl);
            // 打开链接
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            // 设置请求方式为"GET"
            connect.setRequestMethod("GET");
            connect.setConnectTimeout(connectTimeout);
            connect.setReadTimeout(readTimeout);
            // 通过输入流获取图片数据
            InputStream inStream = connect.getInputStream();
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            // new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(localFilePath + localFileName);
            // 创建输出流
            outStream = new FileOutputStream(imageFile);
            // 写入数据
            outStream.write(data);
            // 关闭输出流
            outStream.close();
            result = true;
        } catch (MalformedURLException e) {
            log.warn(String.format("HttpClientUtils.doRequest 下载图片信息 MalformedURLException, url :%s", fileRemotePUrl), e);
        } catch (FileNotFoundException e) {
            log.warn(String.format("HttpClientUtils.doRequest 下载图片信息 FileNotFoundException, url :%s", fileRemotePUrl), e);
        } catch (Exception e) {
            log.error(String.format("HttpClientUtils.doRequest 下载图片信息 Exception, url :%s", fileRemotePUrl), e);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流异常" + fileRemotePUrl, e);
                }
            }
        }
        return result;
    }

    private static byte[] readInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串,1K的缓冲区
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        try {
            while ((len = inStream.read(buffer)) != -1) {
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }

        } catch (IOException e) {
            log.error("读取图片信息异常！！！", e);
        } finally {
            // 关闭输入流
            inStream.close();
        }
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
