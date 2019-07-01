package com.example.udharaplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolderContact> {

    private AlertDialog.Builder alert;
    private Intent intent;
    private ContactConstructorlList constructorlList;
    private List<ContactConstructorlList> list = new ArrayList<>();
    private Context context;

    public AdapterContact(List<ContactConstructorlList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderContact onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_name_layout, viewGroup, false);

        return new ViewHolderContact(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderContact viewHolderContact, final int i) {


        constructorlList = list.get(i);

        viewHolderContact.NameText.setText(constructorlList.getName());
        viewHolderContact.PhoneText.setText(constructorlList.getPhone());
        viewHolderContact.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(context, ItemsTaken.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                int p = viewHolderContact.getAdapterPosition();
                String phone = viewHolderContact.PhoneText.getText().toString();

                intent.putExtra("PhoneNumber", phone);
                context.startActivity(intent);
            }
        });

        viewHolderContact.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                alert = new AlertDialog.Builder(context);
                alert.setTitle("Update phone number");

                alert.setPositiveButton("okk", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                   //  DataBaseHelperClass dataBaseHelperClass=new DataBaseHelperClass(context);

                     }
                }).setNegativeButton("nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alert.setCancelable(true);
                    }
                }).show();
                return false;
            }
        });


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

        list.add(position, item);
        notifyDataSetChanged();

    }

    public class ViewHolderContact extends RecyclerView.ViewHolder {

        TextView PhoneText, NameText;
        RelativeLayout card;

        public ViewHolderContact(@NonNull View itemView) {
            super(itemView);
            PhoneText = itemView.findViewById(R.id.phoneAdapter);
            NameText = itemView.findViewById(R.id.nameAdapter);
            card = itemView.findViewById(R.id.card1);


        }
    }


}
