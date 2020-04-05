package uploadjson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ElasticSearchManager {
    private static String USER = "polytech";
    private static String PASSWORD = "yes you are polytech 2019";
    private static int READ_TIMEOUT = 1000 ;

    /**
     * Mise en place de la connexion HttpURLConnection avec tous ses paramètres.
     *
     * @param URL    URL pour effectuer l'action (dépend aussi de la méthode)
     * @param Method Méthode de connexion (GET POST PUT DELETE)
     * @return HttpURLConnection mise en place avec tous ses paramètres
     * @throws IOException Problème de configuration
     */
    public HttpURLConnection setConnection(URL URL, String Method) throws IOException
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
    public void sendData(HttpURLConnection con, String data) throws IOException
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
    private String read(InputStream is) throws IOException
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

    /**
     * Permet de mettre en ligne une mesure.
     *
     * @param postUrl URL pour poster.
     * @param data    JSON en chaine de caractères
     * @return Résultat de l'opéraiton : JSON renvoyé par ElasticSearch
     * @throws IOException Problème de connexion avec ElasticSearch
     */
    public String post(String postUrl, String data) throws IOException
    {
        URL url;
        url = new URL(postUrl);
        String Method = "POST";
        HttpURLConnection con = setConnection(url, Method);
        sendData(con, data);
        try (InputStream is = con.getInputStream())
        {
            return read(is);
        }
    }
}
