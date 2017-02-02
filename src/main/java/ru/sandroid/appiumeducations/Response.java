package ru.sandroid.appiumeducations;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;


//TODO добавить метод получения json контента
public class Response {

    private HttpResponse response;
    private HttpMessageContents contents;
    private HttpMessageInfo messageInfo;
    private long responseTime;

    public Response(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        this.response = response;
        this.contents = contents;
        this.messageInfo = messageInfo;
        this.responseTime = new Date().getTime();
    }

    public HttpResponse getResponse() {
            return response;
        }

    public URL getResponseURL() {
        try {
            return new URL(messageInfo.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getContents() {
            return contents.toString();
        }
    public HttpMessageInfo getMessageInfo() {
            return messageInfo;
        }
    public long getResponseTime() {
            return responseTime;
        }

}
