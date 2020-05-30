/**
 * This file created at 2017年2月28日.
 *
 * Copyright (c) 2002-2017 Bingosoft, Inc. All rights reserved.
 */
package com.tianma315.core.utils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * <code>{@link HttpJsonUtils}</code>
 *
 *
 * @author Administrator
 */
@Service
public class HttpJsonUtils {
	
	/**
	 * http get请求
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String get(String url) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		
		//设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//		httpClient4.getParams().setCookiePolicy(
//				CookiePolicy.BROWSER_COMPATIBILITY);
		//get请求
		GetMethod getMethod = new GetMethod(url);
		//设置Referer
//		getMethod4.setRequestHeader("Referer", "");
		//浏览器userAgent
//		String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
//		getMethod4.setRequestHeader("User-Agent", userAgent);
//		getMethod4.setRequestHeader("If-Modified-Since","0");
		
		//执行请求
		httpClient.executeMethod(getMethod);
		//获取响应数据 方式一
//		String responseBodyString = getMethod4.getResponseBodyAsString();
		//获取响应数据 方式二 推荐
		InputStream in=getMethod.getResponseBodyAsStream();
		InputStreamReader ii=new InputStreamReader(in,"utf-8");
		BufferedReader read=new BufferedReader(ii);
		StringBuffer responseBodyString=new StringBuffer();
		String s="";
		while((s=read.readLine())!=null)
			responseBodyString.append(s);
		
//		JSONObject jSONObject=new JSONObject(responseBodyString.toString());
//		jSONObject=jSONObject.getJSONObject("data");
//		jSONObject=jSONObject.getJSONObject("info");
//		
//		String score=jSONObject.get("score").toString();
//		String votes=jSONObject.get("votes").toString();
//		String num=jSONObject.get("num").toString();
//		String disparity=jSONObject.get("disparity").toString();
		/*String hourBegin=jSONObject.get("hourBegin").toString();
		String hourEnd=jSONObject.get("hourEnd").toString();*/
		
		getMethod.releaseConnection();
		httpClient.getHttpConnectionManager().closeIdleConnections(0);
		
		return responseBodyString.toString();
	}
	
	/**
	 * 
	 * http post请求
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String post(String url,Map<String,String> params)
			throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(url);

		// 设置请求参数
		NameValuePair[] data = {};
		Set<String> keys=params.keySet();
		for(String key:keys){
			data=Arrays.copyOf(data, data.length+1);
			data[data.length-1]=new NameValuePair(key, params.get(key));
		}
		
		postMethod.setRequestBody(data);
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		
		//执行请求
		httpClient.executeMethod(postMethod);

		InputStream in=postMethod.getResponseBodyAsStream();
		InputStreamReader ii=new InputStreamReader(in,"utf-8");
		BufferedReader read=new BufferedReader(ii);
		StringBuffer responseBodyString=new StringBuffer();
		String s="";
		while((s=read.readLine())!=null)
			responseBodyString.append(s);

		// System.out.println("responseBodyString:"+responseBodyString);

		// 获得 Cookie
//		Cookie[] cookies = httpClient.getState().getCookies();

		postMethod.releaseConnection();
		httpClient.getHttpConnectionManager().closeIdleConnections(0);
		
		return responseBodyString.toString();
	}
	
		
	
		
		
		
	
}
