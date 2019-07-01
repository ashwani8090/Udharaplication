package com.example.udharaplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ViewHolderItems> {

    private Context context;
    private Intent intent;
    private List<ConstructorItems> list = new ArrayList<>();
    private String date;

    public AdapterItems(Context context, List<ConstructorItems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderItems onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);

        return new ViewHolderItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderItems viewHolderItems, int i) {
        ConstructorItems constructorItems = list.get(i);
        viewHolderItems.textView.setText(constructorItems.getITEM_NAME().toString().trim());
        viewHolderItems.textView2.setText(String.format("%s", constructorItems.getAMOUNT().toString().trim()));


        viewHolderItems.card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, SelectedItemActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.putExtra("ID",list.get(viewHolderItems.getAdapterPosition()).getID());



                context.startActivity(intent);

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

    public void restoreItem(ConstructorItems item, int position) {

        list.add(position, item);
        notifyDataSetChanged();

    }

    public  class ViewHolderItems extends RecyclerView.ViewHolder {

        TextView textView,textView2;
        RelativeLayout card4;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_name);
            card4=itemView.findViewById(R.id.card4);
            textView2=itemView.findViewById(R.id.item_amount);

        }
    }

}
