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
    ImageButton btn_action_dial;
    TextView txtSoDienThoai, txtNhom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_action_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyNutGoi();
            }
        });
    }

    private void xuLyNutGoi() {
        try {
            Uri uri = Uri.parse("tel:" + txtSoDienThoai.getText().toString());
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            if (intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }
        }catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        btn_action_dial = findViewById(R.id.btn_action_call);
        imageViewAvartar = findViewById(R.id.imageViewAvartar);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoai);
        txtNhom = findViewById(R.id.txtNhom);
        imgLike = findViewById(R.id.imgLike);
        Intent intent = getIntent();
        Bundle bundle;
        DanhBa ct = new DanhBa();
        if (intent!=null){
            bundle = intent.getBundleExtra(MainActivity.BUNDLE);
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
        imageViewAvartar.setImageResource(R.drawable.ic_avartar_96dp);
    }
}
