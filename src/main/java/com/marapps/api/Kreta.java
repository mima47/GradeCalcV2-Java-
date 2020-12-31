package com.marapps.api;

import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.IOException;

public class Kreta {

    final String SCHOOL_LIST_URL = "https://kretaglobalmobileapi2.ekreta.hu:443/api/v2/Institute";
    final String TOKEN_URL = "https://idp.e-kreta.hu/connect/token";

    OkHttpClient client = new OkHttpClient();
    Endpoints endpoints = new Endpoints();
//  This is why the content-type header is not needed in get_tokens.
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public String get_school_list(){
        Request request = new Request.Builder()
                .url(SCHOOL_LIST_URL)
                .addHeader("apiKey", "7856d350-1fda-45f5-822d-e1a2f3f1acf0")
                .build();

        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String get_tokens(String username, String password, String instituteCode){
        String postBody = "userName="+ username +"&password="+ password +"&institute_code="+ instituteCode +"&grant_type=password&client_id=kreta-ellenorzo-mobile";

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "hu.ekreta.student/1.0.5/Android/0/0")
                .build();

        try (Response response = client.newCall(request).execute()){
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String get_student_info(String token, String url){
        Request request = new Builder()
                .url(url + endpoints.student)
                .build();

        try (Response response = client.newCall(request).execute()){
         return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String get_student_evaluations(String token, String url){
        Request request = new Builder()
                .url(url + endpoints.evaluations)
                .addHeader("User-Agent", "hu.ekreta.student/1.0.5/Android/0/0")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
