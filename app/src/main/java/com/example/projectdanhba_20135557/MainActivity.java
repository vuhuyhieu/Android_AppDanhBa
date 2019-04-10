package com.example.projectdanhba_20135557;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.adapter.DanhBaAdapter;
import com.example.adapter.DanhBaAdapterRecycleView;
import com.example.model.DanhBa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<DanhBa> arrDanhBa;
    LinearLayoutManager layoutManager;
    RecyclerView rvDAnhBa;
    DanhBaAdapterRecycleView adapterDanhBa;
    FloatingActionButton btn_action_add;
    public static final String MACT = "mact";
    public static final String HOTEN = "hoten";
    public static final String SODIENTHOAI = "sodienthoai";
    public static final String NHOM = "nhom";
    public static final String THICH = "thich";
    public static final String BUNDLE = "bundle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        adapterDanhBa.setOnItemClickListener(new DanhBaAdapterRecycleView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                xuLyMoManHinhDetalis(arrDanhBa.get(position));
            }
        });

    }

    private void xuLyMoManHinhDetalis(DanhBa ct) {
        try {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(MACT, ct.getMaDanhBa());
            bundle.putString(HOTEN, ct.getHoTen());
            bundle.putString(NHOM, ct.getNhom());
            bundle.putString(SODIENTHOAI, ct.getSoDienThoai());
            bundle.putBoolean(THICH, ct.isThich());
            intent.putExtra(BUNDLE, bundle);
            startActivity(intent);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addControls() {
        arrDanhBa = new ArrayList<DanhBa>();
        //arrDanhBa = getContactList();
        arrDanhBa.add(new DanhBa(1, "Hiếu", "0982960442", "Di động", true));
        arrDanhBa.add(new DanhBa(2, "A Tuấn", "0976750594", "Di động", false));
        arrDanhBa.add(new DanhBa(3, "Honey", "0982887803", "Di động", true));
        arrDanhBa.add(new DanhBa(4, "Mẹ", "0989884858", "Di động", true));
        arrDanhBa.add(new DanhBa(5, "Bố", "0912011101", "Di động", false));
        arrDanhBa.add(new DanhBa(6, "A Thành", "0985951786", "Di động", false));
        arrDanhBa.add(new DanhBa(7, "Huy", "0388846597", "Di động", true));
        arrDanhBa.add(new DanhBa(8, "Hùng Sinh", "036715367", "Di động", false));
        arrDanhBa.add(new DanhBa(1, "Hiếu", "0982960442", "Di động", true));
        arrDanhBa.add(new DanhBa(2, "A Tuấn", "0976750594", "Di động", false));
        arrDanhBa.add(new DanhBa(3, "Honey", "0982887803", "Di động", true));
        arrDanhBa.add(new DanhBa(4, "Mẹ", "0989884858", "Di động", true));
        arrDanhBa.add(new DanhBa(5, "Bố", "0912011101", "Di động", false));
        arrDanhBa.add(new DanhBa(6, "A Thành", "0985951786", "Di động", false));
        arrDanhBa.add(new DanhBa(7, "Huy", "0388846597", "Di động", true));
        arrDanhBa.add(new DanhBa(8, "Hùng Sinh", "036715367", "Di động", false));
        rvDAnhBa = findViewById(R.id.rvDanhBa);
        rvDAnhBa.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvDAnhBa.setLayoutManager(layoutManager);
        adapterDanhBa = new DanhBaAdapterRecycleView(arrDanhBa);
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
//        rvDAnhBa.addItemDecoration(itemDecoration);
        rvDAnhBa.setAdapter(adapterDanhBa);
        btn_action_add = findViewById(R.id.btn_action_add);
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                       // Log.i(TAG, "Name: " + name);
                       // Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }
}
