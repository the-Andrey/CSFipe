package com.example.fipe_to_csv;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DetailsActivity2 extends AppCompatActivity {

    Button btnExport;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        btnExport = findViewById(R.id.buttonExport);

        List<Cars> carsList = (List<Cars>) getIntent().getSerializableExtra("cars_list");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCars);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CarsAdapter adapter = new CarsAdapter(carsList);
        recyclerView.setAdapter(adapter);

        btnExport.setOnClickListener(v -> {
            if (carsList != null && !carsList.isEmpty()) {
                CSVUtils.writeCarsToCSV(this, carsList, "car_table.csv");
            } else {
                Toast.makeText(this, "Lista vazia!", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
