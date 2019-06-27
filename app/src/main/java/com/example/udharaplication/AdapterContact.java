package com.example.udharaplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolderContact> {


    private ContactConstructorlList constructorlList;
    private List<ContactConstructorlList> list = new ArrayList<>();
    private Context context;

    public AdapterContact(List<ContactConstructorlList> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolderContact onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_name_layout, viewGroup, false);

        return new ViewHolderContact(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContact viewHolderContact, int i) {


        constructorlList = list.get(i);

           viewHolderContact.DateText.setText(constructorlList.getDate());
           viewHolderContact.NameText.setText(constructorlList.getName());
           viewHolderContact.PhoneText.setText(constructorlList.getPhone());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List getData() {

        return list;
    }

    public void removeItem(int position) {



        list.remove(position);
        notifyDataSetChanged();

    }

    public void restoreItem(ContactConstructorlList item, int position) {

        list.add(position,item);
        notifyDataSetChanged();

    }

    public class ViewHolderContact extends RecyclerView.ViewHolder {

        TextView PhoneText, NameText, DateText;

        public ViewHolderContact(@NonNull View itemView) {
            super(itemView);
            PhoneText = itemView.findViewById(R.id.phoneAdapter);
            NameText = itemView.findViewById(R.id.nameAdapter);
            DateText = itemView.findViewById(R.id.date);


        }
    }


}
