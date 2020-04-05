package acquisition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Objet contenant une donnée venant d'une caméra IP. Chaque mesure comporte champs suivants:
 * <ul>
 *     <li>L'identifiant de la caméra (,...)</li>
 *     <li>Un type (image, son, vidéo)</li>
 *     <li>L'URL NextCloud ou le média est stocké </li>
 *     <li>La date a laquelle la mesure a ete faite (format ISO8601)</li>
 * </ul>
 * Et deux marqueurs d'erreurs :
 * <ul>
 *     <li>erreurMesure (Mise a 1 lorsqu'il y a eu une erreur de connection avec le capteur)</li>
 *     <li>erreurUpload (Mise a 1 lorsque la connection internet n'etait pas etablie lors du dernier essai d'envoi de la mesure)</li>
 * </ul>
 */
@SuppressWarnings({ "WeakerAccess", "unused" }) public class DataCam
{
    // Ensemble des attributs correspondant a une mesure :
    private String type;
    private String date;
    private String camId;
    private String url ; //URL de l'image sur NEXTCLOUD --modif*/
    private int measureError;
    private int uploadError;
    private String connected;

    /**
     * Constructeur d'une mesure avec création de la date.
     *
     * @param type      (image, son, vidéo)
     * @param camId      L'identifiant du capteur ayant effectue la mesure
     * @param url       URL NextCloud ou le média est stocké
     * @param measureError  Erreur lors de la mesure ('1' quand il y a une erreur)
     * @param uploadError   Erreur lors de l'upload
     * @param connected     indique si le capteur est connecté
     */

    public DataCam(String type, String camId, String url, int measureError, int uploadError, String connected)
    {
        // Definition du format ISO8601 :
        // Format de la date
        SimpleDateFormat ISO8601DATEFORMAT;
        ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ" , Locale.FRANCE);
        this.date = ISO8601DATEFORMAT.format(new Date());
        // Creation de la date et affectation de celle-ci

        // Affectation des attributs de la mesure
        this.type = type;
        this.camId = camId;
        this.url = url;
        this.measureError = measureError;
        this.uploadError = 0;
        this.connected = connected;
    }
    /**
     * Constructeur d'une mesure avec passage en parametre de la date.
     *
     * @param date          String correspondant a la date au format ISO8601
     * @param camId      L'identifiant du capteur ayant effectue la mesure (DRG2A14, DRG2A15,...)
     * @param type          Grandeur de la mesure (temperature, humidite, ...)
     * @param measureError  Erreur lors de la mesure ('1' quand il y a une erreur)
     * @param connected     indique si le capteur est connecté
     */
    public DataCam(String date,String type, String camId, String url, int measureError, int uploadError, String connected)
    {
        this.date = date;
        this.type = type;
        this.camId = camId;
        this.measureError = measureError;
        this.uploadError = 0;
        this.connected = connected;
        this.url = url;
    }

    /**
     * Constructeur vide pour le parser.
     */
    public DataCam() {}

    // Getters
    public String gettype()
    {
        return this.type;
    }

    public String getdate()
    {
        return this.date;
    }

    public String getcamId()
    {
        return this.camId;
    }

    public int getmeasureError()
    {
        return this.measureError;
    }

    public int getuploadError()
    {
        return this.uploadError;
    }

    public String getconnected()
    {
        return connected;
    }

    public String geturl()
    {
        return url;
    }

    // Setters
    public void settype(String s)
    {
        this.type = s;
    }

    public void setdate(String s)
    {
        this.date = s;
    }

    public void setcamId(String s)
    {
        this.camId = s;
    }

    public void setmeasureError(int measureError)
    {
        this.measureError = measureError;
    }

    public void setuploadError(int uploadError)
    {
        this.uploadError = uploadError;
    }

    public void setconnected(String connected)    {
        this.connected = connected;
    }

	public void seturl(String url)
	{
		this.url = url;
	}

    /**
     * Redéfinition de la méthode equals
     *
     * @param obj Donnée à comparer
     * @return 'true' si tous les champs de l'objet sont éguaux, 'flase' sinon
     */
    public boolean equals(Object obj)
    {
        if ( obj instanceof DataCam )
        {
            DataCam d = (DataCam) obj;
            return d.getcamId().equals(this.camId)
                    && d.getdate().equals(this.date)
                    && d.getmeasureError() == this.measureError
                    && d.gettype().equals(this.type)
                    && d.getuploadError() == this.uploadError
                    && d.getconnected().equals(this.connected)
                    && d.geturl().equals(this.url);
        }
        return false;
    }
}
