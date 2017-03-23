package com.aps.user.sametime;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.ViewHolder>{

    Context context;
    List<SignatureDetail> list = new ArrayList<>();

    public SignAdapter(Context context , List<SignatureDetail> list)
    {
        this.context = context;
        this.list = list;
    }

    public void setGridData(List<SignatureDetail> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sign_model , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SignatureDetail item = list.get(position);

        ImageLoader loader = ImageLoader.getInstance();

        loader.displayImage(item.getSignatureImg() , holder.image);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context , UpdateSignature.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id" , item.getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        ImageButton edit;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.image);
            edit = (ImageButton)itemView.findViewById(R.id.edit);

        }
    }


}
