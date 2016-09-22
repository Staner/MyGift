package gs.mygift.mainActivity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gs.mygift.R;
import gs.mygift.mainActivity.MainActivity;
import gs.mygift.mainActivity.fragments.adapters.GiftRecyclerViewAdapter;

/**
 * Created by Sergei on 7/17/2016.
 */
public class FragmentMyGifts extends Fragment {

    public  static View view;


    static public RecyclerView RV;
    static public GiftRecyclerViewAdapter GiftRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {





        view = inflater.inflate(R.layout.fragment_my_gifts,container,false);

        RV = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        RV.setLayoutManager(llm);
        GiftRecyclerViewAdapter = new GiftRecyclerViewAdapter(MainActivity.gifts,getActivity());
        RV.setAdapter(GiftRecyclerViewAdapter);

        return view;
    }


}