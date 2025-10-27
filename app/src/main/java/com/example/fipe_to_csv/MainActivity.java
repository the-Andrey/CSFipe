package com.example.fipe_to_csv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Spinner spinnerBrands;
    Spinner spinnerModels;
    Spinner spinnerTypeVehicles;
    Spinner spinnerYearsModels;
    Button btnDetails2;
    Button btnFavorite;
    Button btnShowFavs;
    private Preferences prefs;



    List<String> typeVehicles = Arrays.asList(
            "Carros",
            "Motocicletas",
            "Caminhão"
    );

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        prefs = new Preferences(this);

        spinnerBrands = findViewById(R.id.spinnerBrand);
        spinnerModels = findViewById(R.id.spinnerModels);
        spinnerTypeVehicles = findViewById(R.id.spinnerTypeVehicles);

        btnDetails2 = findViewById(R.id.btnDetails2);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShowFavs = findViewById(R.id.btnFavorited);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                typeVehicles
        );

        ApiClient apiClient = RetrofitClient.getRetrofit().create(ApiClient.class);

        // spinner dos tipos de veículos
        spinnerTypeVehicles.setAdapter(adapter);

        spinnerTypeVehicles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = position == 0 ? "cars" : position == 1 ? "motorcycles" : "trucks";


                Call<List<Brand>> callMarcas = apiClient.getBrandPerType(selectedType);

                callMarcas.enqueue(new Callback<List<Brand>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Brand>> call, @NonNull Response<List<Brand>> response) {
                        if (response.isSuccessful() && response.body() != null){
                            List<Brand> brands = response.body();

                            //Log.d("API_JSON_BRAND", new Gson().toJson(brands));

                            List<String> brandNames = new ArrayList<>();

                            for(Brand brand:brands){
                                brandNames.add(brand.getName());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    brandNames
                            );



                            // spinner das marcas por tipo de veículo
                            spinnerBrands.setAdapter(adapter);

                            spinnerBrands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Brand selectedBrand = brands.get(position);
                                    int brandId = Integer.parseInt(selectedBrand.getCode());
                                    String brandName = selectedBrand.getName();



                                    Call<List<Model>> callModels = apiClient.getModelPerBrand(selectedType, brandId);

                                    callModels.enqueue(new Callback<List<Model>>() {
                                        @Override
                                        public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                                            if (response.isSuccessful() && response.body()!=null){
                                                List<Model> models = response.body();

                                                //Log.d("API_JSON_MODEL", new Gson().toJson(models));

                                                List<String> modelNames = new ArrayList<>();

                                                for(Model model:models){
                                                    modelNames.add(model.getName());
                                                }

                                                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                        MainActivity.this,
                                                        android.R.layout.simple_spinner_dropdown_item,
                                                        modelNames
                                                );

                                                //spinner dos modelos por marcas
                                                spinnerModels.setAdapter(adapter);

                                                spinnerModels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        Model selectedModel = models.get(position);
                                                        int modelId = Integer.parseInt(selectedModel.getCode());
                                                        String modelName = selectedModel.getName();



                                                        Call<List<Years>> callYears = apiClient.getYearsPerModel(selectedType, brandId, modelId);

                                                        callYears.enqueue(new Callback<List<Years>>() {
                                                            @Override
                                                            public void onResponse(Call<List<Years>> call, Response<List<Years>> response) {
                                                                if(response.isSuccessful() && response.body()!=null){
                                                                    List<Years> years = response.body();


                                                                    //Log.d("API_JSON_YEARS", new Gson().toJson(years));
                                                                    List<Cars> carsDetails = new ArrayList<>();

                                                                    for(Years year:years){
                                                                        String yearId = year.getCode();

                                                                        // consulta todos os anos de veículos baseados no tipo, marca e modelo escolhidos
                                                                        Call<Cars> details = apiClient.getVehicleFullDetails(selectedType,brandId,modelId,yearId);
                                                                        details.enqueue(new Callback<Cars>() {
                                                                            @Override
                                                                            public void onResponse(Call<Cars> call, Response<Cars> response) {
                                                                                if(response.isSuccessful() && response.body() != null){
                                                                                    Cars car = response.body();
                                                                                    carsDetails.add(car);


                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<Cars> call, Throwable t) {

                                                                            }
                                                                        });
                                                                    }

                                                                    // botao que envia a lista de carros consultados para a outra janela
                                                                    btnDetails2.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(MainActivity.this, DetailsActivity2.class);
                                                                        intent.putExtra("cars_list", (Serializable) carsDetails); // envia a lista inteira
                                                                        startActivity(intent);
                                                                    });

                                                                    btnFavorite.setOnClickListener(v -> {
                                                                        
                                                                        prefs.setVehicleType(selectedType);
                                                                        prefs.setBrandName(brandName);
                                                                        prefs.setModelName(modelName);

                                                                        
                                                                        String vhType = prefs.getVehicleType(); 
                                                                        String brand = prefs.getBrandName();
                                                                        String model = prefs.getModelName();

                                                                        Log.d("PREFS_TYPE_NAME", vhType);
                                                                        Log.d("PREFS_BRAND_NAME", brand);
                                                                        Log.d("PREFS_MODEL_NAME", model);

                                                                        
                                                                        prefs.addVehicle(new Cars(vhType, brand, model));

                                                                        Toast.makeText(MainActivity.this, "Veículo favoritado!", Toast.LENGTH_SHORT).show();
                                                                    });


                                                                    btnShowFavs.setOnClickListener(v -> {
                                                                        List<Cars> carsfavs = prefs.getSavedVehicles();

                                                                        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                                                                        intent.putExtra("cars_favs", (Serializable) carsfavs); // envia a lista de favoritados
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<List<Years>> call, Throwable t) {
                                                                //Log.e("API_JSON_YEARS", "Resposta não foi bem-sucedida");
                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Model>> call, Throwable t) {
                                            Log.e("API_JSON_MODEL", "Resposta não foi bem-sucedida");
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Brand>> call, Throwable t) {
                        Log.e("API_JSON_BRAND", "Resposta não foi bem-sucedida");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}