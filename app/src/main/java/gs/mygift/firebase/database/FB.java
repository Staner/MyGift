package gs.mygift.firebase.database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import gs.mygift.mainActivity.MainActivity;
import gs.mygift.mainActivity.fragments.FragmentMyGifts;
import gs.mygift.myClass.Gift;

/**
 * Created by Sergei on 8/11/2016.
 */

public class FB {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference ref = database.getReference("Gifts");


    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReferenceFromUrl("gs://my-gift-1f8bc.appspot.com");


    public static Uri myUri;

    public static void saveGift(Gift gift, final Activity context) {


        Map<String, Object> newGift = new HashMap<>();
        newGift.put("name", gift.getName());
        newGift.put("description", gift.getDescription());
        newGift.put("price", gift.getPrice());
        newGift.put("userId", gift.getUserId());
        newGift.put("uri", gift.getUri().toString());
        newGift.put("status",gift.getStatus());

        ref.push().setValue(newGift).addOnSuccessListener(context, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("save success", "Saved!");
            }
        }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("save failure", e.toString());
            }
        });


        uploadImageToDataBase(context, gift);


    }

    private static void uploadImageToDataBase(final Context context, Gift gift) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        gift.getBitmap().compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] data = baos.toByteArray();

        StorageReference imageRef = storageRef.child("images/" + gift.getUri());

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                String imageAddress = downloadUri.toString();
                Toast.makeText(context, imageAddress, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void loadData() {

       Query queryRef = ref
               .orderByChild("userId")
               .equalTo(MainActivity.currentUser.getUid())
               .getRef();


        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MainActivity.gifts.clear();
                for (DataSnapshot dataSnapshotGift : dataSnapshot.getChildren()) {
                    Gift gift = dataSnapshotGift.getValue(Gift.class);
                    gift.setKey(dataSnapshotGift.getKey());
                    if (gift.getUri() != null) {
                        downloadImage(gift);
                    }
                    MainActivity.gifts.add(gift);
                    FragmentMyGifts.GiftRecyclerViewAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    public static void removeGift(Gift gift) {

        ref.child(gift.getKey()).removeValue();
    }

    public static void downloadImage(final Gift gift) {


        StorageReference pathRef = storageRef.child("images/" + gift.getUri());
        pathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                gift.setUri(uri.toString());
                FragmentMyGifts.GiftRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }


    public static void updateGift(Gift gift){
        String key = gift.getKey();
        gift.setKey(null);
    ref.child(key).setValue(gift);
}

    public static void updateStatusGift(Gift gift,String status){

        DatabaseReference updateStatus = database.getReference("Gifts").child(gift.getKey()).child("status");
        updateStatus.setValue(status);
    }

}
