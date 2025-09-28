package com.example.fipe_to_csv;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVUtils {

    public static void writeCarsToCSV(Context context, List<Cars> carsList, String fileName) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            }
            if (uri == null) throw new IOException("Não foi possível criar o arquivo CSV");

            try (OutputStream os = context.getContentResolver().openOutputStream(uri);
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {

                // Cabeçalho
                writer.write("Marca,Modelo,Ano,Fipe,Combustivel,Preco");
                writer.newLine();

                for (Cars car : carsList) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%s", // conteúdo da tabela
                            car.getBrand(),
                            car.getModel(),
                            car.getModelYear(),
                            car.getCodeFipe(),
                            car.getFuel(),
                            car.getPrice()));
                    writer.newLine();
                }
            }

            Toast.makeText(context, "CSV salvo em Downloads!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao salvar CSV: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}