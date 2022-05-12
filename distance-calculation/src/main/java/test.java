public class test {
    private static Double toRad(Double value) {
        return (value * Math.PI) / 180;
    }

    public static Double calcularDisntancia(Double[] lugar1, Double[] lugar2){
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

        Double[] lonLatTESTE = {-46.6388,-23.5489};
        Double[] lonLat2TESTE = {-43.2096,-22.9035};

        Double teste = calcularDisntancia(lonLatTESTE,lonLat2TESTE);
        System.out.println(teste);

        Double teste2 = calcularDisntancia(lonLat,lonLat2);
        System.out.println(teste2);
    }
}
