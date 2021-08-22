package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllSurgicalModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_SurgicalAD extends RecyclerView.Adapter<Get_SurgicalAD.MyViewHolder> {

    Context context;
    List<AllSurgicalModel.Datum> allSurgicalModels;
    OnSurgicalClick onSurgicalClick;

    public Get_SurgicalAD(Context context, List<AllSurgicalModel.Datum> allSurgicalModels, OnSurgicalClick onSurgicalClick) {
        this.context = context;
        this.allSurgicalModels = allSurgicalModels;
        this.onSurgicalClick = onSurgicalClick;
    }

    @NonNull
    @NotNull
    @Override
    public Get_SurgicalAD.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Get_SurgicalAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_diagnostic_service_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Get_SurgicalAD.MyViewHolder holder, int position) {
        if (allSurgicalModels.get(position).getIsChecked()){
            holder.addToCart_btn.setText("cart added");
            holder.addToCart_btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.yellow));
        }else {
            holder.addToCart_btn.setText("add to cart");
            holder.addToCart_btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.tabColor));
        }
        holder.surgical_name.setText(allSurgicalModels.get(position).getTitle());
        holder.surgical_price.setText(context.getString(R.string.moneySymbol)+" "+String.valueOf(allSurgicalModels.get(position).getPrice()));
        Glide.with(context)
                .load(Constant.SURGICAL_AVATAR_URL +allSurgicalModels.get(position).getLogo())
                .apply(new RequestOptions().placeholder(R.drawable.ic_surgical1))
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return allSurgicalModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView surgical_name,surgical_price;
        Button addToCart_btn;
        ImageView diagnostic_love,cartAdded;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.cirIM_dia);
            surgical_name=itemView.findViewById(R.id.diagonstic_name);
            surgical_price=itemView.findViewById(R.id.diagonstic_price);
            addToCart_btn=itemView.findViewById(R.id.addToCart_btn);
            diagnostic_love=itemView.findViewById(R.id.diagonstic_love);
            cartAdded = itemView.findViewById(R.id.cartAdded);


            addToCart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allSurgicalModels.get(getAdapterPosition()).getIsChecked()) {
                        allSurgicalModels.get(getAdapterPosition()).setIsChecked(true);
                        onSurgicalClick.sendOrDeleteData_surgical(getAdapterPosition(), "add");
                    } else {
                        allSurgicalModels.get(getAdapterPosition()).setIsChecked(false);
                        onSurgicalClick.sendOrDeleteData_surgical(getAdapterPosition(), "delete");

                    }
                    notifyDataSetChanged();
                }
            });

        }
    }
    public interface OnSurgicalClick{
        void sendOrDeleteData_surgical(int position,String opType);

    }
}
