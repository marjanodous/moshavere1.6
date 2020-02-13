package com.example.hoquqi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hoquqi.dataBase.Database;

import java.util.ArrayList;

public class list_moshaveran extends AppCompatActivity {
    private RecyclerView recyclerView;
    AdapterMoshaver adapterMoshaver;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Moshaver> moshaverArrayList = new ArrayList<>();
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_moshaveran);

        recyclerView = findViewById(R.id.recycler_view);
        imgBack =findViewById(R.id.img_back);


         moshaverArrayList= callDataBase();
         setupRecyclerAdapter(moshaverArrayList);

         imgBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });

    }

    private void setupRecyclerAdapter(ArrayList<Moshaver> list) {
        adapterMoshaver = new AdapterMoshaver(this, list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapterMoshaver);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    public  ArrayList<Moshaver> callDataBase(){
        ArrayList<Moshaver> moshavers= new ArrayList<>();
        moshavers = Database.getdataMoshaver(getApplicationContext());
        return moshavers;
    }

}
