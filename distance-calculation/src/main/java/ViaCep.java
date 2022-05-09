import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViaCep {
    private String json;
    private  StringBuilder jsonSb;


    public ViaCep() {
        this.jsonSb = new StringBuilder();
    }

    public String buscarCep(String cep) {
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            br.lines().forEach(l -> this.jsonSb.append(l.trim()));

            this.json = this.jsonSb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.json;
    }

    public String[] mapping(){
        Map<String,String> mapa = new HashMap<>();

        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(this.json);
        while (matcher.find()) {
            String[] group = matcher.group().split(":");
            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
        }

        String[] list = {mapa.get("logradouro"),mapa.get("bairro"),mapa.get("localidade"),mapa.get("uf")};

        return list;
    }
}
