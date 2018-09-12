package com.mydream.tulinrobot.method;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Package: com.mydream.tulinrobot.method
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/2015:25
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class Test1 {

    public void request(String url, String content) {


        String status = "";
        String response = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置请求属性
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setRequestProperty("x-adviewrtb-version", "2.1");
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpUrlConnection.getOutputStream());
            // 发送请求参数
            out.write(content);
            // flush输出流的缓冲
            out.flush();
            httpUrlConnection.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response += line;
            }
            status = new Integer(httpUrlConnection.getResponseCode()).toString();
            System.out.println("status=============="+status);
            System.out.println("response=============="+response);


            // 转换获取正确的返回内容
            JSONObject thesultStr = JSONObject.fromObject(response);
            System.out.println("thesultStr======"+thesultStr);

            List<Object> results = (List<Object>) thesultStr.get("results");
            System.out.println("results======"+results);

            JSONObject resultObj = JSONObject.fromObject(results.get(0));
            System.out.println("resultObj======"+resultObj);

            JSONObject values = JSONObject.fromObject(resultObj.get("values"));
            System.out.println("values======"+values);

            values.get("text");
            System.out.println(" text;======"+ values.get("text"));

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) { out.close();}
                if (in != null) {in.close();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        // 请求json，里面包含reqType，perception，userInfo
        JSONObject reqJson = new JSONObject();
        // 输入类型:0-文本(默认)、1-图片、2-音频
        int reqType = 0;
        reqJson.put("reqType",reqType);

        // 输入信息,里面包含inputText，inputImage，selfInfo
        JSONObject perception = new JSONObject();
        // 输入的文本信息
        JSONObject inputText = new JSONObject();
        inputText.put("text","你叫什么");
        perception.put("inputText",inputText);
//        // 输入的图片信息
//        JSONObject inputImage = new JSONObject();
//        inputImage.put("url","");
//        perception.put("inputImage",inputImage);
//        // 个人信息，里面包含location
//        JSONObject selfInfo = new JSONObject();
//        // 包含city，province，street
//        JSONObject location = new JSONObject();
//        location.put("city","");
//        location.put("province","");
//        location.put("street","");
//        selfInfo.put("location",location);
//        perception.put("selfInfo",selfInfo);
        // 用户信息
        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey","3660f1b9c5b148f588a2a31838db9bc2");
        userInfo.put("userId","311630");

        reqJson.put("perception",perception);
        reqJson.put("userInfo",userInfo);


        System.out.println("reqJson.toString()=======" + reqJson.toString());

        new Test1().request("http://openapi.tuling123.com/openapi/api/v2",reqJson.toString());
    }

}