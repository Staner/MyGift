package gs.mygift.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import gs.mygift.R;
import gs.mygift.editGift.EditGiftActivity;
import gs.mygift.firebase.database.FB;
import gs.mygift.login.Login;
import gs.mygift.myClass.Friend;
import gs.mygift.myClass.Gift;

public class MainActivity extends AppCompatActivity {

    static public Gift choiceGift;
    static public Friend choiceFriend;
    static public ArrayList<Gift> gifts;
    static public ArrayList<Friend> friends;



    public static FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
        } else {

            //addFriends();

            setTitle(currentUser.getDisplayName());
            MainActivity.gifts = new ArrayList<>();

            FB.loadData();

            choiceGift = new Gift();

            choiceFriend = new Friend();

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("My gifts"));
            tabLayout.addTab(tabLayout.newTab().setText("My Friends"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final MainActivityFragmentAdapter adapter = new MainActivityFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(onTabSelectedListener(viewPager));


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    choiceGift.setKey(null);
                    Intent intent = new Intent(MainActivity.this, EditGiftActivity.class);
                    startActivity(intent);

                }
            });

        }

        addFriends();
    }


    public void addFriends() {
        Log.d("name: ","addFriends");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("name: ",response.toString());
                        try {
                            Log.d("name: ","json");
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");
                            for (int i=0; i < rawName.length();i++){
                                Log.d("name: ","for");
                                Log.d("name: ",rawName.getJSONObject(i).getString("name"));
                            }
                        } catch (JSONException e) {
                            Log.d("name: ",e.toString());
                            e.printStackTrace();
                        }


                    }
    });

    }






    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                Log.d("tab", tab.getPosition() + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }
}
