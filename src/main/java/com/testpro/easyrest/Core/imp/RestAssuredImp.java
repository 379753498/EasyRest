package com.testpro.easyrest.Core.imp;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Abstract.AbstractRestAssuredExecute;
import com.testpro.easyrest.Core.Filter.CacheOrAutoFilter;
import com.testpro.easyrest.Core.Interface.CacheDataSource;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Core.Interface.ParameterFilter;
import com.testpro.easyrest.Enum.ContentType;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class RestAssuredImp extends AbstractRestAssuredExecute {

  public RestAssuredImp() {
    parameterFilters.add(new CacheOrAutoFilter());
  }

  private CacheDataSource<String> CacheDataSource = new DataCacheOrAuto();

  private List<ParameterFilter<ExecutionData>> parameterFilters = new LinkedList<>();
  /**
   * 尝试检查数据对象中的参数化情况尝试从缓存中读取
   *
   * @param executionData 数据对象
   * @return 数据驱动对象
   */
  @Override
  protected ExecutionData tryGetCacheOrAutoSetValue(ExecutionData executionData) {
    parameterFilters.sort(
        (o1, o2) -> {
          int order1 = o1.getOrder();
          int order2 = o2.getOrder();
          return Integer.compare(order1, order2);
        });
    for (ParameterFilter<ExecutionData> dynamicParameter : parameterFilters) {
      executionData = dynamicParameter.dynamicParameterListener(executionData);
    }
    return executionData;
  }

  /**
   * 初始化环境设置 根据数据驱动对象
   *
   * @param executionData 数据驱动对象
   */
  @Override
  protected void initEnvironment(ExecutionData executionData) {
    if (executionData.getCleanCookie() != null) {
      System.setProperty(
          EasyRestConfig.GLOBAL_SYSTEM_COOKIE_SETTING, executionData.getCleanCookie());
    }
    if (executionData.getCleanSession() != null) {
      System.setProperty(
          EasyRestConfig.GLOBAL_SYSTEM_SESSION_SETTING, executionData.getCleanCookie());
    }
  }

  /** 执行初始化配置操作 */
  @Override
  protected void InitConfiguration() {
    InitialConfiguration configuration = new InitialConfigurationImp();
    configuration.initConfiguration();
  }

  /**
   * 根据执行参数进行执行请求返回Response 对象
   *
   * @param executionData 数据驱动对象
   * @return 请求返回值对象
   */
  @Override
  protected Response execute(ExecutionData executionData) {
    RequestSpecification requestSpecification = getRequestSpecification(executionData);
    switch (executionData.getMethod()) {
      case "GET":
        return RestAssured.given(requestSpecification).get(executionData.getUrl());
      case "POST":
        return RestAssured.given(requestSpecification).post(executionData.getUrl());
      default:
        throw new RuntimeException("暂时不支持其他方式");
    }
  }

  /**
   * 执行验证工作
   *
   * @param response 返回值
   * @param executionData 数据驱动对象
   */
  @Override
  protected void RestAssuredExecuteVerification(Response response, ExecutionData executionData) {

    String contentType = response.getContentType();
    if (contentType.contains(ContentType.JSON.getValue())) {
      String resultAllStringCheck = executionData.getResultAllStringCheck();
      String resultJsonPathCheck = executionData.getResultJsonPathCheck();
      String resultContainsString = executionData.getResultContainsString();
      if (!StrUtil.isEmpty(resultAllStringCheck)
          || !StrUtil.isEmpty(resultJsonPathCheck)
          || !StrUtil.isEmpty(resultContainsString)) {
        // 执行验证
        ResponseSpecification specification = getResponseSpecification(executionData);
        response.then().assertThat().spec(specification);
        log.info("验证工作执行完成");
      }
    } else {
      log.warn("暂不支持返回值非{}的数据格式校验功能", ContentType.JSON.getValue());
    }
  }

  /**
   * 根据请求参数拼出请求参数信息
   *
   * @param data 数据驱动对象
   * @return 请求参数对象
   */
  @SuppressWarnings("unchecked")
  private RequestSpecification getRequestSpecification(ExecutionData data) {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    if (!StrUtil.isEmpty(data.getHeaders())) {
      Map<String, String> map =
          (Map<String, String>) JSON.parseObject(data.getHeaders(), Map.class);
      builder.addHeaders(map);
    }
    if (!StrUtil.isEmpty(data.getParameters())) {
      Map<String, String> map =
          (Map<String, String>) JSON.parseObject(data.getParameters(), Map.class);
      builder.addParams(map);
    }

    //        builder.addCookie("cook","hello cook");
    return builder.build();
  }

  /**
   * 根据返回值验证信息进行组装返回值验证信息对象
   *
   * @param executionData 数据驱动对象
   * @return 返回值验证信息对象
   */
  private ResponseSpecification getResponseSpecification(ExecutionData executionData) {
    ResponseSpecBuilder builder = new ResponseSpecBuilder();
    if (!StrUtil.isEmpty(executionData.getResultAllStringCheck())) {
      builder.expectBody(Matchers.equalTo(executionData.getResultAllStringCheck()));
    }
    if (!StrUtil.isEmpty(executionData.getResultJsonPathCheck())) {
      Map map = JSON.parseObject(executionData.getResultJsonPathCheck(), Map.class);
      for (Object o : map.keySet()) {
        String key = (String) o;
        String value = (String) map.get(key);
        try {
          // 识别是有小数点还是没有小数点的算法
          String[] split = StrUtil.split(value, ".");
          if (split.length > 1) {
            double val = Double.parseDouble(value);
            builder.expectBody(key, Matchers.is(val));
          } else {
            int val = Integer.parseInt(value);
            builder.expectBody(key, Matchers.is(val));
          }

        } catch (Exception e) {
          builder.expectBody(key, Matchers.equalTo(value));
        }
      }
    }
    if (!StrUtil.isEmpty(executionData.getResultContainsString())) {
      String returnCharacterString = executionData.getResultContainsString();
      String[] split = StrUtil.split(returnCharacterString, ",");
      for (String s : split) {
        builder.expectBody(Matchers.containsString(s));
      }
    }
    return builder.build();
  }

  /**
   * @param Filter 参数拦截器实现类
   * @return 实例本身
   */
  public RestAssuredImp addFilter(ParameterFilter<ExecutionData> Filter) {
    parameterFilters.add(Filter);
    return this;
  }

  /**
   * @param Filters 参数拦截器实现类集合
   * @return 实例本身
   */
  public RestAssuredImp addFilters(List<ParameterFilter<ExecutionData>> Filters) {
    parameterFilters.addAll(Filters);
    return this;
  }
}
