package com.example.projectdanhba_20135557;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.model.Contact;

public class AddNewContactActivity extends AppCompatActivity {
    ImageButton buttonGoBack, buttonCreate, buttonTakePhoto;
    TextView textViewAddNewContact;
    EditText editTextName, editTextPhoneNumber, editTextGroups;
    CheckBox checkBoxLike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        addControls();
        addEvents();
    }

    private void addEvents() {
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMainActivity();
            }
        });
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotosFromDevice();
            }
        });
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewContact();
            }
        });
    }

    private void addNewContact() {
        Contact ct = new Contact();
        ct.setName(editTextName.getText().toString());
        ct.setPhoneNumber(editTextPhoneNumber.getText().toString());
        ct.setGroup(editTextGroups.getText().toString());
        if (checkBoxLike.isChecked()) {
            ct.setLike(true);
        } else {
            ct.setLike(false);
        }
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONETIC_NAME, editTextName.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, editTextPhoneNumber.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, editTextGroups.getText());
        startActivity(intent);
    }


    private void getPhotosFromDevice() {

    }

    private void goBackToMainActivity() {
        Intent intent = new Intent(AddNewContactActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        buttonCreate = findViewById(R.id.btn_action_create);
        buttonGoBack = findViewById(R.id.btn_go_back);
        buttonTakePhoto = findViewById(R.id.buttonTakePhoto);
        textViewAddNewContact = findViewById(R.id.textViewAddContact);
        editTextName = findViewById(R.id.editTextName);
        editTextGroups = findViewById(R.id.editTextGroup);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        checkBoxLike = findViewById(R.id.checkBoxLike);
    }
}
