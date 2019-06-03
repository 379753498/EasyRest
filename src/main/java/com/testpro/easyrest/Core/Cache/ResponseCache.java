package com.testpro.easyrest.Core.Cache;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testpro.easyrest.Enum.ContentType;
import com.testpro.easyrest.Enum.ResponseCacheEnum;
import com.testpro.easyrest.Util.JsonUtil;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseCache {

  private ResponseCache() {}

  public static final String contentType = ResponseCacheEnum.contentype.name();
  public static final ConcurrentHashMap<String, String> Content_Type = new ConcurrentHashMap<>();

  public static final String Headers = ResponseCacheEnum.headers.name();
  public static final ConcurrentHashMap<String, Headers> HEADERS = new ConcurrentHashMap<>();

  public static final String status = ResponseCacheEnum.status.name();
  public static final ConcurrentHashMap<String, Integer> STATUS = new ConcurrentHashMap<>();

  public static final String Body = ResponseCacheEnum.body.name();
  public static final ConcurrentHashMap<String, ResponseBody> BODY = new ConcurrentHashMap<>();

  public static final String Cookies = ResponseCacheEnum.cookies.name();
  public static final ConcurrentHashMap<String, Cookies> COOKIES = new ConcurrentHashMap<>();

  public static final String Time = ResponseCacheEnum.time.name();
  public static final ConcurrentHashMap<String, Long> TIME = new ConcurrentHashMap<>();

  /** @return 返回ResponseCacheContentType 数据 */
  public static String ResponseCacheContentType() {
    return Content_Type.get(contentType);
  }
  /**
   * @param targetKey 目标Key
   * @return 返回requestCacheHeaders 数据
   */
  public static String responseCacheHeaders(String targetKey) {
    Headers headers = ResponseCache.HEADERS.get(targetKey);

    return headers != null ? RequestCache.getHeadersString(targetKey, headers) : null;
  }

  /** @return 返回responseCacheStatus 数据 */
  public static String responseCacheStatus() {
    return STATUS.get(status).toString();
  }
  /**
   * @param targetKey 目标Key
   * @return 返回requestCacheHeaders 数据
   */
  public static String responseCacheCookies(String targetKey) {
    Cookies cookies = ResponseCache.COOKIES.get(Cookies);
    return cookies != null ? RequestCache.getCookiesString(targetKey, cookies) : null;
  }

  /**
   * @param targetKey 目标Key
   * @return 返回responseCacheBody 数据
   */
  public static String responseCacheBody(String targetKey) {
    if (ResponseCacheContentType().contains(ContentType.JSON.getValue())) {
      ResponseBody responseBody = BODY.get(Body);
      if (responseBody != null) {
        DocumentContext documentContext = JsonPath.parse(responseBody.asString());
        List<Map<String, Object>> selectResult = documentContext.read("$.." + targetKey);
        if (selectResult.size() == 0) {
          return null;
        }
        String FirstResult = JsonUtil.GsonBeanToString(selectResult.get(0));
        if (FirstResult.contains("{") && FirstResult.contains("}")) {

          return FirstResult;
        } else {
          FirstResult = FirstResult.substring(1, FirstResult.length() - 1);
          return FirstResult;
        }
      }
    }

    return null;
  }

  /** @return 返回responseCacheTime 数据 */
  public static String responseCacheTime() {
    return TIME.get(Time).toString();
  }
}
