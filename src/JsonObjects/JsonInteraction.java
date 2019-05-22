package JsonObjects;

import java.util.ArrayList;

/**
 * Interficie que tracta la serielitzacio i la deserialitzacio per tot objecte que tracti amb fitxers JSON
 * @author Alexander Roca
 * @version 0.1
 */
public interface JsonInteraction {

    /**
     * Funcio que deserialitza el fitxer JSON a Object
     * @param path : String
     * @return Array de Object
     */
     Object[] deserializeJSON(String path);

    /**
     * Procediment que serialitza un ArrayList d'objectes a JSON
     * @param obj : ArrayList de Object
     * @param name : String
     */
     void serializeJSON(ArrayList<Object> obj, String name);
}
