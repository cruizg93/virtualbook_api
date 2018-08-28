package com.virtualbook.api.configurations;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor{
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		logRequest(request,body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		return response;
	}

	
	private void logRequest(HttpRequest request, byte[] body) throws IOException{
			StringBuilder sb = new StringBuilder();
			sb.append("=======================REQUEST BEGIN================================");
			sb.append("\n URI           :").append(request.getURI());
			sb.append("\n Method        :").append(request.getMethod());
			sb.append("\n Headers       :").append(request.getHeaders());
			sb.append("\n Request Body  :").append(new String(body, "UTF-8"));
			sb.append("=======================REQUEST END==================================");
			log.info(sb.toString());
	}
	
	private void logResponse(ClientHttpResponse response) throws IOException{
			StringBuilder sb = new StringBuilder();
			sb.append("=======================RESPONSE BEGIN================================");
			sb.append("\n Status Code   :").append(response.getStatusCode());
			sb.append("\n Status text   :").append(response.getStatusText());
			sb.append("\n Headers       :").append(response.getHeaders());
			sb.append("\n Response Body :").append(StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
			sb.append("=======================RESPONSE END==================================");
			log.info(sb.toString());
	}
}
