package com.testpro.easyrest.Core.Cache;

import com.testpro.easyrest.Enum.RequestCacheEnum;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 全局缓存 用于用例依赖情况的取值 */
public class RequestCache {
  /*
     拒绝实例化
  */
  private RequestCache() {}

  public static final String method = RequestCacheEnum.method.name();
  public static final ConcurrentHashMap<String, String> METHOD = new ConcurrentHashMap<>();

  public static final String url = RequestCacheEnum.url.name();
  public static final ConcurrentHashMap<String, String> URI = new ConcurrentHashMap<>();

  public static final String RequestParams = RequestCacheEnum.params.name();
  public static final ConcurrentHashMap<String, Map<String, String>> REQUEST_PARAMS =
      new ConcurrentHashMap<>();
  public static final String QueryParams = RequestCacheEnum.params.name();
  public static final ConcurrentHashMap<String, Map<String, String>> QUERY_PARAMS =
      new ConcurrentHashMap<>();
  public static final String FormParams = RequestCacheEnum.params.name();
  public static final ConcurrentHashMap<String, Map<String, String>> FORM_PARAMS =
      new ConcurrentHashMap<>();
  public static final String PathParams = RequestCacheEnum.params.name();
  public static final ConcurrentHashMap<String, Map<String, String>> PATH_PARAMS =
      new ConcurrentHashMap<>();

  public static final String Headers = RequestCacheEnum.headers.name();
  public static final ConcurrentHashMap<String, Headers> HEADERS = new ConcurrentHashMap<>();

  public static final String Cookies = RequestCacheEnum.cookies.name();
  public static final ConcurrentHashMap<String, Cookies> COOKIES = new ConcurrentHashMap<>();

  public static final String Body = RequestCacheEnum.body.name();
  public static final ConcurrentHashMap<String, String> BODY = new ConcurrentHashMap<>();

  /** @return 返回缓存的方法名称 */
  public static String RequestCacheMethod() {
    return RequestCache.METHOD.get(RequestCache.method);
  }

  /** @return 返回requestCacheUrl 数据 */
  public static String requestCacheUrl() {
    return URI.get(url);
  }

  /**
   * @param targetKey 目标Key
   * @return 返回requestCacheparams 数据
   */
  public static String requestCacheParams(String targetKey) {
    Map<String, String> REQUEST_PARAMS_map = REQUEST_PARAMS.get(RequestParams);
    Map<String, String> QUERY_PARAMS_Map = QUERY_PARAMS.get(QueryParams);
    Map<String, String> FORM_PARAMS_Map = FORM_PARAMS.get(FormParams);
    Map<String, String> PATH_PARAMS_Map = PATH_PARAMS.get(PathParams);
    if (REQUEST_PARAMS_map.get(targetKey) != null) {
      return REQUEST_PARAMS_map.get(targetKey);
    } else if (QUERY_PARAMS_Map.get(targetKey) != null) {
      return QUERY_PARAMS_Map.get(targetKey);
    } else if (FORM_PARAMS_Map.get(targetKey) != null) {
      return FORM_PARAMS_Map.get(targetKey);
    } else if (PATH_PARAMS_Map.get(targetKey) != null) {
      return PATH_PARAMS_Map.get(targetKey);
    } else {
      return null;
    }
  }

  /**
   * @param targetKey 目标Key
   * @return 返回requestCacheHeaders 数据
   */
  public static String requestCacheHeaders(String targetKey) {
    Headers headers = HEADERS.get(Headers);
    return headers != null ? getHeadersString(targetKey, headers) : null;
  }

  /**
   * @param targetKey 目标Key
   * @return 返回requestCacheCookies 数据
   */
  public static String requestCacheCookies(String targetKey) {
    Cookies cookies = COOKIES.get(Cookies);
    return cookies != null ? getCookiesString(targetKey, cookies) : null;
  }

  /**
   * 暂时没想好怎么做
   *
   * @param targetKey 目标Key
   * @return 返回requestCacheCookies 数据
   */
  public static String requestCacheBody(String targetKey) {
    String method = RequestCacheMethod();

    return null;
  }

  public static String getHeadersString(String targetKey, Headers headers) {
    if (headers.exist()) {
      for (Header next : headers) {
        if (next.getName().equals(targetKey)) {
          return next.getValue();
        }
      }
    }
    return null;
  }

  public static String getCookiesString(String targetKey, Cookies cookies) {
    if (cookies.exist()) {
      for (Cookie next : cookies) {
        if (next.getName().equals(targetKey)) {
          return next.getValue();
        }
      }
    }
    return null;
  }
}
