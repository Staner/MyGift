package gs.mygift.friendGifts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import gs.mygift.R;
import gs.mygift.mainActivity.MainActivity;
import gs.mygift.mainActivity.fragments.adapters.GiftRecyclerViewAdapter;

public class FriendGifts extends AppCompatActivity {

    static public RecyclerView RV;
    static public GiftRecyclerViewAdapter GiftRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_gift);

        RV = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        RV.setLayoutManager(llm);
        GiftRecyclerViewAdapter = new GiftRecyclerViewAdapter(MainActivity.choiceFriend.getGifts(),this);
        RV.setAdapter(GiftRecyclerViewAdapter);

    }
}
