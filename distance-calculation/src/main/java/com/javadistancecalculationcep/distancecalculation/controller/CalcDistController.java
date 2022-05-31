package com.javadistancecalculationcep.distancecalculation.controller;

import com.javadistancecalculationcep.distancecalculation.model.ViaCep;
import com.javadistancecalculationcep.distancecalculation.rest.ViaCepClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

@RestController
@RequestMapping("/calc")
public class CalcDistController {

    @Autowired
    private ViaCepClient clienteViaCep;

    @GetMapping("/dist/{cep1}/{cep2}")
    public ResponseEntity getDist(@PathVariable String cep1,
                                  @PathVariable String cep2){

        ViaCep cepEncontrado1 = clienteViaCep.getCep(cep1);
        ViaCep cepEncontrado2 = clienteViaCep.getCep(cep2);

        Double[] lonlat1 = buscarLatidudeLongitude(cepEncontrado1.getLogradouro(),
                                                    cep1,
                                                    cepEncontrado1.getLocalidade(),
                                                    cepEncontrado1.getUf());

        Double[] lonlat2 = buscarLatidudeLongitude(cepEncontrado2.getLogradouro(),
                                                   cep2,
                                                   cepEncontrado2.getLocalidade(),
                                                   cepEncontrado2.getUf());

        Double dist = calcularDisntancia(lonlat1,lonlat2);

        return ResponseEntity.status(200).body(dist);
    }

    public Double[] buscarLatidudeLongitude(String logradouro, String cep, String localidade, String uf) {
        Double[] list = new Double[2];
        try {
            String endpoint = String.format("https://api.geoapify.com/v1/geocode/search?street=%s&postcode=%s&city=%s&state=%s&country=Brazil&limit=1&format=json&apiKey=03197b59532d488d8529d2c3d663c4d5",
                    logradouro, cep, localidade, uf).replace(" ","%20");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject object = new JSONObject(response.body()); //jsonString = String from the file
            JSONArray array = object.getJSONArray("results");
            Iterator<Object> iterator = array.iterator();
            while(iterator.hasNext()){
                JSONObject jsonObject = (JSONObject) iterator.next();

                list[0] = Double.valueOf(String.valueOf(jsonObject.get("lon")));
                list[1] = Double.valueOf(String.valueOf(jsonObject.get("lat")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static Double toRad(Double value) {
        return (value * Math.PI) / 180;
    }

    public static Double calcularDisntancia(Double[] lugar1, Double[] lugar2){
        System.out.println(lugar1[0]+"\t"+lugar1[1]);
        System.out.println(lugar2[0]+"\t"+lugar2[1]);

        Double long1 = toRad(lugar1[0]);
        Double lati1 = toRad(lugar1[1]);
        Double long2 = toRad(lugar2[0]);
        Double lati2 = toRad(lugar2[1]);

        Double dlon = long2 - long1;
        Double dlat = lati2 - lati1;

        Double sinDlatSquare = Math.pow(Math.sin(dlat/2),2);
        Double sinDlonSquare = Math.pow(Math.sin(dlon/2),2);

        Double a = sinDlatSquare + Math.cos(lati1) * Math.cos(lati2) * sinDlonSquare;

        Double b = 2*(Math.asin(Math.sqrt(a)));

        return 6371 * b;
            /*
    *   dlon = lon2 - lon1
        dlat = lat2 - lat1
        a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
        c = 2 * atan2( sqrt(a), sqrt(1-a) )
        d = R * c
        R = 6367 km
    *
    * */
    }
}
