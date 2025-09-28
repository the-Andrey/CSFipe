package com.example.fipe_to_csv;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClient {

    // será preciso criar uma classe para cada GET, isso deve ser feito pois
    // cada GET tem informações específicas

    // CONSULTA PARA OBTER AS MARCAS POR TIPO
    @GET("{vehicleType}/brands")
    Call<List<Brand>> getBrandPerType(
            @Path("vehicleType") String vehicleType
    );

    //CONSULTA PARA OBTER OS MODELOS POR MARCA
    @GET("{vehicleType}/brands/{brandId}/models")
    Call<List<Model>> getModelPerBrand(
            @Path("vehicleType") String vehicleType,
            @Path("brandId") Integer brandId
    );

    //CONSULTA DE ANOS POR MODELO
    @GET("{vehicleType}/brands/{brandId}/models/{modelId}/years")
    Call <List<Years>> getYearsPerModel(
            @Path("vehicleType") String vehicleType,
            @Path("brandId") Integer brandId,
            @Path("modelId") Integer modelId
    );

    //CONSULTA PARA OBTER OS DADOS DA FIPE DO MODELO(QUEREMOS SOMENTE O CODIGO FIPE)
    @GET("{vehicleType}/brands/{brandId}/models/{modelId}/years/{yearId}")
    Call<Cars> getVehicleFullDetails(
            @Path("vehicleType") String vehicleType,
            @Path("brandId") int brandId,
            @Path("modelId") int modelId,
            @Path("yearId") String yearId
    );

}
