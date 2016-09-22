package gs.mygift.mainActivity.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gs.mygift.R;
import gs.mygift.friendGifts.FriendGifts;
import gs.mygift.mainActivity.MainActivity;
import gs.mygift.myClass.Friend;

/**
 * Created by Sergei on 7/17/2016.
 */
public class MyFriendRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendRecyclerViewAdapter.GroupViewHolder> {

    List<Friend> friends;
    Context context;

    public MyFriendRecyclerViewAdapter(ArrayList<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }


    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tvName;
        ImageView ivPhoto;



        GroupViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);

        }
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_my_friend, viewGroup, false);
        GroupViewHolder pvh = new GroupViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder personViewHolder, final int i) {


        personViewHolder.tvName.setText(friends.get(i).getName());

        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("cv", "click");
                choice(i);

                Intent intent = new Intent(context, FriendGifts.class);
                context.startActivity(intent);

            }


        });

    }

    private void choice(int position) {

        MainActivity.choiceFriend.setName(friends.get(position).getName());
        MainActivity.choiceFriend.setGifts(friends.get(position).getGifts());
    }





    @Override
    public int getItemCount() {
        return friends.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

