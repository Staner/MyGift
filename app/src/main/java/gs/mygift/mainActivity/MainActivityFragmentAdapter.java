package gs.mygift.mainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gs.mygift.mainActivity.fragments.FragmentFriendsList;
import gs.mygift.mainActivity.fragments.FragmentMyGifts;

/**
 * Created by Sergei on 7/17/2016.
 */
public class MainActivityFragmentAdapter extends FragmentPagerAdapter {


    int mNumOfTabs;

    public MainActivityFragmentAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position ) {
        switch (position){
            case 0:
                FragmentMyGifts myGifts = new FragmentMyGifts();
                return myGifts;
            case 1:
                FragmentFriendsList friendsList = new FragmentFriendsList();
                return friendsList;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return mNumOfTabs;

    }
}