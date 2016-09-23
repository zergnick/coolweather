package com.coolweather.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chai on 2016/9/13.
 */
public class Utility {
    /**
     * Deal with province data from server
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    // solved data to be saved into Table Province
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Deal with city data from server
     */
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0){
                for (String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    // solved data to be saved into Table City
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Deal with country data from server
     */
    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    // solved data to be saved into Table City
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Deal with JASON data from server and save the same in local
     */
    public static void handleWeatherResponse(Context context, String response){
        try{
            JSONObject jsonObject = new JSONObject(response);

            JSONObject weatherInfo = jsonObject.getJSONObject("retData");
            String cityName = weatherInfo.getString("city");
            String cityCode = weatherInfo.getString("citycode");
            String temp1 = weatherInfo.getString("l_tmp");
            String temp2 = weatherInfo.getString("h_tmp");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("date");
            String windDirection = weatherInfo.getString("WD");
            String windSpeed = weatherInfo.getString("WS");
            saveWeatherInfo(context,cityName,cityCode,temp1,temp2,weatherDesp,publishTime,windDirection,windSpeed);



        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    /**
     * Save all weather data from server to SharedPreferences folder
     */
    public static void saveWeatherInfo(Context context,String cityName,String cityCode,String temp1,String temp2,String weatherDesp,String publishTime,String windDirection,String windSpeed){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("city_code",cityCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.putString("wind_direction",windDirection);
        editor.putString("wind_speed",windSpeed);
        editor.commit();
    }

}
