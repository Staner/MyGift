package gs.mygift.myClass;

import java.util.ArrayList;

/**
 * Created by Sergei on 7/17/2016.
 */
public class Friend {

    private int Id;
    private String name;
    private ArrayList<Gift> gifts;
    private String pic;

    public Friend(int id, String name) {
        Id = id;
        this.name = name;
        gifts = new ArrayList<>();

    }

    public Friend() {
    }

    public void addGift(Gift gift){
        this.gifts.add(gift);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(ArrayList<Gift> gifts) {
        this.gifts = gifts;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
