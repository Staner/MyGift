package gs.mygift.myClass;



import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Sergei on 7/17/2016.
 */
public class Gift {

    private String userId;
    private String name;
    private Bitmap bitmap;
    private int price;
    private String description;
    private String uri;
    private String status;


   @JsonIgnore
   private String key;


    public Gift(String name, Bitmap bitmap, int price, String description) {
        this.name = name;
        this.bitmap = bitmap;
        this.price = price;
        this.description = description;

    }
    public Gift(){}



    public Gift(String name, int price, String description,String userId,Bitmap bitmap, String uri,String status ) {

        this.name = name;
        this.price = price;
        this.description = description;
        this.userId = userId;
        this.bitmap = bitmap;
        this.uri = uri;
        this.status =  status;


    }

    public Gift(String name, int price, String description,String userId,String status ) {

        this.name = name;
        this.price = price;
        this.description = description;
        this.userId = userId;
        this.status =  status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPic() {
        return bitmap;
    }

    public void setPic(Bitmap pic) {
        this.bitmap = pic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
