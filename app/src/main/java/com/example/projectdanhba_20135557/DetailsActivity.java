package com.example.projectdanhba_20135557;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Contact;

import static com.example.projectdanhba_20135557.MainActivity.NAME;
import static com.example.projectdanhba_20135557.MainActivity.GROUP;
import static com.example.projectdanhba_20135557.MainActivity.PHONE_NUMBER;
import static com.example.projectdanhba_20135557.MainActivity.LIKE;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageViewAvatar, imageViewLike;
    TextView textViewName;
    ImageButton buttonCall;
    TextView textViewPhoneNumber, textViewGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addControls();
        addEvents();
    }

    private void addEvents() {
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
    }

    private void makeCall() {
        try {
            Uri uri = Uri.parse("tel:" + textViewPhoneNumber.getText().toString());
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            if (intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }
        }catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        buttonCall = findViewById(R.id.btn_action_call);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewName = findViewById(R.id.textViewName);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewGroups = findViewById(R.id.textViewGroup);
        imageViewLike = findViewById(R.id.imageViewLike);
        Intent intent = getIntent();
        Bundle bundle;
        Contact ct = new Contact();
        if (intent!=null){
            bundle = intent.getBundleExtra(MainActivity.BUNDLE);
            ct.setName(bundle.getString(NAME));
            ct.setPhoneNumber(bundle.getString(PHONE_NUMBER));
            ct.setGroup(bundle.getString(GROUP));
            ct.setLike(bundle.getBoolean(LIKE));
            textViewName.setText(ct.getName());
            textViewPhoneNumber.setText(ct.getPhoneNumber());
            textViewGroups.setText(ct.getGroup());
            if (ct.isLike()){
                imageViewLike.setVisibility(View.VISIBLE);
            }
            else{
                imageViewLike.setVisibility(View.INVISIBLE);
            }
        }
        else {
            Toast.makeText(this,"Intent is empty",Toast.LENGTH_LONG).show();
        }
        imageViewAvatar.setImageResource(R.drawable.ic_avartar_96dp);
    }
}
