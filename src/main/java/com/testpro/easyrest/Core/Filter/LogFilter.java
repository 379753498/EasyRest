package com.testpro.easyrest.Core.Filter;

import com.testpro.easyrest.Util.ReportDetil;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements OrderedFilter {
    @Override
    public int getOrder() {
        return 999;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        String responseBody = response.asString();
        log.info("请求执行完成返回值{}，总耗时{}", responseBody, response.getTime());
        ReportDetil.respondBody(responseBody);
        return response;
    }
}
