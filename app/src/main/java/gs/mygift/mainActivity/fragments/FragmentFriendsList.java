package gs.mygift.mainActivity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gs.mygift.R;
import gs.mygift.mainActivity.MainActivity;
import gs.mygift.mainActivity.fragments.adapters.MyFriendRecyclerViewAdapter;
import gs.mygift.myClass.Friend;
import gs.mygift.myClass.Gift;

/**
 * Created by Sergei on 7/17/2016.
 */
public class FragmentFriendsList extends Fragment {

    public  static View view;

    static public RecyclerView RV;
    static public MyFriendRecyclerViewAdapter myFriendRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends_list,container,false);


        demoFriends();



        view = inflater.inflate(R.layout.fragment_my_fiends,container,false);

        RV = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        RV.setLayoutManager(llm);
        myFriendRecyclerViewAdapter = new MyFriendRecyclerViewAdapter(MainActivity.friends,getActivity());
        RV.setAdapter(myFriendRecyclerViewAdapter);





        return view;
    }

    private void demoFriends() {


        MainActivity.friends = new ArrayList<>();

        for(int f=0;f<20;f++){

            Friend friend = new Friend(f,"Name friend");

            for(int g=0;g<20;g++){

                    Gift gift = new Gift("giftName",g,"description gift","sad","Open");
                    friend.addGift(gift);

            }

            MainActivity.friends.add(friend);


        }


    }
}
