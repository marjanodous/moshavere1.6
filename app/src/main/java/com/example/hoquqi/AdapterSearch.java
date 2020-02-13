package com.example.hoquqi;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterSearch extends ArrayAdapter {
    public int resourceId;
    public Activity activity;
    public ArrayList<Moshaver> data;
    TextView txtName;
    Moshaver model = new Moshaver();
    public static boolean flag_search=false;

    public AdapterSearch(Activity activity, int resourceId, ArrayList<Moshaver> object) {
        super(activity, resourceId, object);
        this.resourceId = resourceId;
        this.activity = activity;
        this.data = object;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = this.activity.getLayoutInflater().inflate(this.resourceId, null);
        txtName = view.findViewById(R.id.item_search);
        model =data.get(position);
        txtName.setText(model.getmName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String title = data.get(position).getmName();
                Log.i("title isssssssssss",title);
                if(title.contains("قانون اساسی جمهوری اسلامی ایران")|| title.contains("تاریخچه")||
                        title.contains("قضا در قانون اساسی")||title.contains("قوه مجریه")||title.contains("نمایندگان") ){
                    MainActivity.flag_home=true;
                    Log.i("homeeeeeeeeeee",title);
                    activity.finish();
                }
                else if(title.contains("صلاحیت های دادگاه خانواده")|| title.contains("لزوم تشکیل دادگاه خانواده")||
                        title.contains("تشریفات رسیدگی در دادگاه خانواده")||title.contains("حضور قاضی زن در دادگاه خانواده")
                        ||title.contains("شرایط قضات دادگاه خانواده") || title.contains("مراکز مشاوره خانواده")
                        || title.contains("حمایت های قانونی از افراد بی بضاعت")){
                    MainActivity.flag_family=true;
                    Log.i("familyyyyyyyyyyyyyyyyy",title);
                    activity.finish();
                }
                else if(title.contains("مهاجرت")){
                    MainActivity.flag_mohajerat=true;
                    Log.i("mohajeratttttttttttttt",title);
                    activity.finish();
                }
                else if(title.contains("جرائم مالیاتی چیست؟")||title.contains("انواع جرائم مالیاتی")){
                    MainActivity.flag_maliaty=true;
                    Log.i("maliatyyyyyyyyyyyyyyyyy",title);
                    activity.finish();
                }
                else {
                    flag_search=true;
                    Intent intent  = new Intent(activity,ActivityRezomeh.class);
                    intent.putExtra("id_search", data.get(position).getMid());
                    intent.putExtra("title_search", data.get(position).getmName());
                    intent.putExtra("rotbeh_search", data.get(position).getMrotbeh());
                    intent.putExtra("madrak_search", data.get(position).getmMadrak());
                    intent.putExtra("sabegheh_search", data.get(position).getmSabegheh());
                    intent.putExtra("rezomeh_search", data.get(position).getmRezomeh());
                    intent.putExtra("adrres_search", data.get(position).getmAdress());
                    intent.putExtra("phone_search", data.get(position).getmPhone());
                    intent.putExtra("email_search", data.get(position).getmMail());
                    intent.putExtra("pic_search", data.get(position).getmImage());
                    activity.startActivity(intent);
                }

            }
        });
        return view;
    }
}
