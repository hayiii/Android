package com.example.mistery.msg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mistery.R;

import java.util.List;

public class ConverAdapter extends RecyclerView.Adapter<ConverAdapter.ViewHolder>{

    private List<Person> personList;

    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        ImageView tx;
        TextView content;
        TextView latest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.con_item);
            tx = itemView.findViewById(R.id.con_iv);
            content = itemView.findViewById(R.id.con_tv);
            latest = itemView.findViewById(R.id.latest_talk);
        }
    }

    public ConverAdapter(List<Person> personList){
        this.personList = personList;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.conver_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.layout.setVisibility(View.VISIBLE);

        holder.latest.setText(person.getLatest());
        holder.content.setText(person.getContent());
        holder.tx.setImageDrawable(person.getTx());

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
        return personList.size();
    }
}