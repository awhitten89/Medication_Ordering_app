package com.example.alan.medicationorderingapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Alan on 15/09/2015.
 */
public class requestPackage {

    private String uri;
    private String method = "POST";
    private Map<String, Object> params = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setParam(String key, Object value) {
        params.put(key,value);
    }
    
    public String getEncodedParams() {
        
        StringBuilder sb = new StringBuilder();

        for (String key: params.keySet()) {
            Object value = null;
            try {
                value = URLEncoder.encode(String.valueOf(params.get(key)), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(sb.length() > 0) {
                sb.append("&");
            }

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }
}
