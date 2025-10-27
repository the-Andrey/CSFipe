package com.example.fipe_to_csv;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Preferences {

    private static final String PREF_NAME = "VEHICLE_PREFS";

    // Chaves únicas
    private static final String KEY_BRAND_NAME = "brand_name";
    private static final String KEY_MODEL_NAME = "model_name";
    private static final String KEY_VEHICLE_TYPE = "vehicle_type";
    private static final String KEY_VEHICLES = "vehicles_list";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public Preferences(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void addVehicle(Cars car) {
        List<Cars> vehicles = getSavedVehicles(); // pega lista atual
        vehicles.add(car);                        // adiciona novo veículo
        String json = gson.toJson(vehicles);      // converte para JSON
        editor.putString(KEY_VEHICLES, json);
        editor.apply();
    }

    public List<Cars> getSavedVehicles() {
        String json = sharedPreferences.getString(KEY_VEHICLES, null);
        List<Cars> vehicles;

        try {
            Type type = new TypeToken<List<Cars>>() {}.getType();
            vehicles = gson.fromJson(json, type);
            if (vehicles == null) vehicles = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            // em caso de dado corrompido, limpa a lista e retorna vazia
            editor.remove(KEY_VEHICLES).apply();
            vehicles = new ArrayList<>();
        }

        return vehicles;
    }

    public void setBrandName(String brandName) {
        editor.putString(KEY_BRAND_NAME, brandName);
        editor.apply();
    }

    public void setModelName(String modelName) {
        editor.putString(KEY_MODEL_NAME, modelName);
        editor.apply();
    }

    public void setVehicleType(String vehicleType) {
        editor.putString(KEY_VEHICLE_TYPE, vehicleType);
        editor.apply();
    }

    public String getBrandName() {
        return sharedPreferences.getString(KEY_BRAND_NAME, "Default");
    }

    public String getModelName() {
        return sharedPreferences.getString(KEY_MODEL_NAME, "Default");
    }

    public String getVehicleType() {
        return sharedPreferences.getString(KEY_VEHICLE_TYPE, "Default");
    }
    
    public void clearVehicles() {
        editor.remove(KEY_VEHICLES);
        editor.apply();
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}
