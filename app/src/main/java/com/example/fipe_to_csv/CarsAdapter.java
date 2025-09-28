package com.example.fipe_to_csv;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarViewHolder> {

    private List<Cars> carsList;

    public CarsAdapter(List<Cars> carsList) {
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Cars car = carsList.get(position);
        holder.tvBrand.setText(car.getBrand());
        holder.tvModel.setText(car.getModel());
        holder.tvYear.setText(String.valueOf(car.getModelYear()));
        holder.tvFipe.setText(car.getCodeFipe());
        holder.tvFuel.setText(car.getFuel());
        holder.tvPrice.setText(car.getPrice());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrand, tvModel, tvYear, tvFipe, tvFuel, tvPrice;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvFipe = itemView.findViewById(R.id.tvFipe);
            tvFuel = itemView.findViewById(R.id.tvFuel);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
