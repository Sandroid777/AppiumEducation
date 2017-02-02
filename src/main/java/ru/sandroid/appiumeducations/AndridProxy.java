package ru.sandroid.appiumeducations;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.net.InetAddress.getLocalHost;


public class AndridProxy {

    private BrowserMobProxyServer server;
    private List<Request> requestList;
    private List<Response> responseList;
    private ExecutorService executorService;

    //Старый вариант для работы T007_Proxy_Test
    public  void addInterceptRequest() throws UnknownHostException {

        requestList = Collections.synchronizedList(new ArrayList<Request>());
        executorService = Executors.newFixedThreadPool(1);

        server.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(final HttpRequest request, final HttpMessageContents contents, final HttpMessageInfo messageInfo) {
                requestList.add(new Request(request, contents, messageInfo));
                return null;
            }
        });
    }

    AndridProxy(){
        server = new BrowserMobProxyServer();
    }

    public void startProxy() throws UnknownHostException {
        server.start(0, getLocalHost());
    }

    public void replaceJsonInResponse(final List<String> hostList, final List<String> jsonList){

        responseList = Collections.synchronizedList(new ArrayList<Response>());
        executorService = Executors.newFixedThreadPool(1);//TODO возможно не надо уже

        server.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {

                //бегу по масиву хостов
                for(int i = 0; i < hostList.size(); i++){
                    //Если хост совпадает делаю подмену jsona
                    if(messageInfo.getUrl().contains(hostList.get(i))){
                        contents.setTextContents(jsonList.get(i));
                    }
                }
                responseList.add(new Response(response, contents, messageInfo));
            }
        });
    }

    public BrowserMobProxyServer getProxyServer(){return  server;}
    public List<Request> getRequestList() {
        return requestList;
    }
    public List<Response> getResponseList() {
        return responseList;
    }
}
