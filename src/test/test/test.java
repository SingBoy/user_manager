package advert_manager;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import cn.net.ibingo.common.utils.ConstantConfig;
import cn.net.ibingo.common.utils.HttpRequester;
import cn.net.ibingo.common.utils.HttpRespons;
import cn.net.ibingo.common.utils.HttpUtil;

import cn.net.ibingo.core.controller.VoluumNotifyController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.cache.jcache.interceptor.JCacheOperationSourcePointcut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class test {
	private static Logger log = Logger.getLogger(String.valueOf(test.class));
	private static String token = "";
	
	public static void main(String[] main){
		testVoluum();
	}
	public static void testVoluum() {
		Map<String,Object> params = new HashMap<>();
		params.
		HttpRespons sr = httpUtil.sendPost("http://localhost:9090/voluum/notify", params, null);
	}

	public static File downloadFile1(String p,String name) {
		String urlStr="http://yxj.ngrok.cc/user_manager/account/downloadImage";
		String path= p;
		String fileName=name;
		FileOutputStream output = null;
		InputStream input = null;
		HttpURLConnection conn = null;
		System.out.println("downloadFile = " + Thread.currentThread().getName());
		try {
			URL url=new URL(urlStr);
			conn=(HttpURLConnection)url.openConnection();
			// conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			StringBuffer param = new StringBuffer();
			param.append("&");
			param.append("userName").append("=").append(name);
			conn.getOutputStream().write(param.toString().getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			input = conn.getInputStream();
			String pathName= path+fileName;
			int res = conn.getResponseCode();

			File file=new File(pathName);
			file.delete();
			System.out.println("downloadFile1 = " + file.getAbsolutePath());
			if(file.exists()){
				System.out.println("exits");
				return file;
			}else{
				int len;
				File png = new File(path);
				png.mkdir();
				file.createNewFile();
				try {
					output=new FileOutputStream(file);
				} catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				byte[] bytes = new byte[1024];
				while((len = input.read(bytes))!=-1){
					output.write(bytes,0,len);
				}
				output.flush();
			}
			output.close();
			input.close();
			conn.disconnect();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(output != null) {
					output.close();
				}
				if(input != null) {
					output.close();
				}
			} catch (Exception e1) {

			}
		}
		return null;
	}







}
