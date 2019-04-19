package com.example.adapter;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.DanhBa;
import com.example.projectdanhba_20135557.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DanhBaAdapterRecycleView extends RecyclerView.Adapter<DanhBaAdapterRecycleView.ViewHolder> implements Filterable {
    private ArrayList<DanhBa> arrDanhBa;
    private OnItemClickListener mlistener;
    private ArrayList<DanhBa> arrDanhBaFull;
    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }

    public DanhBaAdapterRecycleView(ArrayList<DanhBa> arrDanhBa) {
        this.arrDanhBa = arrDanhBa;
        arrDanhBaFull = new ArrayList<>(arrDanhBa);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtHoTen;
        public ImageView imageViewAvartar;
        public CheckBox cboIsChecked;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtHoTen = itemView.findViewById(R.id.txtHoTen_item);
            imageViewAvartar = itemView.findViewById(R.id.imgAvatar);
            cboIsChecked = itemView.findViewById(R.id.cboIsChecked);
            cboIsChecked.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtHoTen.setText(arrDanhBa.get(position).getHoTen());
        holder.imageViewAvartar.setImageResource(R.drawable.ic_circled_a_48dp);

}

    @Override
    public int getItemCount() {
        return arrDanhBa.size();
    }

    @Override
    public Filter getFilter() {
        return danhBaFilter;
    }

    private Filter danhBaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DanhBa> filteredList = new ArrayList<>();

            if (constraint==null || constraint.length()==0){
                filteredList.addAll(arrDanhBaFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DanhBa ct : arrDanhBaFull){
                    if (ct.getHoTen().toLowerCase().contains(filterPattern)){
                        filteredList.add(ct);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrDanhBa.clear();
            arrDanhBa.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
