package cn.com.yisi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

public class WebHelper {
	private int defaultTime = 30 * 1000;//��ʱʱ��
	private String defaultCode = "utf-8";
	private Map<String, String> result=new HashMap<String, String>();

	private WebHelper(){
		
	}
	public static WebHelper getInstance(){
		return new WebHelper();
	}
	/**
	 * 
	 * setDoOutput(Boolean)   �����Ƿ����д������

		setRequestMethod(String)  ��������ķ�ʽ��"GET"��"POST"��

		OutputStream getOutputStream()  ��������������ʵͨ������Ϳ����������������д���ݣ�������վ�ǾͿ��Ի�������ˣ�

		InputStream getInputStream()    ���������������ʵ���������վ���ع��������ݣ�
	 */
	public Map<String, String> getResult(String urlStr,List<NameValuePair> params) {
		
		StringBuilder paramsStr=new StringBuilder();
		for (NameValuePair nameValuePair : params) {
			paramsStr.append(nameValuePair.getName()+"="+nameValuePair.getValue()+"&");
		}
		if (paramsStr.toString().contains("&")) {
			paramsStr.toString().replace(paramsStr.toString().charAt(paramsStr.toString().length()), ' ');
		}
		result.clear();
		String statue="";
		String resultStr=null;
		HttpURLConnection con =null;
		try {
			URL url = new URL(urlStr);
			String host = url.getHost();
			con=(HttpURLConnection) url.openConnection();
			con.setConnectTimeout(defaultTime);
			PrintWriter pw=new PrintWriter(con.getOutputStream());
			pw.write(paramsStr.toString().trim());
			con.connect();
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				statue="ok";
				resultStr=con.getResponseMessage();
			} else {
				statue="false";
			}
			con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			statue = "error";
		}finally{
			if (con!=null) {
				con.disconnect();
			}
		}

		result.put("statue", statue);
		if (resultStr!=null) {
			result.put("resultStr", resultStr);
		}
		return result;
	}
	public Map<String, String> resultUrlGet(String urlStr,List<NameValuePair> params){
		result.clear();
		String statue="";
		StringBuilder resultStr=new StringBuilder();
		HttpClient client=new DefaultHttpClient();
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, defaultTime);
		urlStr+="?"+URLEncodedUtils.format(params, "utf-8");
		HttpGet get=new HttpGet(urlStr);
		try {
			HttpResponse resp=client.execute(get);
			if(resp.getStatusLine().getStatusCode()==200){
				statue="ok";
				InputStream is=resp.getEntity().getContent();
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				String line="";
				while ((line=br.readLine())!=null) {
					resultStr.append(line);
				}
			}else{
				statue="false";
			}
		} catch (ClientProtocolException e) {
			statue="error";
			e.printStackTrace();
		} catch (IOException e) {
			statue="error";
			e.printStackTrace();
		}
		result.put("statue", statue);
		result.put("resultStr", resultStr.toString());
		return result;
	}
	public Map<String, String> resultUrlPost(String urlStr,List<NameValuePair> params){
		result.clear();
		String statue="";
		StringBuilder resultStr=new StringBuilder();
		HttpClient client=new DefaultHttpClient();
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, defaultTime);
		HttpPost post=new HttpPost(urlStr);
//		for (NameValuePair nameValuePair : params) {
//			post.setParams(post.getParams().setParameter(nameValuePair.getName(), nameValuePair.getValue()));
//		}
		try {
			post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			HttpResponse resp=client.execute(post);
			if(resp.getStatusLine().getStatusCode()==200){
				InputStream is=resp.getEntity().getContent();
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				String line="";
				while ((line=br.readLine())!=null) {
					resultStr.append(line);
				}
				statue="ok";
			}else{
				statue="false";
			}
		} catch (ClientProtocolException e) {
			statue="error";
			e.printStackTrace();
		} catch (IOException e) {
			statue="error";
			e.printStackTrace();
		}
		result.put("statue", statue);
		result.put("resultStr", resultStr.toString());
		return result;
	}
	/**���ó�ʱʱ��,Ĭ��30s*/
	public void setDefaultTime(int timeOut){
		this.defaultTime=timeOut;
	}
	/**���ñ��뷽ʽ,Ĭ��utf-8*/
	public void setCode(String code){
		this.defaultCode=code;
	}
}
