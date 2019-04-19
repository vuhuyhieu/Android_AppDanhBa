package com.example.projectdanhba_20135557;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.DanhBa;

import static com.example.projectdanhba_20135557.MainActivity.BUNDLE;
import static com.example.projectdanhba_20135557.MainActivity.HOTEN;
import static com.example.projectdanhba_20135557.MainActivity.NHOM;
import static com.example.projectdanhba_20135557.MainActivity.SODIENTHOAI;
import static com.example.projectdanhba_20135557.MainActivity.THICH;

public class AddNewContactActivity extends AppCompatActivity {
    ImageButton btn_action_go_back, btn_action_create, btn_action_takePhoto;
    TextView txtTaoLienHe;
    EditText etHoTen, etSoDienThoai, etNhom;
    CheckBox cboUaThich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_action_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veManHinhChinh();
            }
        });
        btn_action_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layAnhTuThietBi();
            }
        });
        btn_action_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoLienHeMoi();
            }
        });
    }

    private void taoLienHeMoi() {
        DanhBa ct = new DanhBa();
        ct.setHoTen(etHoTen.getText().toString());
        ct.setSoDienThoai(etSoDienThoai.getText().toString());
        ct.setNhom(etNhom.getText().toString());
        if (cboUaThich.isChecked()) {
            ct.setThich(true);
        } else {
            ct.setThich(false);
        }
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONETIC_NAME,etHoTen.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, etSoDienThoai.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, etNhom.getText());
        startActivity(intent);
    }


    private void layAnhTuThietBi() {

    }

    private void veManHinhChinh() {
        Intent intent = new Intent(AddNewContactActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        btn_action_create = findViewById(R.id.btn_action_create);
        btn_action_go_back = findViewById(R.id.btn_go_back);
        btn_action_takePhoto = findViewById(R.id.btn_action_take_photo);
        txtTaoLienHe = findViewById(R.id.txtTaoLienHe);
        etHoTen = findViewById(R.id.etHoTen);
        etNhom = findViewById(R.id.etNhom);
        etSoDienThoai = findViewById(R.id.etSoDienThoai);
        cboUaThich = findViewById(R.id.cboUaThich);
    }
}
