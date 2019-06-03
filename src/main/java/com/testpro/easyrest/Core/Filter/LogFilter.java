package com.testpro.easyrest.Core.Filter;

import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.Util.RequestReport;
import com.testpro.easyrest.Util.ResponseReport;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.UrlDecoder;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class LogFilter implements OrderedFilter {
  @Override
  public int getOrder() {
    return 4;
  }

  @Override
  public Response filter(
      FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec,
      FilterContext ctx) {
    String uri = requestSpec.getURI();

    uri =
        UrlDecoder.urlDecode(
            uri,
            Charset.forName(
                requestSpec.getConfig().getEncoderConfig().defaultQueryParameterCharset()),
            true);

    String RequestDetail =
        RequestReport.print(requestSpec, requestSpec.getMethod(), uri, LogDetail.ALL, true);
    ReportDetil.RequestBodyReport(RequestDetail);
    Response response = ctx.next(requestSpec, responseSpec);
    String ResponseDetail = ResponseReport.print(response, response, LogDetail.ALL, true);
    ReportDetil.RespondBodyReport(ResponseDetail);
    return response;
  }
}
