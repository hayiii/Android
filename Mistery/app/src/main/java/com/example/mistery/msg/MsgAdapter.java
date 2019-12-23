package com.example.mistery.msg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mistery.R;

import java.util.List;

import static com.example.mistery.R.layout.msg_item;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;

    static class  ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        RelativeLayout rightLyaout;

        TextView leftMsg;
        TextView rightMsg;

        ImageView leftIv;
        ImageView rightIv;

        public ViewHolder(View view){
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLyaout = view.findViewById(R.id.right_layout);
            leftMsg = view.findViewById(R.id.left_msg);
            rightMsg = view.findViewById(R.id.right_msg);
            leftIv = view.findViewById(R.id.left_iv);
            rightIv = view.findViewById(R.id.right_iv);
        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(msg_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED){
            //收到的消息
           holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLyaout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.leftIv.setImageDrawable(msg.getTx());
        }else if (msg.getType() == Msg.TYPE_SENT){
            //发出的消息
            holder.rightLyaout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
            holder.rightIv.setImageDrawable(msg.getTx());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
