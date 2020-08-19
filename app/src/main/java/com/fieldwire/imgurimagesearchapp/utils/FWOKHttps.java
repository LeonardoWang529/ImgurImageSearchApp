package com.fieldwire.imgurimagesearchapp.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FWOKHttps {
    public static final String TAG = "FWOKHTTPS";
    static FWOKHttps fwOkHttps;
    public OkHttpClient okHttpClient;
    static final String BASEURL = FWConstants.IMGURBASEURL;

    private FWOKHttps(){
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    synchronized public static FWOKHttps getInstance() {
        if(fwOkHttps == null){
            synchronized (FWOKHttps.class) {
                if(fwOkHttps == null) {
                    fwOkHttps = new FWOKHttps();
                }
            }
        }
        return fwOkHttps;
    }

    public Response doGetRequest(String api) throws IOException {
        Request request = new Request.Builder()
                .url(BASEURL+api)
                .method("GET", null)
                .addHeader("Authorization", FWConstants.CLIENTID)
                .build();

        return httpCall(request);
    }

    public Response doPostRequest(String api, RequestBody requestBody) throws IOException {
        //RequestBody requestBody = RequestBody.create(json,BJJSON);
        Request request = new Request.Builder()
                .url(BASEURL+api)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();

        return httpCall(request);
    }

    public Response httpCall(Request request) throws IOException {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            //FWLogs.e(TAG, "HTTP call did not give response");
            e.printStackTrace();
            //throw new IOException("Http call not give response:" + response);
            return response;
        }

        if (!response.isSuccessful()) {
            //FWLogs.e(TAG, "Http call not give successful response");
            //throw new IOException("Http call not give successful response:" + response);
        }
        return response;
    }




}
