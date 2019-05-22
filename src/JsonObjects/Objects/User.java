package JsonObjects.Objects;

import JsonObjects.JsonInteraction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe que conte la informacio de tot usuari, aquesta classe usa la interficie JsonInteraction
 * @author Alexander Roca
 * @version 0.1
 */
public class User implements JsonInteraction {
    private String username;
    private long creation;
    private ArrayList<String> to_follow;
    private MyArrayList<String> my_to_follow;

    public MyArrayList<String> getMy_to_follow() {
        return my_to_follow;
    }

    public void setMy_to_follow(ArrayList<String> to_follow) {
        try {
            my_to_follow = new MyArrayList<>(to_follow.size());
            for (int i = 0; i < to_follow.size(); i++)
                my_to_follow.add(to_follow.get(i));
        }catch(NullPointerException e){
            System.out.println(username + " no segueix a cap usuari, per tan no esta conectat a ningu");
        }
    }

    public void setMy_to_follow(MyArrayList to_follow){
        this.my_to_follow = to_follow;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreation() {
        return creation;
    }

    public void setCreation(long creation) {
        this.creation = creation;
    }

    public ArrayList<String> getTo_follow() {
        return to_follow;
    }

    public void setTo_follow(ArrayList<String> to_follow) {
        this.to_follow = to_follow;
    }

    @Override
    public Object[] deserializeJSON(String path) {
        Gson gson = new GsonBuilder().create();
        User[] usuaris = null;
        try{
            usuaris = gson.fromJson(new BufferedReader(new FileReader(path)), User[].class);
        }catch (FileNotFoundException e){
            System.out.println("No s'ha llegit correctament el JSON");
        }
        return usuaris;
    }

    @Override
    public void serializeJSON(ArrayList<Object> obj, String name) {
        try (Writer writer = new FileWriter("jsons/" + name + ".json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(obj, writer);
        } catch (IOException e) {
            System.out.println("Problema en crear el JSON del resultat de l'ordenacio.");;
        }
    }
}
