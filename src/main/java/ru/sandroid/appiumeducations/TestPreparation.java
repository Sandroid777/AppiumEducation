package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.net.InetAddress.getLocalHost;


public class TestPreparation {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private AndroidDriver driver;
    private AppiumDriverSteps steps;
    private BrowserMobProxyServer server;
    private LinkedList<Request> requestList;
    private ExecutorService executorService;

    public void createDriverAndSteps() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "aPhone");
        capabilities.setCapability("appPackage",  "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserActivity");
        capabilities.setCapability("appWaitActivity", ".firstscreen.FirstScreenActivity");

        driver = new AndroidDriver(new URL(TESTOBJECT), capabilities);
        steps = new AppiumDriverSteps(driver);
    }

    public  void addProxyServer(int port) throws UnknownHostException {

        server = new BrowserMobProxyServer();

        requestList = new LinkedList<Request>();
        executorService = Executors.newFixedThreadPool(1);

        server.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(final HttpRequest request, final HttpMessageContents contents, final HttpMessageInfo messageInfo) {

                executorService.submit(new Runnable() {
                    public void run() {
                        requestList.addLast(new Request(request, contents, messageInfo));
                    }
                });
                return null;
            }
        });

        server.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
            }
        });

        server.start(port, getLocalHost());
    }


    public AppiumDriver getDriver(){
        return driver;
    }
    public AppiumDriverSteps getSteps(){
        return  steps;
    }
    public BrowserMobProxyServer getProxyServer(){return  server;}
    public LinkedList<Request> getRequestList() {
        return requestList;
    }
}
