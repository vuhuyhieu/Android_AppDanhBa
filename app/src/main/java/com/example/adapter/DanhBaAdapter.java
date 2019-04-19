package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.model.DanhBa;
import com.example.projectdanhba_20135557.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DanhBaAdapter extends ArrayAdapter<DanhBa> {
    @NonNull Context context;
    int resource;
    @NonNull List<DanhBa> objects;

    public DanhBaAdapter(@NonNull Context context, int resource, @NonNull List<DanhBa> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row = inflater.inflate(this.resource,null);
        ImageView imgAvatar = row.findViewById(R.id.imgAvatar);
        TextView txtHoTen_item = row.findViewById(R.id.txtHoTen_item);
        CheckBox cboIsChecked = row.findViewById(R.id.cboIsChecked);
        cboIsChecked.setVisibility(View.INVISIBLE);
        imgAvatar.setImageResource(R.drawable.ic_avatar_24dp);
        final DanhBa ct = this.objects.get(position);
        txtHoTen_item.setText(ct.getHoTen());
        return row;
    }
}
