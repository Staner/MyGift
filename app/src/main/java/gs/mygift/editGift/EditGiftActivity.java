package gs.mygift.editGift;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import gs.mygift.R;
import gs.mygift.firebase.database.FB;
import gs.mygift.mainActivity.MainActivity;
import gs.mygift.mainActivity.fragments.FragmentMyGifts;
import gs.mygift.myClass.Gift;


public class EditGiftActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    EditText tvName,tvDescription,tvPrice;
    Button btnSave;
    ImageButton imageButton;

    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gift);


        tvName = (EditText) findViewById(R.id.tvName);
        tvDescription = (EditText) findViewById(R.id.tvDescription);
        tvPrice = (EditText) findViewById(R.id.tvPrice);
        btnSave = (Button) findViewById(R.id.btnSave);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        if (MainActivity.choiceGift.getKey()!=null) {
    tvName.setText(MainActivity.choiceGift.getName());
    tvDescription.setText(MainActivity.choiceGift.getDescription());
    tvPrice.setText(MainActivity.choiceGift.getPrice() + "");

}else {
    btnSave.setText("Create gift");
}

imageButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        takePic();
    }
});


    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
if (MainActivity.choiceGift.getKey()!=null){
    editGift();
}else {
    newGift();
}

            FragmentMyGifts.GiftRecyclerViewAdapter.notifyDataSetChanged();
            onBackPressed();


        }
    });



    }

    private void takePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    private void newGift() {

        Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();

        bitmap = getResizedBitmap(bitmap,80,80);

        Gift gift = new Gift(tvName.getText().toString(),
                Integer.valueOf(tvPrice.getText().toString()),
                tvDescription.getText().toString(),MainActivity.currentUser.getUid(),bitmap,selectedImage.toString(),"want");

        FB.saveGift(gift,this);
    }





    private void editGift() {

                MainActivity.choiceGift.setName(tvName.getText().toString());
                MainActivity.choiceGift.setPrice(Integer.valueOf(tvPrice.getText().toString()));
                MainActivity.choiceGift.setDescription(tvDescription.getText().toString());

            FB.updateGift(MainActivity.choiceGift);


    }

    @Override
    public void onBackPressed() {
        //finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();

            Picasso.with(this).load(selectedImage).noPlaceholder().centerCrop().fit()
                    .into((imageButton));
        }

    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }


}
