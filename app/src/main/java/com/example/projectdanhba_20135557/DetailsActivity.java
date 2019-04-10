package com.example.projectdanhba_20135557;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.DanhBa;

import static com.example.projectdanhba_20135557.MainActivity.HOTEN;
import static com.example.projectdanhba_20135557.MainActivity.MACT;
import static com.example.projectdanhba_20135557.MainActivity.NHOM;
import static com.example.projectdanhba_20135557.MainActivity.SODIENTHOAI;
import static com.example.projectdanhba_20135557.MainActivity.THICH;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageViewAvartar,imgLike;
    TextView txtHoTen;
    ImageButton btn_action_call, btn_action_message, btn_action_video_call;
    TextView txtSoDienThoai, txtNhom;
    RecyclerView rvLichSu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_action_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyNutGoi();
            }
        });
        btn_action_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyGuiTinNhan();
            }
        });
        btn_action_video_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuuLyGoiVideo();
            }
        });
    }

    private void xuuLyGoiVideo() {

    }

    private void xulyGuiTinNhan() {
        try {
            String phoneNumber = txtSoDienThoai.getText().toString();
            Uri uri = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_PROCESS_TEXT, uri);
            startActivity(intent);
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void xuLyNutGoi() {
        try {
            Uri uri = Uri.parse("tel:" + txtSoDienThoai.getText().toString());
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            if (intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }
        }catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        btn_action_call = findViewById(R.id.btn_action_call);
        btn_action_message = findViewById(R.id.btn_action_send_message);
        btn_action_video_call = findViewById(R.id.btn_action_video_call);
        imageViewAvartar = findViewById(R.id.imageViewAvartar);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoai);
        txtNhom = findViewById(R.id.txtNhom);
        rvLichSu = findViewById(R.id.rvLichSu);
        imgLike = findViewById(R.id.imgLike);
        Intent intent = getIntent();
        Bundle bundle;
        DanhBa ct = new DanhBa();
        if (intent!=null){
            bundle = intent.getBundleExtra(MainActivity.BUNDLE);
            ct.setMaDanhBa(bundle.getInt(MACT));
            ct.setHoTen(bundle.getString(HOTEN));
            ct.setSoDienThoai(bundle.getString(SODIENTHOAI));
            ct.setNhom(bundle.getString(NHOM));
            ct.setThich(bundle.getBoolean(THICH));
            txtHoTen.setText(ct.getHoTen());
            txtSoDienThoai.setText(ct.getSoDienThoai());
            txtNhom.setText(ct.getNhom());
            if (ct.isThich()){
                imgLike.setVisibility(View.VISIBLE);
            }
            else{
                imgLike.setVisibility(View.INVISIBLE);
            }
        }
        else {
            Toast.makeText(this,"Intent is empty",Toast.LENGTH_LONG).show();
        }

    }
}
