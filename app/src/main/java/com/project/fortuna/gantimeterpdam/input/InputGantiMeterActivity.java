package com.project.fortuna.gantimeterpdam.input;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.fortuna.gantimeterpdam.DAO.TbBlok;
import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.Utils.GPSTracker;
import com.project.fortuna.gantimeterpdam.R;
import com.project.fortuna.gantimeterpdam.Utils.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InputGantiMeterActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Ganti Meter/";
    public static final String PHOTO_PATH_LAMA = Environment.getExternalStorageDirectory().toString() + "/Ganti Meter/Foto/Foto Lama/";
    public static final String PHOTO_PATH_PASANG = Environment.getExternalStorageDirectory().toString() + "/Ganti Meter/Foto/Foto Pasang/";

    protected static final String PHOTO_TAKEN = "photo_taken";
    private static final int SCANNER_QRCODE = 1;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static final int PHOTO_LAMA = 2;
    private static final int PHOTO_PASANG = 3;

    public static final int AVS_MOST_ACCURATE = 100;
    protected static final String MY_ACTION = null;
    private SharedPreferences pref;
    private String sPathFoto;

    private EditText txtKDPel, txtPelanggan, txtAlamat, txtNoMeterAirLama, txtMerkMeterBaru,
            txtKodeStandMeterBaru, txtAngkaMeterBaru, txtUserId;
    private Button btnQRCode, btnCari, btnViewMap, btnFotoLama, btnFotoPasang, btnInput, btnReset, btnInputCancel;
    private ImageView imageViewMeterLama, imageViewMeterPasang;
    private Spinner spinStatus, spinDiameter;
    protected String _path;
    protected boolean _taken, isImageFitToScreen;
    protected String sFoto="";
    private String fileName;
    private String fileName1;
    protected Uri outputFileUri = null;
    protected Uri outputFileUri1 = null;
    Uri output = null;
    Uri output1 = null;

    String tglPeriode = new SimpleDateFormat("yyyyMMdd").format(new Date());

    InputStream is=null;
    String result=null;
    String line=null;
    int code;

    // GPSTracker class
    GPSTracker gps;
    private double newlat = 0.0;
    private double newlong = 0.0;
    private String latitude = "";
    private String longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ganti_meter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Input Ganti Meter");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pref = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);
        getGPSFoto();

        doInitComponent();
        doInitData();
        doFromReview();
    }

    private void doInitComponent() {
        // TODO Auto-generated method stub
        txtKDPel = (EditText) findViewById(R.id.txtKDPel);
        txtPelanggan = (EditText) findViewById(R.id.txtPelanggan);
        txtAlamat = (EditText) findViewById(R.id.txtAlamat);
        txtNoMeterAirLama = (EditText) findViewById(R.id.txtNoMeterAirLama);
        txtMerkMeterBaru = (EditText) findViewById(R.id.txtMerkMeterBaru);
        txtKodeStandMeterBaru = (EditText) findViewById(R.id.txtKodeStandMeterBaru);
        txtAngkaMeterBaru = (EditText) findViewById(R.id.txtAngkaMeterBaru);
        txtUserId = (EditText) findViewById(R.id.txtUserId);

        btnQRCode = (Button) findViewById(R.id.btnQRCode);
        btnQRCode.setOnClickListener(new ButtonQRCodeClickHandler());
        btnCari = (Button) findViewById(R.id.btnCari);
        btnCari.setOnClickListener(new ButtonCariClickHandler());
        btnViewMap = (Button) findViewById(R.id.btnMap);
        btnViewMap.setOnClickListener(new ButtonMapClickHandler());
        btnInput = (Button) findViewById(R.id.btnInput);
        btnInput.setOnClickListener(new ButtonInputClickHandler());
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new ButtonResetClickHandler());
        btnInputCancel = (Button) findViewById(R.id.btnInputCancel);
        btnInputCancel.setOnClickListener(new ButtonCancelClickHandler());

        btnFotoLama = (Button) findViewById(R.id.btnFotoLama);
        btnFotoLama.setOnClickListener(new ButtonFotoLamaClickHandler());
        btnFotoPasang = (Button) findViewById(R.id.btnFotoPasang);
        btnFotoPasang.setOnClickListener(new ButtonFotoPasangClickHandler());

        imageViewMeterLama = (ImageView) findViewById(R.id.imageViewMeterLama);
        imageViewMeterPasang = (ImageView) findViewById(R.id.imageViewMeterPasang);

        spinDiameter = (Spinner) findViewById(R.id.spinDiameter);
        spinDiameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        spinStatus = (Spinner) findViewById(R.id.spinStatus);
        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });



        // init
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.v("ISA", "ERROR: Creation of directory " + DATA_PATH + " on sdcard failed");
                return;
            } else {
                Log.v("ISA", "Created directory " + DATA_PATH + " on sdcard");
            }
        }

        // init2
        File dir2 = new File(PHOTO_PATH_LAMA);
        if (!dir2.exists()) {
            if (!dir2.mkdirs()) {
                Log.v("ISA", "ERROR: Creation of directory " + PHOTO_PATH_LAMA + " on sdcard failed");
                return;
            } else {
                Log.v("ISA", "Created directory " + PHOTO_PATH_LAMA + " on sdcard");
            }
        }

        // init3
        File dir3 = new File(PHOTO_PATH_PASANG);
        if (!dir3.exists()) {
            if (!dir3.mkdirs()) {
                Log.v("ISA", "ERROR: Creation of directory " + PHOTO_PATH_PASANG + " on sdcard failed");
                return;
            } else {
                Log.v("ISA", "Created directory " + PHOTO_PATH_PASANG + " on sdcard");
            }
        }
    }

    @SuppressWarnings("unused")
    private void doInitData() {

        String kdWilayah = pref.getString("kdWilayah", "");
        List<TbBlok> listJalan = new TbBlok(InputGantiMeterActivity.this).retrieveByWilayah(kdWilayah);
        txtKDPel.setFocusable(true);

    }

    private void doFromReview() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string = extras.getString("key");
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA " + string);
            txtKDPel.setText(string);
            getData(string);
        }
    }

    public class ButtonQRCodeClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            try {
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, SCANNER_QRCODE);

            } catch (ActivityNotFoundException anfe) {
                showDialog(InputGantiMeterActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
            }
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }



    public class ButtonCariClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            getData(txtKDPel.getText().toString());
        }
    }


    private void getData(String sKDPelanggan) {

        try {
            String userid = pref.getString("userid", "");
            TbGantiMeter ds = new TbGantiMeter(this);
            ds = ds.retrieveForMeter(userid, "'" + sKDPelanggan);

            if (ds == null) {
                Util.showmsg(this, "Peringatan :", getString(R.string.msg_no_data));
                txtKDPel.setEnabled(true);
                txtKDPel.setSelection(txtKDPel.getText().length());

            } else {

                txtPelanggan.setText(ds.getNAMA_PELANGGAN());
                txtAlamat.setText(ds.getALAMAT_PELANGGAN());
                txtUserId.setText(ds.getUSER_ID());
                txtNoMeterAirLama.setText(ds.getKD_STAND_METER_LAMA());
                txtMerkMeterBaru.setText(ds.getMERK_METER_BARU());
                txtKodeStandMeterBaru.setText(ds.getKD_STAND_METER_BARU());

                latitude = ds.GPS_LAT_PELANGGAN;
                longitude = ds.GPS_LONG_PELANGGAN;

                Util.popup(this, "Pelanggan : " + ds.getKODE_PELANGGAN());
                txtKDPel.setEnabled(false);

            }
        } catch (Exception e) {
            Log.e("getData", "Error: " + e.toString());
        }

    }

    public class ButtonMapClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub

            Intent maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?f=d&daddr=" + latitude + "," + longitude));
            startActivity(maps);

        }

    }

    public class ButtonInputClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String StatusBaca = spinStatus.getSelectedItem().toString();
            String tokenStatusBaca = StatusBaca.substring(4);
            String diameter = spinDiameter.getSelectedItem().toString();

            if (txtPelanggan.getText().length() == 0){
                Util.showmsg(InputGantiMeterActivity.this, "Peringatan :", "Data Pelanggan Belum Ada");
            }else if(txtAngkaMeterBaru.getText().toString() == null || txtAngkaMeterBaru.getText().toString().trim().equals("")){
                Util.showmsg(InputGantiMeterActivity.this, "Peringatan :", "Nomor Stand Angkat Tidak Koleh Kosong");
            }else if(outputFileUri == null){
                Util.showmsg(InputGantiMeterActivity.this, "Peringatan :", "Foto Lama Tidak Boleh Kosong");
            }else if(outputFileUri1 == null){
                Util.showmsg(InputGantiMeterActivity.this, "Peringatan :", "Foto Pasang Tidak Boleh Kosong");
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(InputGantiMeterActivity.this);
                builder.setTitle(getString(R.string.action_input))
                        .setMessage("KD Pelanggan = "
                                + txtKDPel.getText().toString() + "\n"
                                + "Pelanggan = " + txtPelanggan.getText().toString()  + "\n"
                                + "Alamat = " + txtAlamat.getText().toString() + "\n"
                                + "No Meter Air Lama = " + txtNoMeterAirLama.getText().toString() + "\n"
                                + "Merk Meter Air = " + txtMerkMeterBaru.getText().toString() + "\n"
                                + "New Lat = " + newlat + "\n"
                                + "New Long = " + newlong + "\n"
                                + "Nomor Meter Air Baru = " + txtKodeStandMeterBaru.getText().toString() + "\n"
                                + "Pipa Diameter = " + diameter + "\n"
                                + "Stand Angkat = " + txtAngkaMeterBaru.getText().toString() + "\n"
                                + "Tgl Pasang = " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\n"
                                + "Status Baca = " + tokenStatusBaca)
                        .setCancelable(false)
                        .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saveData();
                            }})
                        .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setCancelable(false);
                alert.show();
            }

        }

        protected void saveData() {

            String mesgErr = "";

            if (mesgErr.equals("")) {


                TbGantiMeter gantimeter = new TbGantiMeter(getApplicationContext());
                gantimeter.ALAMAT_PELANGGAN = txtAlamat.getText().toString();
                gantimeter.DIAMETER = spinDiameter.getSelectedItem().toString();
                gantimeter.FOTO_LAMA = fileName;
                gantimeter.FOTO_PASANG = fileName1;
                gantimeter.GPS_LAT_PELANGGAN = "" + latitude;
                gantimeter.GPS_LONG_PELANGGAN = "" + longitude;
                gantimeter.GPS_LAT_PASANG = "" + newlat;
                gantimeter.GPS_LONG_PASANG = "" + newlong;
                gantimeter.KD_STAND_METER_LAMA = txtNoMeterAirLama.getText().toString();
                gantimeter.MERK_METER_BARU = txtMerkMeterBaru.getText().toString();
                gantimeter.KD_STAND_METER_BARU = txtKodeStandMeterBaru.getText().toString().replace(" ", "");
                gantimeter.ANGKA_METER_BARU = txtAngkaMeterBaru.getText().toString();
                gantimeter.KODE_PELANGGAN = "'" + txtKDPel.getText().toString();
                gantimeter.S_PASANG = spinStatus.getSelectedItem().toString().split(" = ")[0].trim();
                gantimeter.USER_ID = pref.getString("userid", "");
                gantimeter.TGL_PASANG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                gantimeter.update();
                AlertDialog.Builder builder = new AlertDialog.Builder(InputGantiMeterActivity.this);
                builder.setTitle(getString(R.string.action_input))
                        .setMessage("Input Data Sukses")
                        .setCancelable(false)
                        .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                clearText();
                                txtKDPel.setEnabled(true);
                                txtKDPel.requestFocus();
//						surveyScrollView.fullScroll(ScrollView.FOCUS_UP);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setCancelable(false);
                alert.show();
            }
        }

    }

    public class ButtonFotoLamaClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (txtPelanggan.getText().toString().length() == 0)
                Util.showmsg(InputGantiMeterActivity.this, "Peringatan :", getString(R.string.msg_scan_pelanggan));
            else {
                Log.v("PHOTO_LAMA", "Starting Camera app");
                startCameraActivityLama(v);
            }
        }
    }

    /* Start Camera */
    public void startCameraActivityLama(View v) {
        fileName = composeFileFoto(txtKDPel.getText().toString());
        sPathFoto = PHOTO_PATH_LAMA + fileName;

        File file = new File(sPathFoto);
        output = Uri.fromFile(file);
        //	outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, PHOTO_LAMA);
    }


    private String composeFileFoto(String sNoReg) {
        return "GM_LAMA_" + tglPeriode + "_" + sNoReg.replace("'", "") + ".jpg";
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("ON ACTIVITY RESULT", "resultCode: " + resultCode + " On Request: " + requestCode);

        if (resultCode != RESULT_OK) {
            Util.showmsg(this, "Peringatan :", "Pengambilan Foto Gagal");
        } else {

            switch (requestCode) {
                case PHOTO_LAMA:
                    outputFileUri = output;

                    onPhotoTaken();
                    viewFoto();
                    //	getGPSFoto();
                    break;
                case PHOTO_PASANG:
                    outputFileUri1 = output1;

                    onPhotoTaken2();
                    viewFoto1();

                    break;
                case SCANNER_QRCODE:
                    String contents = data.getStringExtra("SCAN_RESULT");
                    //	String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                    txtKDPel.setText(contents);
                    getData(contents);
                    break;
                default:
                    break;
            }
        }
    }



    private void onPhotoTaken() {

        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        options.inJustDecodeBounds = true;

        //	Bitmap bitmap = BitmapFactory.decodeFile(sPathFoto, options);
        Bitmap bitmap = ShrinkBitmap(sPathFoto, 1280, 720);
        imageViewMeterLama.setImageBitmap(bitmap);

    }

    private void onPhotoTaken2() {

        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        options.inJustDecodeBounds = true;

        //	Bitmap bitmap = BitmapFactory.decodeFile(sPathFoto, options);
        Bitmap bitmap2 = ShrinkBitmap(sPathFoto, 1280, 720);
        imageViewMeterPasang.setImageBitmap(bitmap2);

    }



    Bitmap ShrinkBitmap(String file, int width, int height){

        Bitmap scaledBitmap = null;

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int actualHeight = bmpFactoryOptions.outHeight;
        int actualWidth = bmpFactoryOptions.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        bmpFactoryOptions.inSampleSize = calculateInSampleSize(bmpFactoryOptions, actualWidth, actualHeight);
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmpFactoryOptions.inDither = false;
        bmpFactoryOptions.inPurgeable = true;
        bmpFactoryOptions.inInputShareable = true;
        bmpFactoryOptions.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) bmpFactoryOptions.outWidth;
        float ratioY = actualHeight / (float) bmpFactoryOptions.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateTime = sdf.format(Calendar.getInstance().getTime());
        String textPhoto = getString(R.string.cr_pdam_name);
        String idPel = txtKDPel.getText().toString();
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);

        Canvas canvas = new Canvas(scaledBitmap);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setAntiAlias(true);
        tPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setTypeface(typeface);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        float heightNew = tPaint.measureText("yY");
        canvas.drawText(dateTime + " " + idPel, (middleX - bmp.getWidth() / 2) + 15, canvas.getHeight() - 10, tPaint);
        canvas.drawText(textPhoto, 0, 30, tPaint);
        /*canvas.drawText(idPel, 1,31, tPaint);*/

        ExifInterface exif;
        try {
            exif = new ExifInterface(file);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    public int calculateInSampleSize(BitmapFactory.Options bmpFactoryOptions, int reqWidth, int reqHeight) {
        final int height = bmpFactoryOptions.outHeight;
        final int width = bmpFactoryOptions.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            System.out.println("Simple Size " + inSampleSize);
        }
        final float totalPixels = width * height;
        System.out.println("Total Pixels " +totalPixels);
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        System.out.println("Total req pix " +totalReqPixelsCap);

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    /* View Foto */
    public void viewFoto() {
        imageViewMeterLama.setImageURI(outputFileUri);
    }


    /* View Foto */
    public void viewFoto1() {
        imageViewMeterPasang.setImageURI(outputFileUri1);
    }


    public class ButtonFotoPasangClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (txtPelanggan.getText().toString().length() == 0)
                Util.showmsg(InputGantiMeterActivity.this, "Peringatan :", getString(R.string.msg_scan_pelanggan));
            else {
                Log.v("PHOTO_PASANG", "Starting Camera app");
                startCameraActivityPasang(v);
            }
        }
    }

    /* Start Camera */
    public void startCameraActivityPasang(View v) {
        fileName1 = composeFileFoto1(txtKDPel.getText().toString());
        sPathFoto = PHOTO_PATH_PASANG + fileName1;

        File file = new File(sPathFoto);
        output1 = Uri.fromFile(file);
        //	outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output1);
        startActivityForResult(intent, PHOTO_PASANG);
    }


    private String composeFileFoto1(String sNoReg) {
        return "GM_PASANG_" + tglPeriode + "_" + sNoReg.replace("'", "") + ".jpg";
    }

    public class ButtonResetClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            clearText();
            txtKDPel.setEnabled(true);
            txtKDPel.requestFocus();
        }
    }


    private void clearText() {
        // TODO Auto-generated method stub
        txtKDPel.setText("");
        txtPelanggan.setText("");
        txtAlamat.setText("");
        txtNoMeterAirLama.setText("");
        txtMerkMeterBaru.setText("");
        txtKodeStandMeterBaru.setText("");
        spinDiameter.setSelection(0);
        txtAngkaMeterBaru.setText("");
        outputFileUri=null;
        imageViewMeterLama.setImageResource(R.mipmap.tidak_ada_foto_meter);
        imageViewMeterPasang.setImageResource(R.mipmap.tidak_ada_foto_meter);
    }

    public class ButtonCancelClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            cancelDialog();

        }

    }

    private void cancelDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(InputGantiMeterActivity.this);
        builder.setTitle("Peringatan : ");
        builder.setMessage("Batal Melakukan Input Data ? ").setCancelable(false);
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {

                InputGantiMeterActivity.this.finish();

            }
        });

        builder.setNegativeButton("Tidak", null).show();

    }

    public void getGPSFoto()
    {
        // create class object
        gps = new GPSTracker(InputGantiMeterActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){
            newlat = gps.getLatitude();
            newlong = gps.getLongitude();

            Toast.makeText(InputGantiMeterActivity.this, "Your Location is - \nLat: " + newlat + "\nLong: " + newlong, Toast.LENGTH_LONG).show();
        }else{
            gps.showSettingsAlert();
        }
    }
}
