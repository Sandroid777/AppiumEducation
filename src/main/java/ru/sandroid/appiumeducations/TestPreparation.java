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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import static java.net.InetAddress.getLocalHost;

public class TestPreparation {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private AppiumDriver driver;
    private AppiumDriverSteps steps;
    private BrowserMobProxyServer server;
    private List<Request> requestList;



    public void createDriverAndSteps() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "aPhone");
        capabilities.setCapability("appPackage",  "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserActivity");
        //capabilities.setCapability("unicodeKeyboard", "true");

        driver = new AndroidDriver(new URL(TESTOBJECT), capabilities);
        steps = new AppiumDriverSteps(driver);
    }

    public  void addProxyServer(int port) throws UnknownHostException {

        server = new BrowserMobProxyServer();
        //паораметры для перехвата
        //server.setHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        requestList = new LinkedList<>();

        server.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                synchronized (requestList) {
                    requestList.add(new Request(request, contents, messageInfo));
                }

                return null;
            }
        });

        server.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                if (messageInfo.getUrl().contains("browser.mobile.yandex.net/locate")) {
                    response.headers().remove("X-YaMisc");
                    response.headers().add("X-YaMisc", "region_id=2; client_country=RU; country=ru;");
                }
            }
        });

        server.start(port, getLocalHost());
        server.newHar("Har_01");
    }

    public void executeCommandLine(String string) throws IOException {
        Process p = Runtime.getRuntime().exec(string);
    }

    public AppiumDriver getDriver(){
        return driver;
    }
    public AppiumDriverSteps getSteps(){
        return  steps;
    }
    public BrowserMobProxyServer getProxyServer(){return  server;}
    public List<Request> getRequestList() {
        return requestList;
    }
}
