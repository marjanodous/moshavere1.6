package com.example.hoquqi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hoquqi.dataBase.Database;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class singup_moshavere extends AppCompatActivity {

    ImageView imageView,imgBack;
    EditText edtUName, edtUPass, edtEmail, edtPhone, edtSabegheh, edtMadrak, edtRotbeh, edtAdress, edtRezomeh;
    Button btnsave;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String encodedImageData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_moshavere);

        imgBack = findViewById(R.id.img_back);
        edtUName = findViewById(R.id.edt_user_name_sing_up_moshaver);
        edtUPass = findViewById(R.id.edt_password_singup_moshaver);
        edtEmail = findViewById(R.id.edt_email_singup_moshaver);
        edtPhone = findViewById(R.id.edt_phone_singup);
        edtSabegheh = findViewById(R.id.edt_sabeqe_singup);
        edtMadrak = findViewById(R.id.edt_madrak_singup);
        edtRotbeh = findViewById(R.id.edt_rotbe_singup);
        edtAdress = findViewById(R.id.edt_address_singup);
        edtRezomeh = findViewById(R.id.edt_rezume_singup);
        btnsave = findViewById(R.id.ok_sing_up_moshaver);
        imageView = findViewById(R.id.add_img_moshaver);
        requestMultiplePermissions();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] imageByteEnd = new byte[0];

                String mName = edtUName.getText().toString().trim();
                String mpass = edtUPass.getText().toString().trim();
                String mmail = edtEmail.getText().toString().trim();
                String mphone = edtPhone.getText().toString().trim();
                String msabegheh = edtSabegheh.getText().toString().trim();
                String mmadrak = edtMadrak.getText().toString().trim();
                String mrotbeh = edtRotbeh.getText().toString().trim();
                String madress = edtAdress.getText().toString().trim();
                String mrezomeh = edtRezomeh.getText().toString().trim();
                if (!mName.equals("") && !mpass.equals("") && !mmail.equals("")
                        && !mphone.equals("") && !msabegheh.equals("") && !mmadrak.equals("")
                        && !mrotbeh.equals("") && !madress.equals("")
                        && !mrezomeh.equals("")) {

                    try {

                        imageView.buildDrawingCache();
                        Bitmap bmap = imageView.getDrawingCache();
                        encodedImageData = getEncoded64ImageStringFromBitmap(bmap);
//                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//                        Bitmap bitmap = drawable.getBitmap();
//                        byte[] imageByte = getBytes(bitmap);
//                        imageByteEnd =imageByte;
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    Database.addMoshaver(mName,mpass ,mmail ,mphone,msabegheh,mmadrak,mrotbeh
                            ,madress,mrezomeh, encodedImageData,getApplicationContext());
                    if (Database.flagSabt == true) {
                        Toast.makeText(getApplicationContext(), "ثبت اطلاعات", Toast.LENGTH_SHORT).show();
                        singup_moshavere.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "خطا در عملیات!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(singup_moshavere.this, "لطفا اطلاعات را کامل کنید!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPictureDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //////////addNew
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                    String path = saveImage(bMapScaled);
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageView.setImageBitmap(bMapScaled);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Bitmap bMapScaled = Bitmap.createScaledBitmap(thumbnail, 100, 100, true);
            imageView.setImageBitmap(bMapScaled);
            saveImage(bMapScaled);
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("انتخاب عملیات");
        String[] pictureDialogItems = {
                "انتخاب عکس از گالری",
                "گرفتن عکس با دوربین"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    // convert from bitmap to byte array
//    public byte[] getBytes(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        return stream.toByteArray();
//    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }
}

