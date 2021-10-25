package com.example.httpinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yh.network.tools.DiagnoseProxy;

import fairy.easy.httpmodel.util.HttpLog;

public class MainActivity extends AppCompatActivity {
    public static final String HTTP_ADDRESS = "http";
    public static final String HTTP_RB = "rb";

    private EditText etInput;
    private CheckBox radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.main_net));
        //获取手机强度使用
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        etInput = findViewById(R.id.main_activity_et_input);
        radioButton = findViewById(R.id.main_activity_start_rb);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiagnoseActivity.class);
                intent.putExtra(HTTP_ADDRESS, etInput.getText().toString());
                intent.putExtra(HTTP_RB, radioButton.isChecked());
                startActivity(intent);
            }
        });

        findViewById(R.id.main_activity_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isACCESS_NETWORK_STATE = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED;
                boolean isREAD_PHONE_STATE = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED;
                if (isACCESS_NETWORK_STATE && isREAD_PHONE_STATE) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                                    Manifest.permission.READ_PHONE_STATE}, 1);
                    return;
                } else if (isACCESS_NETWORK_STATE) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                    return;
                } else if (isREAD_PHONE_STATE) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        goToSettings();
                    }

                    return;
                }
                if (TextUtils.isEmpty(etInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Input Address is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(HTTP_ADDRESS, etInput.getText().toString());
                intent.putExtra(HTTP_RB, radioButton.isChecked());
                startActivity(intent);
            }
        });
    }

    private void goToSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, 2);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                HttpLog.i("imei", "permission is granted after requested！");
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                HttpLog.i("imei", "permission is not granted after requested！");
                //这里表示申请权限后被用户拒绝了

            } else {
                HttpLog.i("imei", "permission is not granted after requested！");
            }
        }
    }

}
