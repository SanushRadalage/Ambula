package com.intpro.decepticons;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.service.Common;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HospitalItemAdapter extends RecyclerView.Adapter<HospitalItemAdapter.ViewHolder> {
    private ArrayList<String> icons = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    private Activity context_rv;
    int index;
    List<Item> items;

    public HospitalItemAdapter(Activity context, ArrayList<String> names, ArrayList<String> icons) {
        this.context_rv = context;
        this.names = names;
        this.icons = icons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hospital_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Glide.with(context_rv).load(icons.get(i)).into(viewHolder.icon);
        viewHolder.name.setText(names.get(i));

        viewHolder.setItemClickListner(new ItemClickListner(){
            @Override
            void onClick(View view, int position) {
                index = position;
                notifyDataSetChanged();
            }
        });

        if (index == i){
            viewHolder.mainLayout.setBackgroundColor(Color.parseColor("#F0F0FA"));
        }
        else {
            viewHolder.mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListner itemClickListner;
        TextView name;
        CircleImageView icon;
        ConstraintLayout mainLayout;

        public void setItemClickListner(ItemClickListner itemClickListner){
            this.itemClickListner = itemClickListner;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_hospital_item_name);
            icon = itemView.findViewById(R.id.img_hospital_item_icon);
            mainLayout = itemView.findViewById(R.id.hospital_item_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v, getAdapterPosition());
        }
    }


}