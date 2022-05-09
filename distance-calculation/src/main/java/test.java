public class test {
    public  static Double calculoRad(Double value){
        return Math.toRadians(value);
    }

    public static Double diff(Double ini, Double fin){
        Double value = ini-fin;
        if (value<0){
            value=value*-1;
        }
        return value;
    }

    public static Double calculoSenCos(Double lat, Double lon, Double latIni, Double latFin){
        Double sin1 = Math.sin(lat/2);
        Double sin2 = Math.sin(lon/2);

        Double value = ((sin1*sin1) + Math.cos(latIni) * Math.cos(latFin) *(sin2*sin2));

        return value;
    }

    public static Double calculoCot(Double senCos){
        Double sqrt1 = Math.sqrt(senCos);
        Double sqrt2 = Math.sqrt(1-senCos);
        Double tan = Math.tan(sqrt1/sqrt2);

        Double value = 2 * (1/tan);
        return value;
    }

    public static Double calcularDisntancia(Double[] lugar1, Double[] lugar2){
        Double longitudeIni = lugar1[0];
        Double latitudeIni = lugar1[1];
        Double longitudeFin = lugar2[0];
        Double latitudeFin = lugar2[1];

        Double longitudeIniRad = calculoRad(longitudeIni);
        Double latitudeIniRad = calculoRad(latitudeIni);
        Double longitudeFinRad = calculoRad(longitudeFin);
        Double latitudeFinRad = calculoRad(latitudeFin);

        Double longitude = diff(longitudeIniRad,longitudeFinRad);
        Double latitude = diff(latitudeIniRad,latitudeFinRad);

        Double senCos = calculoSenCos(longitude,latitude,latitudeIniRad,latitudeFinRad);

        Double cot = calculoCot(senCos);

        return 6.371 * cot;
    }

    public static void main(String[] args) {
//        ViaCep viaCep = new ViaCep();
//        String json = viaCep.buscarCep("03805030");
//        String[]endereco = viaCep.mapping();

//        GeoApify geoApify = new GeoApify(endereco[0],endereco[2],endereco[3]);
//        String[] lonLat = geoApify.buscarLatidudeLongitude("03805030");
//        System.out.println(lonLat[0]);
//        System.out.println(lonLat[1]);

        Double[] lonLat = {-46.4805177,-23.5041115};
        Double[] lonLat2 = {-46.66162834754086,-23.55802478862016};

        Double[] lonLatTESTE = {-118.2436,34.0522};
        Double[] lonLat2TESTE = {139.7514,-35.6850};

        Double teste = calcularDisntancia(lonLatTESTE,lonLat2TESTE);
        System.out.println(teste);

        Double teste2 = calcularDisntancia(lonLat,lonLat2);
        System.out.println(teste2);
    }
}
