package JsonObjects.Objects;

import JsonObjects.JsonInteraction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Classe que conte la informacio de tot post, aquesta classe usa la interficie JsonInteraction
 * @author Alexander Roca
 * @version 0.1
 */
public class Post implements JsonInteraction {
    private int id;
    private ArrayList<String> liked_by;
    private MyArrayList<String> my_liked_by;
    private long published_when;
    private String published_by;
    private Vector location;
    private ArrayList<String> hashtags;
    private MyArrayList<String> my_hashtags;

    public MyArrayList<String> getMy_hashtags() {
        return my_hashtags;
    }

    public void setMy_ArrayLists(ArrayList<String> hashtags, ArrayList<String> liked_by) {

        my_hashtags = new MyArrayList<>(hashtags.size());
        my_liked_by = new MyArrayList<>(liked_by.size());

        for(int i = 0; i < hashtags.size(); i++)
            my_hashtags.add(hashtags.get(i));
        for(int i = 0; i < liked_by.size(); i++)
            my_liked_by.add(liked_by.get(i));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setLiked_by(ArrayList<String> liked_by) {
        this.liked_by = liked_by;
    }

    public long getPublished_when() {
        return published_when;
    }

    public MyArrayList<String> getMy_liked_by() {
        return my_liked_by;
    }

    public void setMy_liked_by(MyArrayList<String> my_liked_by) {
        this.my_liked_by = my_liked_by;
    }

    public void setMy_hashtags(MyArrayList<String> my_hashtags) {
        this.my_hashtags = my_hashtags;
    }

    public void setPublished_when(long published_when) {
        this.published_when = published_when;
    }

    public String getPublished_by() {
        return published_by;
    }

    public void setPublished_by(String published_by) {
        this.published_by = published_by;
    }

    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public ArrayList<String> getLiked_by() {
        return liked_by;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    @Override
    public Object[] deserializeJSON(String path) {
        Gson gson = new GsonBuilder().create();
        Post[] posts = null;
        try{
            posts = gson.fromJson(new BufferedReader(new FileReader(path)), Post[].class);
        }catch (FileNotFoundException e){
            System.out.println("No s'ha llegit correctament el JSON");
        }
        return posts;
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
