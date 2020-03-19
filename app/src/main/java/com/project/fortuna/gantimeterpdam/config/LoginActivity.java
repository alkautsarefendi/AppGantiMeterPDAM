package com.project.fortuna.gantimeterpdam.config;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.project.fortuna.gantimeterpdam.Utils.Cryptograph;
import com.project.fortuna.gantimeterpdam.DAO.TbUser;
import com.project.fortuna.gantimeterpdam.HalamanUtamaActivity;
import com.project.fortuna.gantimeterpdam.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnKonfigurasi;
    private RelativeLayout relativeLayout;
    private EditText txtUser, txtPass;
    Snackbar snackbar;

    protected static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/CSV/";
    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Foto/";
    public static final String ICON_DEFAULT = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Icon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
    }

    private void initComponent() {

        txtUser = (EditText) findViewById(R.id.txtUserLogin);
        txtPass = (EditText) findViewById(R.id.txtUserPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnKonfigurasi = (Button)findViewById(R.id.btnKonfigurasi);
        relativeLayout = (RelativeLayout) findViewById(R.id.login_layout) ;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnKonfigurasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, KonfigurasiActivity.class));
            }
        });
    }

    private void login() {

        TbUser user = new TbUser(getApplicationContext());
        user = user.getUser(txtUser.getText().toString());
        System.out.println(user);
        if (user != null) {
            String decrypt = Cryptograph.getInstance().decrypt(user.getPassword());
            if (decrypt.equals(txtPass.getText().toString())) {

                //TODO Save data user ke session
                SharedPreferences pref = getApplicationContext().getSharedPreferences("GantiMeterApp", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userid", user.userid);
                editor.putString("username", user.username);
                editor.putString("nmPegawai", user.nmPegawai);
                editor.putString("nmJabatan", user.nmJabatan);
                editor.putString("kdWilayah", user.kdWilayah);
                editor.putString("nmWilayah", user.nmWilayah);
                editor.apply();

                Intent menuUtama = new Intent(LoginActivity.this, HalamanUtamaActivity.class);
                startActivity(menuUtama);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            } else {
                snackbar = Snackbar.make(relativeLayout, "Password salah", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            snackbar = Snackbar.make(relativeLayout, "User tidak di temukan", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

        alertDialog.setMessage("Keluar dari Aplikasi?");
        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                LoginActivity.super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }
}
