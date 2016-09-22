package gs.mygift.mainActivity.fragments.adapters;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gs.mygift.R;
import gs.mygift.description.DescriptionActivity;
import gs.mygift.editGift.EditGiftActivity;
import gs.mygift.firebase.database.FB;
import gs.mygift.mainActivity.MainActivity;
import gs.mygift.myClass.Gift;

/**
 * Created by Sergei on 7/17/2016.
 */
public class GiftRecyclerViewAdapter extends RecyclerView.Adapter<GiftRecyclerViewAdapter.GroupViewHolder> {

        List<Gift> gifts;
        Context context;

      public static FirebaseDatabase database = FirebaseDatabase.getInstance();


public GiftRecyclerViewAdapter(ArrayList<Gift> gifts, Context context) {
        this.gifts = gifts;
        this.context = context;
        }


    public static class GroupViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    TextView tvName;
    ImageView ivPhoto;
    TextView tvPrice;


    GroupViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);

    }
}

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_my_gift, viewGroup, false);
        GroupViewHolder pvh = new GroupViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder personViewHolder, final int i) {


        personViewHolder.tvName.setText(gifts.get(i).getName());
        personViewHolder.tvPrice.setText("Price: "+gifts.get(i).getPrice()+"$");

        if (gifts.get(i).getUri()!=null){
            personViewHolder.ivPhoto.setBackgroundResource(0);
            Picasso.with(context).load(gifts.get(i).getUri()).into(personViewHolder.ivPhoto);
        }

        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("cv", "click");

                choice(i);
                Intent intent = new Intent(context, DescriptionActivity.class);
                context.startActivity(intent);
            }


        });


       personViewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

           if (context instanceof MainActivity) {
                 choice(i);
                 openGiftDialog(gifts.get(i));
                 Log.d("cv", "long click");
                 }
                return true;
            }
        });


    }

    private void choice(int position) {

        MainActivity.choiceGift.setName(gifts.get(position).getName());
        MainActivity.choiceGift.setDescription(gifts.get(position).getDescription());
        MainActivity.choiceGift.setPrice(gifts.get(position).getPrice());
        MainActivity.choiceGift.setKey(gifts.get(position).getKey());
        MainActivity.choiceGift.setUserId(gifts.get(position).getUserId());

        if (gifts.get(position).getUri()!=null){
            MainActivity.choiceGift.setUri(gifts.get(position).getUri());
        }

    }

    private void openGiftDialog(final Gift gift) {


        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.gift_dialog);
        dialog.setTitle("Gift: "+gift.getName());
        dialog.setCancelable(true);
        Button editGift = (Button) dialog.findViewById(R.id.editGift);
        Button deleteGift = (Button) dialog.findViewById(R.id.deleteGift);

        editGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditGiftActivity.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        deleteGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDeleteDialog(gift);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void openDeleteDialog(final Gift gift) {

        final Dialog dialogDelete = new Dialog(context);
        dialogDelete.setContentView(R.layout.gift_delete_dialog);
        dialogDelete.setCancelable(true);

        Button btnDontWantIt = (Button) dialogDelete.findViewById(R.id.btnDontWantIt);
        Button btnGotThisGift = (Button) dialogDelete.findViewById(R.id.btnHaveThisGift);

        TextView textView = (TextView) dialogDelete.findViewById(android.R.id.title);
        textView.setText("Gift name: "+gift.getName());
        if(textView != null) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }



        btnDontWantIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FB.updateStatusGift(gift,"don't want");
                dialogDelete.dismiss();
            }
        });

        btnGotThisGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FB.updateStatusGift(gift,"have");
                dialogDelete.dismiss();
            }
        });

        dialogDelete.show();
    }




    @Override
    public int getItemCount() {
        return gifts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}

