package com.example.udharaplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolderContact> {

    private String phonNumber;
    private DatabaseDates databaseDates;
    private DatabaseItems databaseItems;
    private DataBaseHelperClass dataBaseHelperClass;
    private Dialog dialog;
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

        dialog=new Dialog(context);
        dataBaseHelperClass=new DataBaseHelperClass(context);
        databaseDates=new DatabaseDates(context);
        databaseItems=new DatabaseItems(context);
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

                dialog.setContentView(R.layout.popup_phone_layout);
                dialog.show();

                Button done=dialog.findViewById(R.id.popupphone_done);


                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText=dialog.findViewById(R.id.pop_phone_edit);

                        phonNumber=editText.getText().toString().trim();
                        if (phonNumber.length()!=10){

                            Toast.makeText(context, "Phone number is badly formatted", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            dataBaseHelperClass.UpDatePhone(viewHolderContact.PhoneText.getText().toString().trim(),phonNumber);
                            databaseDates.UpDatePhone(viewHolderContact.PhoneText.getText().toString().trim(),phonNumber);
                            databaseItems.UpDatePhone(viewHolderContact.PhoneText.getText().toString().trim(),phonNumber);

                            dialog.dismiss();
                             notifyDataSetChanged();
                            context.startActivity(new Intent(context,contactlist.class));

                        }
                    }
                });

                return true;
            }
        });




        Context wrapper=new ContextThemeWrapper(context,R.style.PopupMenu);

        final PopupMenu popupMenu=new PopupMenu(wrapper,viewHolderContact.hint);
        popupMenu.inflate(R.menu.action_menu_hint);
        viewHolderContact.hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
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

        TextView PhoneText, NameText,hint;
        RelativeLayout card;

        public ViewHolderContact(@NonNull View itemView) {
            super(itemView);
            PhoneText = itemView.findViewById(R.id.phoneAdapter);
            NameText = itemView.findViewById(R.id.nameAdapter);
            card = itemView.findViewById(R.id.card1);
            hint=itemView.findViewById(R.id.hint);


        }
    }


}
