package com.example.projectdanhba_20135557;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adapter.DanhBaAdapter;
import com.example.adapter.DanhBaAdapterRecycleView;
import com.example.model.DanhBa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity  {
    ArrayList<DanhBa> arrDanhBa;
    LinearLayoutManager layoutManager;
    RecyclerView rvDanhBa;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.btn_action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Nhập liên hệ cần tìm");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterDanhBa.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.btn_action_show_more:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void addEvents() {
        adapterDanhBa.setOnItemClickListener(new DanhBaAdapterRecycleView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                xuLyMoManHinhDetalis(arrDanhBa.get(position));
            }
        });

        btn_action_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyMoManHinhThemLienHe();
            }
        });
    }

    private void xuLyMoManHinhThemLienHe() {
        Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
        startActivity(intent);
    }

    private void xuLyMoManHinhDetalis(DanhBa ct) {
        try {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(HOTEN, ct.getHoTen());
            bundle.putString(NHOM, ct.getNhom());
            bundle.putString(SODIENTHOAI, ct.getSoDienThoai());
            bundle.putBoolean(THICH, ct.isThich());
            intent.putExtra(BUNDLE, bundle);
            startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addControls() {
        try {
            arrDanhBa = new ArrayList<>();
            arrDanhBa = getContactList();
            rvDanhBa = findViewById(R.id.rvDAnhBa);
            adapterDanhBa = new DanhBaAdapterRecycleView(arrDanhBa);
            layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
            DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            rvDanhBa.setHasFixedSize(true);
            rvDanhBa.setLayoutManager(layoutManager);
            rvDanhBa.addItemDecoration(itemDecoration);
            rvDanhBa.setAdapter(adapterDanhBa);
            btn_action_add = findViewById(R.id.btn_action_add);
            adapterDanhBa.notifyDataSetChanged();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<DanhBa> getContactList() {
        ArrayList<DanhBa> ds = new ArrayList<>();
        initPermission();
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                ds.add(new DanhBa(name, phoneNumber));
            }
            phones.close();
        } catch (Exception ex) {
            Log.i("Read Contact Data", ex.toString());
        }
        sapXepDanhBa(ds);
        return ds;
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    public void sapXepDanhBa(ArrayList<DanhBa> arrayList) {

        Collections.sort(arrayList, new Comparator<DanhBa>() {
            @Override
            public int compare(DanhBa o1, DanhBa o2) {
                return o1.getHoTen().compareTo(o2.getHoTen());
            }
        });
    }
}
