package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Log.wtf("Z_json", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.optJSONObject("name");

            return new Sandwich(name.optString("mainName"),
                    getListFromJSONArray(name.optJSONArray("alsoKnownAs")),
                    jsonObject.optString("placeOfOrigin"),
                    jsonObject.optString("description"),
                    jsonObject.optString("image"),
                    getListFromJSONArray(jsonObject.optJSONArray("ingredients"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getListFromJSONArray(JSONArray jsonArray) {
        ArrayList<String> listData = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i< jsonArray.length(); i++){
                listData.add(jsonArray.optString(i));
            }
        }
        return listData;
    }
}
