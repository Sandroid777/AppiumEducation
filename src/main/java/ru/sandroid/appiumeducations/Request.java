package ru.sandroid.appiumeducations;

import io.netty.handler.codec.http.HttpRequest;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Request {
    private HttpRequest request;
    private HttpMessageContents contents;
    private HttpMessageInfo messageInfo;
    private long requestTime;
    List<Map.Entry<String, String>> allHeaders;

    public Request(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        this.request = request;
        this.contents = contents;
        this.messageInfo = messageInfo;
        this.requestTime = new Date().getTime();
        allHeaders = getAllHeader();
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpMessageContents getContents() {
        return contents;
    }

    public HttpMessageInfo getMessageInfo() {
        return messageInfo;
    }

    public long getRequestTime() {
        return requestTime;
    }


    public List<Map.Entry<String, String>> getAllHeader() {
        return request.headers().entries();
    }

    public URL getRequestURL() {
        try {
            return new URL(messageInfo.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public URL getReferer() {
        for (Map.Entry<String, String> value : allHeaders) {
            {
                if (value.getKey().equals("Referer")) {
                    try {
                        return new URL(value.getValue());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public Boolean refererIsNull() {
        for (Map.Entry<String, String> value : allHeaders) {
            {
                if (value.getKey().equals("Referer")) {
                    return false;
                }
            }
         }
        return true;
    }
}
