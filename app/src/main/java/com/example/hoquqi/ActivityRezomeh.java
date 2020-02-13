package com.example.hoquqi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoquqi.dataBase.Database;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ActivityRezomeh extends AppCompatActivity {

    public String IMG;
    TextView txtname, txtpass, txtmail, txtphone, txtsabegheh, txtmadrak, txtrotbeh, txtadress, txtrezomeh;
    ImageView imageView, imgBack;
    public Moshaver list = new Moshaver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezume);

        imageView = findViewById(R.id.img_rezume);
        txtname = findViewById(R.id.name_moshaver_zezume);
        txtrotbeh = findViewById(R.id.paye_hoquqi_moshaver_list);
        txtmadrak = findViewById(R.id.madrak_hoquqi_moshaver);
        txtsabegheh = findViewById(R.id.txt_sabeqe_rezume);
        txtrezomeh = findViewById(R.id.txt_rezume_rezume);
        txtadress = findViewById(R.id.txt_address_rezume);
        txtphone = findViewById(R.id.txt_phone_rezume);
        txtmail = findViewById(R.id.txt_email_rezume);
        imgBack = findViewById(R.id.img_back);


        if (AdapterMoshaver.flag_moshaver == true) {
            Bundle bundle = getIntent().getExtras();
            IMG = bundle.getString("pic");
            //decode base64 string to image
            byte[] imageBytes = Base64.decode(IMG, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(decodedImage);
            txtname.setText(bundle.getString("name"));
            txtrotbeh.setText(bundle.getString("rotbeh"));
            txtmadrak.setText(bundle.getString("madrak"));
            txtsabegheh.setText(bundle.getString("sabegheh"));
            txtrezomeh.setText(bundle.getString("rezomeh"));
            txtadress.setText(bundle.getString("adrres"));
            txtphone.setText(bundle.getString("phone"));
            txtmail.setText(bundle.getString("email"));

        } else if (AdapterSearch.flag_search == true) {
            Bundle bundle2 = getIntent().getExtras();
            String val=bundle2.getString("title_search");
            txtname.setText(bundle2.getString("title_search"));
            list=Database.getdataMoshaverSearch(getApplicationContext(),val);
            txtrotbeh.setText(list.getMrotbeh());
            txtmadrak.setText(list.getmMadrak());
            txtsabegheh.setText(list.getmSabegheh());
            txtrezomeh.setText(list.getmRezomeh());
            txtadress.setText(list.getmAdress());
            txtphone.setText(list.getmPhone());
            txtmail.setText(list.getmMail());
            IMG = list.getmImage();
            //decode base64 string to image
            byte[] imageBytes = Base64.decode(IMG, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(decodedImage);

        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                AdapterMoshaver.flag_moshaver = false;
                AdapterSearch.flag_search = false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AdapterMoshaver.flag_moshaver = false;
        AdapterSearch.flag_search = false;
    }
}
