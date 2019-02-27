package com.testpro.easyrest.Core.Filter;
import com.testpro.easyrest.Core.Cache.GlobalCache;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ProxySpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class GlobalCacheFilter implements OrderedFilter {
    @Override
    public int getOrder() {
        return 1000;

    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
//
        System.out.println(new Date().getTime());
        System.out.println("ok");
        Response response = ctx.next(requestSpec, responseSpec);
        setCacheWithRequest(requestSpec);
        setCacheWithResponse(response);
        return response;
    }

    /**
     * 获取返回值 设置缓存 便于接口依赖参数注入
     * @param response
     */
    private void setCacheWithResponse(Response response) {

        String contentType = response.getContentType();
        GlobalCache.ResponseContentType.put("ResponseContentType",contentType);

        Map<String, String> cookies = response.getCookies();
        ResponseBody body = response.getBody();
        Headers headers = response.getHeaders();
        String sessionId = response.getSessionId();

        Cookies detailedCookies = response.getDetailedCookies();
    }

    /**
     *  获取请求参数 设置缓存 便于接口依赖参数注入
     * @param requestSpec
     */
    private void setCacheWithRequest(FilterableRequestSpecification requestSpec) {

        String method = requestSpec.getMethod();
        GlobalCache.method.put("method",method);

        Headers headers = requestSpec.getHeaders();
        List<Header> headers1 = headers.asList();
        Iterator<Header> iterator = headers1.iterator();
        while (iterator.hasNext())
        {
            Header next = iterator.next();
            GlobalCache.Headers.put(next.getName(),next.getValue());
        }
        Iterator<Cookie> iteratorCookie = requestSpec.getCookies().iterator();
        while (iteratorCookie.hasNext())
        {
            Cookie next = iteratorCookie.next();
            GlobalCache.Cookies.put(next.getName(),next.getValue());
        }
        String uri = requestSpec.getBaseUri();
        GlobalCache.URI.put("URI",uri);

        ProxySpecification proxySpecification = requestSpec.getProxySpecification();
        GlobalCache.Proxy.put("Proxy",proxySpecification);

        Map<String, String> requestParams = requestSpec.getRequestParams();
        GlobalCache.Requestparams.put("Requestparams",requestParams);

        Map<String, String> formParams = requestSpec.getFormParams();
        GlobalCache.FormParams.put("FormParams",formParams);

        Map<String, String> pathParams = requestSpec.getPathParams();
        GlobalCache.PathParams.put("PathParams",pathParams);

        Map<String, String> queryParams = requestSpec.getQueryParams();
        GlobalCache.QueryParams.put("queryParams",pathParams);

        List<MultiPartSpecification> multiPartParams = requestSpec.getMultiPartParams();
        GlobalCache.Multiparts.put("Multiparts",multiPartParams);
        
        Object body = requestSpec.getBody();
        GlobalCache.Body.put("Body",body);
    }
}
