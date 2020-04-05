package main;

import acquisition.DataCam;
import uploadjson.ElasticSearchManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;


public class GestionCamMain {


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
        ElasticSearchManager ESManag = new ElasticSearchManager();

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


        try {
            System.out.println("Post du JSON : " +ESManag.post("https://polytech-iotready-es.cantor.fr", MyJSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
