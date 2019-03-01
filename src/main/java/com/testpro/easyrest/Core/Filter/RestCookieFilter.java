package com.testpro.easyrest.Core.Filter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicHeader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *  解决场景问题
 *    A 用户登录 ： 增加操作
 *    B 用户登录 ： 增加操作  此时Cookie 中记录的信息还是A 用户的Cookie信息
 *    解决后： 根据提供的信息是否需要清除Cookie  如果为true 那么将进行一次 清除Cookie 的操作 在请求之前
 *   保证在B 登录后 延续B 的Cookie信息
 *
 */
public class RestCookieFilter extends CookieFilter {

    private CookieSpec cookieSpec = new DefaultCookieSpec();
    private BasicCookieStore cookieStore = new BasicCookieStore();
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {


        final CookieOrigin cookieOrigin = this.cookieOriginFromUri(requestSpec.getURI());
        if (System.getProperty("easyrest.restassured.cookies")!=null&&System.getProperty("easyrest.restassured.cookies").equals("clean"))
        {
            cookieStore.clear();
            System.setProperty("easyrest.restassured.cookies","cache");
        }
        for (Cookie cookie : cookieStore.getCookies()) {
            if (cookieSpec.match(cookie, cookieOrigin) && !requestSpec.getCookies().hasCookieWithName(cookie.getName())) {
                requestSpec.cookie(cookie.getName(), cookie.getValue());
            }
        }

        final Response response = ctx.next(requestSpec, responseSpec);

        List<Cookie> responseCookies = extractResponseCookies(response, cookieOrigin);
        cookieStore.addCookies(responseCookies.toArray(new Cookie[0]));
        return response;
    }
    private CookieOrigin cookieOriginFromUri(String uri) {

        try {
            URL parsedUrl = new URL(uri);
            int port = parsedUrl.getPort() != -1 ? parsedUrl.getPort() : 80;
            return new CookieOrigin(
                    parsedUrl.getHost(), port, parsedUrl.getPath(), "https".equals(parsedUrl.getProtocol()));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private List<Cookie> extractResponseCookies(Response response, CookieOrigin cookieOrigin) {

        List<Cookie> cookies = new ArrayList<Cookie>();
        for (String cookieValue : response.getHeaders().getValues("Set-Cookie")) {
            Header setCookieHeader = new BasicHeader("Set-Cookie", cookieValue);
            try {
                cookies.addAll(cookieSpec.parse(setCookieHeader, cookieOrigin));
            } catch (MalformedCookieException ignored) {
            }
        }
        return cookies;
    }

}
