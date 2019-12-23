package com.example.mistery.msg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mistery.R;


import static com.example.mistery.R.layout.select_item;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String [] mList;

    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
       // LinearLayout selectLayout;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //selectLayout = itemView.findViewById(R.id.select_layout);
            textView = itemView.findViewById(R.id.choice);
        }
    }



    public MyAdapter(String [] list){
        this.mList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(select_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textView.setText(mList[position]);
        //holder.selectLayout.setVisibility(View.VISIBLE);

        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }
}
