package com.testpro.easyrest.Core.Filter;
import com.testpro.easyrest.Core.Cache.RequestCache;
import com.testpro.easyrest.Core.Cache.ResponseCache;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ProxySpecification;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
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
        ResponseCache.CONTENT_TYPE.put(ResponseCache.ContentType,contentType);

        ResponseBody body = response.getBody();
        ResponseCache.BODY.put(ResponseCache.Body, body);

        Headers headers = response.getHeaders();
        ResponseCache.HEADERS.put(ResponseCache.Headers,headers);

//        String sessionId = response.getSessionId();
//        ResponseCache.SESSION_ID.put(ResponseCache.SessionId,sessionId);

        Cookies detailedCookies = response.getDetailedCookies();
        ResponseCache.COOKIES.put(ResponseCache.Cookies,detailedCookies);

        long time = response.getTime();
        ResponseCache.TIME.put(ResponseCache.Time,time);
    }

    /**
     *  获取请求参数 设置缓存 便于接口依赖参数注入
     * @param requestSpec
     */
    private void setCacheWithRequest(FilterableRequestSpecification requestSpec) {

        String method = requestSpec.getMethod();
        RequestCache.METHOD.put(RequestCache.method,method);

        Headers headers = requestSpec.getHeaders();
        RequestCache.HEADERS.put(RequestCache.Headers,headers);
        Cookies cookies = requestSpec.getCookies();
        RequestCache.COOKIES.put(RequestCache.Cookies,cookies);

        String uri = requestSpec.getBaseUri();
        RequestCache.URI.put(RequestCache.uri,uri);

        ProxySpecification proxySpecification = requestSpec.getProxySpecification();
        if(proxySpecification==null)
        {

        }else{
            RequestCache.PROXY.put(RequestCache.Proxy,proxySpecification);
        }


        Map<String, String> requestParams = requestSpec.getRequestParams();
        RequestCache.REQUEST_PARAMS.put(RequestCache.Requestparams,requestParams);

        Map<String, String> formParams = requestSpec.getFormParams();
        RequestCache.FORM_PARAMS.put(RequestCache.FormParams,formParams);

        Map<String, String> pathParams = requestSpec.getPathParams();
        RequestCache.PATH_PARAMS.put(RequestCache.PathParams,pathParams);

        Map<String, String> queryParams = requestSpec.getQueryParams();
        RequestCache.QUERY_PARAMS.put(RequestCache.QueryParams,pathParams);

        List<MultiPartSpecification> multiPartParams = requestSpec.getMultiPartParams();
        RequestCache.MULTIPARTS.put(RequestCache.Multiparts,multiPartParams);

//        Object body = requestSpec.getBody();
//        RequestCache.BODY.put(RequestCache.Body,body);
    }
}
