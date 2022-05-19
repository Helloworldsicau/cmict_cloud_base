package com.cmict.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: lichenxin
 * @Date: 2021/5/26
 */
@Slf4j
public class MasService {

    @Value("${mas.id}")
    private  String id;

    @Value("${mas.pwd}")
    private   String pwd;


    public  String send(String phone,String message){
        String reqXML = buildRequestXMLString(id, pwd,"",phone,message);

        return postXMLSendSMSRequest("http://218.204.110.232:8080/emc/HttpSendSMSService",reqXML);
    }


    private   String buildRequestXMLString(String id, String pwd, String serviceid, String phone, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<svc_init ver=\"2.0.0\">")
                .append("<sms ver=\"2.0.0\">")
                .append("<client>")
                .append("<id>").append(id).append("</id>")
                .append("<pwd>").append(pwd).append("</pwd>")
                .append("<serviceid>").append(serviceid).append("</serviceid>")
                .append("</client>")
                .append("<sms_info>")
                .append("<phone>").append(phone).append("</phone>")
                .append("<content>").append(content).append("</content>")
                .append("</sms_info>")
                .append("</sms>")
                .append(" </svc_init>");

        System.out.println(sb.toString());
        return sb.toString();
    }

    private static  String postXMLSendSMSRequest(String servletUrl, String content) {
        String result = null;

        BufferedReader br = null;
        OutputStreamWriter out = null;
        HttpURLConnection con = null;

        try {
            URL url = new URL(servletUrl);

            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

            out.write(content);

            out.flush();

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            String line = null;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
           log.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }

            if (con != null) {
                con.disconnect();
                con = null;
            }
        }

        return result;
    }
}
