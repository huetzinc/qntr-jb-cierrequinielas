package com.desmxsoft.qntrjbcierrequiniela;

import java.io.BufferedWriter;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

public class HelloHttpFunction implements HttpFunction {
  public void service(final HttpRequest request, final HttpResponse response) throws Exception {
    System.out.println("ejecutando function Hello");
    final BufferedWriter writer = response.getWriter();
    writer.write("Hello world!");
  }
}