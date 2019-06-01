package com.example.projectdanhba_20135557;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adapter.ContactAdapter;
import com.example.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity  {
    ArrayList<Contact> listContact;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerViewContact;
    ContactAdapter contactAdapter;
    FloatingActionButton buttonAddNewContact;
//    public static final String MACT = "mact";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String GROUP = "group";
    public static final String LIKE = "like";
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
        searchView.setQueryHint("Nhập liên hệ cần tìm");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
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
        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDetailsActivity(listContact.get(position));
            }
        });

        buttonAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNewContactAtivity();
            }
        });
    }

    private void openAddNewContactAtivity() {
        Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
        startActivity(intent);
    }

    private void openDetailsActivity(Contact ct) {
        try {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(NAME, ct.getName());
            bundle.putString(GROUP, ct.getGroup());
            bundle.putString(PHONE_NUMBER, ct.getPhoneNumber());
            bundle.putBoolean(LIKE, ct.isLike());
            intent.putExtra(BUNDLE, bundle);
            startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addControls() {
        try {
            listContact = new ArrayList<>();
            listContact = getContactList();
            recyclerViewContact = findViewById(R.id.recyclerViewContact);
            contactAdapter = new ContactAdapter(listContact);
            layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
            DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            recyclerViewContact.setHasFixedSize(true);
            recyclerViewContact.setLayoutManager(layoutManager);
            recyclerViewContact.addItemDecoration(itemDecoration);
            recyclerViewContact.setAdapter(contactAdapter);
            buttonAddNewContact = findViewById(R.id.buttonAddNewContact);
            contactAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Contact> getContactList() {
        ArrayList<Contact> ds = new ArrayList<>();
        initPermission();
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                ds.add(new Contact(name, phoneNumber));
            }
            phones.close();
        } catch (Exception ex) {
            Log.i("Read Contact Data", ex.toString());
        }
        sortListContact(ds);
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

    public void sortListContact(ArrayList<Contact> arrayList) {

        Collections.sort(arrayList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
}
