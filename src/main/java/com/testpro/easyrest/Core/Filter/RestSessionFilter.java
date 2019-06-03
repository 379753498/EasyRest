package com.testpro.easyrest.Core.Filter;

import com.testpro.easyrest.Config.EasyRestConfig;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 解决场景问题 * A 用户登录 ： 增加操作 * B 用户登录 ： 增加操作 session 中记录的信息还是A 用户的session信息 * 解决后： 根据提供的信息是否需要清除session
 * 如果为true 那么将进行一次 清除session 的操作 在请求之前 * 保证在B 登录后 延续B 的session信息
 */
@Slf4j
public class RestSessionFilter implements OrderedFilter {
  private AtomicReference<String> sessionId = new AtomicReference<>();

  public Response filter(
      FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec,
      FilterContext ctx) {
    if (System.getProperty(EasyRestConfig.GLOBAL_SYSTEM_SESSION_SETTING) != null
        && System.getProperty(EasyRestConfig.GLOBAL_SYSTEM_SESSION_SETTING)
            .equals(EasyRestConfig.GLOBAL_SYSTEM_CLEAN_SETTING)) {
      sessionId = new AtomicReference<>();
      System.setProperty(
          EasyRestConfig.GLOBAL_SYSTEM_SESSION_SETTING, EasyRestConfig.GLOBAL_SYSTEM_CACHE_SETTING);
    }

    if (hasStoredSessionId() && !requestHasSessionIdDefined(requestSpec)) {
      requestSpec.sessionId(sessionId.get());
    }

    final Response response = ctx.next(requestSpec, responseSpec);
    final String sessionIdInResponse = response.sessionId();
    if (isNotBlank(sessionIdInResponse)) {
      log.info("sessionID值是{}", sessionIdInResponse);
      sessionId.set(sessionIdInResponse);
    }
    return response;
  }

  private boolean hasStoredSessionId() {
    return sessionId.get() != null;
  }

  private boolean requestHasSessionIdDefined(FilterableRequestSpecification requestSpec) {
    return requestSpec
        .getCookies()
        .hasCookieWithName(requestSpec.getConfig().getSessionConfig().sessionIdName());
  }

  @Override
  public int getOrder() {
    return 2;
  }
}
