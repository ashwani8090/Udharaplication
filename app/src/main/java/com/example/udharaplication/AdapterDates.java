package com.example.udharaplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterDates extends RecyclerView.Adapter<AdapterDates.ViewholderDates> {

    private String date, phone;
    private Context context;
    private Intent intent;
    private DatabaseDates databaseDates;

    private List<ConstructorDate> list = new ArrayList<>();

    public AdapterDates(Context context, List<ConstructorDate> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewholderDates onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_dates, viewGroup, false);

        ViewholderDates viewholderDates = new ViewholderDates(view);
        return viewholderDates;


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewholderDates viewholderDates, final int i) {


        ConstructorDate cnstructoritems = list.get(i);
        viewholderDates.textView.setText(cnstructoritems.getDATE().toString().trim());


        viewholderDates.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = viewholderDates.getAdapterPosition();
                intent = new Intent(context, ItemsOnDate.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {


                    phone = list.get(position).getPHONE();
                } catch (Exception e) {
                }
                date = viewholderDates.textView.getText().toString().trim();
                intent.putExtra("phone", phone);
                intent.putExtra("date", date);

              context.startActivity(intent);

            }
        });



    try {

      if (cnstructoritems.getPAID().equals("paid")){
          viewholderDates.check.setVisibility(View.VISIBLE);
      }
      else {
          viewholderDates.check.setVisibility(View.INVISIBLE);
      }


    }
    catch (Exception e){
        viewholderDates.check.setVisibility(View.INVISIBLE);
    }







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

    public void restoreItem(ConstructorDate item, int position) {

        list.add(position, item);
        notifyDataSetChanged();

    }


    public class ViewholderDates extends RecyclerView.ViewHolder {

        TextView textView,check;
        RelativeLayout card;


        public ViewholderDates(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.DatesItem);
            card = itemView.findViewById(R.id.card2);
            check=itemView.findViewById(R.id.check);

        }
    }

}
