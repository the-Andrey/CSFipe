package com.example.fipe_to_csv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private Preferences prefs;
    private LinearLayout containerFavorites;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        prefs = new Preferences(this);
        containerFavorites = findViewById(R.id.containerFavorites);

        showFavoriteCars();
    }

    @SuppressLint("SetTextI18n")
    private void showFavoriteCars() {
        containerFavorites.removeAllViews(); // limpa antes de adicionar

        List<Cars> cars = prefs.getSavedVehicles();
        List<Cars> carsDetails = (List<Cars>) getIntent().getSerializableExtra("cars_list");

        if (cars.isEmpty()) {
            Toast.makeText(this, "Nenhum veículo favoritado!", Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < cars.size(); i++) {
            Cars car = cars.get(i);

            View itemView = inflater.inflate(R.layout.item_favorite, containerFavorites, false);


            TextView tvCarInfo = itemView.findViewById(R.id.tvCarInfo);
            Button btnRemove = itemView.findViewById(R.id.btnRemove);

            String selectedType;
            if (car.getVhType().equals("cars")) {
                selectedType = "carro";
            } else if (car.getVhType().equals("motorcycles")) {
                selectedType = "motocicleta";
            } else {
                selectedType = "caminhão";
            }


            tvCarInfo.setText("Tipo de veículo: " + selectedType + "\n" +
                    "Marca: " + car.getBrand() + "\n" +
                    "Modelo: " + car.getModel() + "\n");

            int index = i;

            btnRemove.setOnClickListener(v -> removeVehicle(index));


            containerFavorites.addView(itemView);
        }
    }

    private void removeVehicle(int index) {
        List<Cars> cars = prefs.getSavedVehicles();
        if (index >= 0 && index < cars.size()) {
            cars.remove(index);
            prefs.clearVehicles(); // limpa a lista
            for (Cars car : cars) {
                prefs.addVehicle(car); // salva novamente
            }
            showFavoriteCars(); // atualiza tela
        }
    }
}
