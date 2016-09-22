package gs.mygift.description;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gs.mygift.R;
import gs.mygift.mainActivity.MainActivity;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);





        tvName.setText(MainActivity.choiceGift.getName());
        tvDescription.setText(MainActivity.choiceGift.getDescription());
        tvPrice.setText("Price: "+MainActivity.choiceGift.getPrice()+"");
        if (MainActivity.choiceGift.getUri()!=null){
            imageView.setBackgroundResource(0);
            Picasso.with(this).load(MainActivity.choiceGift.getUri()).into(imageView);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
