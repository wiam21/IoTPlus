package main;

import acquisition.DataCam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GestionCamMain {
    private static String USER = "polytech";
    private static String PASSWORD = "yes you are polytech 2019";
    private static int READ_TIMEOUT = 1000 ;


    public static void main(String[] args)
    {
        DataCam MyDataCam = new DataCam("image",
                "FI9816P",
                "https://polytech-iotready-cloud.cantor.fr/s/N3KGdMWcNPi2qCX",
                0,
                0,
                "C" );

        ObjectMapper mapper = new ObjectMapper();
        String MyJSON = null;

        /**
         * Transforme un Objet de type DataCam en chaine de caractère JSON
         *
         * @param  Objet au format DataCam
         * @return JSON en chaine de caractères contenant les champs de obj
         * @s DataCam
         */
        try {
            MyJSON = mapper.writeValueAsString(MyDataCam);
            System.out.println("Data JSON : " + MyJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        URL url = null;
        try {url = new URL("https://polytech-iotready-es.cantor.fr");}
        catch (MalformedURLException e) {e.printStackTrace();}

        String Method = "POST";
        HttpURLConnection con = null;
        try {con = setConnection(url, Method);}
        catch (IOException e) {e.printStackTrace();}

        try {sendData(con, MyJSON);}
        catch (IOException e) {e.printStackTrace();}

        try (InputStream is = con.getInputStream())
        {
            System.out.println("Post inputStream "+read(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    /**
     * Mise en place de la connexion HttpURLConnection avec tous ses paramètres.
     *
     * @param URL    URL pour effectuer l'action (dépend aussi de la méthode)
     * @param Method Méthode de connexion (GET POST PUT DELETE)
     * @return HttpURLConnection mise en place avec tous ses paramètres
     * @throws IOException Problème de configuration
     */
    private static HttpURLConnection setConnection(URL URL, String Method) throws IOException
    {
        HttpURLConnection con;
        con = (HttpURLConnection) URL.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod(Method);
        String userpass = USER + ":" + PASSWORD;
        String basicAuth;
        basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes(
                StandardCharsets.UTF_8));
        con.setRequestProperty("Authorization", basicAuth);
        con.setDoOutput(true);
        con.setReadTimeout(READ_TIMEOUT);
        con.setConnectTimeout(READ_TIMEOUT);
        return con;
    }

    /**
     * Écriture du flux sortant.
     *
     * @param con  HttpURLConnection paramétrée.
     * @param data Chaine de caractère à écrire.
     * @throws IOException Problème lors de l'écriture.
     */
    private static void sendData(HttpURLConnection con, String data) throws IOException
    {
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream()))
        {
            wr.writeBytes(data);
            wr.flush();
        }
    }

    /**
     * Lecture du flux entrant.
     *
     * @param is Flux entrant (renvoyé par ElasticSearch) à lire.
     * @return Chaine de caractère comportant la réponse du serveur.
     * @throws IOException Problème lors de la lecture du flux.
     */
    private static String read(InputStream is) throws IOException
    {
        String inputLine;
        StringBuilder body;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is)))
        {
            body = new StringBuilder();
            while ( (inputLine = in.readLine()) != null )
            {
                body.append(inputLine);
            }
            in.close();
            return body.toString();
        }
    }
}
