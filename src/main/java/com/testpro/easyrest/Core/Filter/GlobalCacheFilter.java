package com.testpro.easyrest.Core.Filter;

import com.testpro.easyrest.Core.Cache.RequestCache;
import com.testpro.easyrest.Core.Cache.ResponseCache;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.filter.log.UrlDecoder;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
public class GlobalCacheFilter implements OrderedFilter {
  @Override
  public int getOrder() {
    return 3;
  }

  @Override
  public Response filter(
      FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec,
      FilterContext ctx) {
    Response response = ctx.next(requestSpec, responseSpec);
    setCacheWithRequest(requestSpec);
    setCacheWithResponse(response);
    return response;
  }

  /**
   * 获取请求参数 设置缓存 便于接口依赖参数注入
   *
   * @param requestSpec FilterableRequestSpecification 对象
   */
  private void setCacheWithRequest(FilterableRequestSpecification requestSpec) {

    String method = requestSpec.getMethod();
    RequestCache.METHOD.put(RequestCache.method, method);

    Headers headers = requestSpec.getHeaders();
    RequestCache.HEADERS.put(RequestCache.Headers, headers);
    Cookies cookies = requestSpec.getCookies();
    RequestCache.COOKIES.put(RequestCache.Cookies, cookies);

    String uri = requestSpec.getURI();
    String url =
        UrlDecoder.urlDecode(
            uri,
            Charset.forName(
                requestSpec.getConfig().getEncoderConfig().defaultQueryParameterCharset()),
            true);

    RequestCache.URI.put(RequestCache.url, url);

    Map<String, String> requestParams = requestSpec.getRequestParams();
    RequestCache.REQUEST_PARAMS.put(RequestCache.RequestParams, requestParams);

    Map<String, String> formParams = requestSpec.getFormParams();
    RequestCache.FORM_PARAMS.put(RequestCache.FormParams, formParams);

    Map<String, String> pathParams = requestSpec.getPathParams();
    RequestCache.PATH_PARAMS.put(RequestCache.PathParams, pathParams);

    Map<String, String> queryParams = requestSpec.getQueryParams();
    RequestCache.QUERY_PARAMS.put(RequestCache.QueryParams, pathParams);

    if (requestSpec.getBody() != null) {
      RequestCache.BODY.put(RequestCache.Body, requestSpec.getBody());
    }
  }

  /**
   * 获取返回值 设置缓存 便于接口依赖参数注入
   *
   * @param response 返回值
   */
  private void setCacheWithResponse(Response response) {

    String contentType = response.getContentType();
    ResponseCache.Content_Type.put(ResponseCache.contentType, contentType);

    Headers headers = response.getHeaders();
    ResponseCache.HEADERS.put(ResponseCache.Headers, headers);

    int statusCode = response.getStatusCode();
    ResponseCache.STATUS.put(ResponseCache.status, statusCode);

    Cookies detailedCookies = response.getDetailedCookies();
    ResponseCache.COOKIES.put(ResponseCache.Cookies, detailedCookies);

    ResponseBody body = response.getBody();
    ResponseCache.BODY.put(ResponseCache.Body, body);

    long time = response.getTime();
    ResponseCache.TIME.put(ResponseCache.Time, time);
  }
}
