package com.example.hoquqi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMoshaver extends RecyclerView.Adapter<AdapterMoshaver.MyViewHolder> {

    private Context context;
    private ArrayList<Moshaver> arrayList;
    public static Boolean flag_moshaver=false;

    public AdapterMoshaver(Context context, ArrayList<Moshaver> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_moshaveran, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.txtname.setText(arrayList.get(position).getmName());
        holder.txtrotbeh.setText(arrayList.get(position).getMrotbeh());
//decode base64 string to image
        byte[] imageBytes = Base64.decode(arrayList.get(position).getmImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imgmoshaver.setImageBitmap(decodedImage);
//        holder.imgmoshaver.setImageResource(arrayList.get(position).getmImage());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_moshaver=true;
                Intent intent = new Intent(context, ActivityRezomeh.class);
                intent.putExtra("id", arrayList.get(position).getMid());
                intent.putExtra("name", arrayList.get(position).getmName());
                intent.putExtra("rotbeh", arrayList.get(position).getMrotbeh());
                intent.putExtra("madrak", arrayList.get(position).getmMadrak());
                intent.putExtra("sabegheh", arrayList.get(position).getmSabegheh());
                intent.putExtra("rezomeh", arrayList.get(position).getmRezomeh());
                intent.putExtra("adrres", arrayList.get(position).getmAdress());
                intent.putExtra("phone", arrayList.get(position).getmPhone());
                intent.putExtra("email", arrayList.get(position).getmMail());
                intent.putExtra("pic", arrayList.get(position).getmImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtrotbeh;
        ImageView imgmoshaver;
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.name_moshaver);
            imgmoshaver = itemView.findViewById(R.id.img_moshaver);
            txtrotbeh = itemView.findViewById(R.id.paye_hoquqi_moshaver_list);
            layout = itemView.findViewById(R.id.item_layout);
        }
    }
}
