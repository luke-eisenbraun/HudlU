package eisenbraun.luke_eisenbraun.hudlu.models;

import io.realm.RealmObject;

/**
 * Created by luke.eisenbraun on 3/31/2016.
 */
public class Favorite extends RealmObject {
    private String title;
    private String author;
    private String image;
    private String link;
    public Favorite(){}
    public Favorite(String title, String author, String image, String link){
        this.title = title;
        this.author = author;
        this.image = image;
        this.link = link;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getImage(){
        return image;
    }
    public String getLink(){
        return link;
    }
}