package com.fieldwire.imgurimagesearchapp.data.imgurImageModule;

import com.fieldwire.imgurimagesearchapp.data.Result;
import com.fieldwire.imgurimagesearchapp.data.model.FWGalleryItem;
import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;
import com.fieldwire.imgurimagesearchapp.utils.FWOKHttps;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.List;

import okhttp3.Response;

public class FWImgurImageDataSource {
    public Result getSearchImages(String sort, String window, String page, String keyWord,List<FWImageItem> fwImageItemList) throws IOException {
        String api = "";
        if(!sort.equals("")) api += "/" + sort;
        if(!window.equals("")) api += "/" + window;
        if(!page.equals("")) api += "/" + page;
        api += "/?q=" + keyWord;

        Response response =  FWOKHttps.getInstance().doGetRequest(api);

        if(response== null || response.code() != 200) return null;

        if(!response.isSuccessful()){
            return new Result.Error(new Exception("Internet response Error"));
        }

        try {
            JSONObject data = new JSONObject(response.body().string());
            JSONArray items = data.getJSONArray("data");

            for(int i=0; i<items.length();i++) {
                JSONObject item = items.getJSONObject(i);
                FWImageItem fwImageItem = new FWImageItem();
                if(item.getBoolean("is_album")) {
                    fwImageItem.setId(item.getString("cover"));
                } else {
                    fwImageItem.setId(item.getString("id"));
                }
                fwImageItem.setTitle(item.getString("title"));

                fwImageItemList.add(fwImageItem); // Add photo to list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Result.Success<>(fwImageItemList);
    }

}
