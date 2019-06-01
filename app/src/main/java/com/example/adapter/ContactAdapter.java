package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.Contact;
import com.example.projectdanhba_20135557.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {
    private ArrayList<Contact> listContact;
    private OnItemClickListener mListener;
    private ArrayList<Contact> listContactFull;
    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }

    public ContactAdapter(ArrayList<Contact> listContact) {
        this.listContact = listContact;
        listContactFull = new ArrayList<>(listContact);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageViewAvatar;
        public CheckBox checkBoxIsLiked;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName_item);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar_item);
            checkBoxIsLiked = itemView.findViewById(R.id.checkBoxIsLiked);
            checkBoxIsLiked.setVisibility(View.INVISIBLE);
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

        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(listContact.get(position).getName());
        holder.imageViewAvatar.setImageResource(R.drawable.ic_circled_a_48dp);

}

    @Override
    public int getItemCount() {
        return listContact.size();
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();

            if (constraint==null || constraint.length()==0){
                filteredList.addAll(listContactFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact ct : listContactFull){
                    if (ct.getName().toLowerCase().contains(filterPattern)){
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
            listContact.clear();
            listContact.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
