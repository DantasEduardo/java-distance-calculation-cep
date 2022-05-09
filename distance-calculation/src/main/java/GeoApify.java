import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;


public class GeoApify {
    private String json;
    private  StringBuilder jsonSb;
    private String logradouro;
    private String localidade;
    private String uf;


    public GeoApify(String logradouro, String localidade, String uf) {
        this.jsonSb = new StringBuilder();
        this.logradouro = logradouro;
        this.localidade = localidade;
        this.uf = uf;
    }

    public String[] buscarLatidudeLongitude(String cep) {
        String[] list = new String[2];
        try {
            String endpoint = String.format("https://api.geoapify.com/v1/geocode/search?street=%s&postcode=%s&city=%s&state=%s&country=Brazil&limit=1&format=json&apiKey=03197b59532d488d8529d2c3d663c4d5",
                    this.logradouro, cep, this.localidade,this.uf).replace(" ","%20");
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
                list[0] = String.valueOf(jsonObject.get("lon"));
                list[1] = String.valueOf(jsonObject.get("lat"));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
