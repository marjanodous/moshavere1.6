package com.example.hoquqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class login extends AppCompatActivity {
    userApp user = new userApp();
    TextView txtgotosingup;
    Button btnLoin;
    public String StoredValue;
    EditText edtName,edtPass;
    public boolean U,P;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtgotosingup=findViewById(R.id.txtgotosingup);
        btnLoin=findViewById(R.id.ok_sing_up);
        edtName=findViewById(R.id.username_login);
        edtPass=findViewById(R.id.password_login);


        StoredValue = returnExit();
        testNoFirst();

        if(StoredValue == "exit"){
            txtgotosingup.setEnabled(false);
            txtgotosingup.setText("");
//            Toast.makeText(login.this, "OKKKKKKKKKKK Exit!", Toast.LENGTH_SHORT).show();
        }
        else if(StoredValue == null){
//            Toast.makeText(login.this, "Noooooo Exit!", Toast.LENGTH_SHORT).show();
        }
        txtgotosingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,signup.class));
                login.this.finish();
            }
        });
        btnLoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(login.this);
                user = sharedPreferencesManager.get_shared_preferences();

                if (user.getFirst_time_run() == true) {
//
                    Toast.makeText(login.this, "لطفا ثبت نام کنید!", Toast.LENGTH_SHORT).show();
                } else {
                    U = edt_name_Equle();
                    P = edt_Pass_Equle();
                    if (U && P) {
                        Toast.makeText(login.this, "کاربر سیستم خوش آمدید ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, MainActivity.class));
                        login.this.finish();
                        delExit();
                    } else if(U) {
                        Toast.makeText(login.this, "رمز عبور نابرابر است...", Toast.LENGTH_SHORT).show();
                    }
                    else if(P) {
                        Toast.makeText(login.this, "نام کاربری نابرابر است...", Toast.LENGTH_SHORT).show();
                    }
                    else if(!P&&!U) {
                        Toast.makeText(login.this, "نام کاربری و رمز عبور نابرابر است...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(login.this, "اطلاعات را کامل کنید...", Toast.LENGTH_SHORT).show();

                    }

                }

//                if (user.getFirst_time_run() == false) {
//                    Toast.makeText(login.this, " ثبت نام کردین!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(login.this, MainActivity.class));
//                    login.this.finish();
//
//                } else {
//                    Toast.makeText(login.this, "لطفا ثبت نام کنید!", Toast.LENGTH_SHORT).show();
//                }
            }
        });


    }
    //======================change font===============================
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void testNoFirst(){
        final SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(login.this);
        user = sharedPreferencesManager.get_shared_preferences();

        if (user.getFirst_time_run() == false) {
            startActivity(new Intent(login.this, MainActivity.class));
            login.this.finish();
        }
        if ( StoredValue =="exit") {
//            Toast.makeText(login.this, " exit >> false", Toast.LENGTH_LONG).show();
            user.setFirst_time_run(false);
            sharedPreferencesManager.set_false_first_time(user);
            delExit();
        }
    }
    public String returnExit(){
        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("Exit", MODE_PRIVATE);
        String val = myPrefs.getString("exit", null);
        return val;
    }
    public void delExit() {
        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("Exit", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        edit.remove("exit");
        edit.commit();
    }
    public String getUname() {
        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("User", MODE_PRIVATE);
        String storedValue = myPrefs.getString("uname", null);
        return storedValue;
    }
    public String getUPass() {
        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("User", MODE_PRIVATE);
        String storedValue = myPrefs.getString("upass", null);
        return storedValue;
    }

    public Boolean edt_name_Equle() {
        boolean b = false;
        String store = getUname();
        if (store == null) {
//            Toast.makeText(Logins.this, "name nulll", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(Logins.this, "name not null", Toast.LENGTH_SHORT).show();
            String uName = edtName.getText().toString().trim();
            b = store.equals(uName);
            if (b) {
//                Toast.makeText(Logins.this, ":)))))))))yehhhh equal", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(Logins.this, ":(((((((((nooooo equal", Toast.LENGTH_SHORT).show();
            }
        }
        return b;
    }
    public Boolean edt_Pass_Equle() {
        boolean b = false;
        String store = getUPass();
        if (store == null) {
//            Toast.makeText(Logins.this, "name nulll", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(Logins.this, "name not null", Toast.LENGTH_SHORT).show();
            String uPass = edtPass.getText().toString().trim();
            b = store.equals(uPass);
            if (b) {
//                Toast.makeText(Logins.this, ":)))))))))yehhhh equal", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(Logins.this, ":(((((((((nooooo equal", Toast.LENGTH_SHORT).show();
            }
        }
        return b;
    }

}
