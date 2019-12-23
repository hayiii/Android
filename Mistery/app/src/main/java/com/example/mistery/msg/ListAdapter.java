package com.example.mistery.msg;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mistery.MyDB;
import com.example.mistery.R;
import com.example.mistery.myview.Rotate3D;

import static com.example.mistery.R.layout.card_layout;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private String [] mList;
    private Context context;

    private MyDB myDB;
    private SQLiteDatabase db;

    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        FrameLayout frameLayout;
        LinearLayout front;
        LinearLayout back;
        TextView item;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_layout);
            front = itemView.findViewById(R.id.front);
            back = itemView.findViewById(R.id.back);
            item = itemView.findViewById(R.id.evidence_item);
            title = itemView.findViewById(R.id.evidence_title);
        }
    }

    public ListAdapter(Context context,String [] list){
        this.context = context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(card_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.back.setVisibility(View.GONE);
        holder.front.setVisibility(View.VISIBLE);
        holder.title.setText(mList[position].split(" ")[0]);
        holder.item.setText(mList[position].split(" ")[1]);

        myDB = new MyDB(context);
        db = myDB.getWritableDatabase();

        if (onItemClickListener!=null){
            holder.front.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.front,holder.getLayoutPosition());

                    ContentValues cv = new ContentValues();
                    cv.put("t_title",holder.title.getText().toString());
                    cv.put("t_item",holder.item.getText().toString());
                    db.insert("thread",null,cv);

                    final float centerX = holder.frameLayout.getWidth()/2.0f;
                    final float centerY = holder.frameLayout.getHeight()/2.0f;
                    final float deftZ = 120.0f;
                    final Rotate3D rotate = new Rotate3D(0,90,centerX,centerY,deftZ,true);
                    rotate.setDuration(300);    //动画持续时间
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    rotate.setAnimationListener(new DisplayNextView(holder.frameLayout,holder.front,holder.back));
                    holder.frameLayout.startAnimation(rotate);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    private final class DisplayNextView implements Animation.AnimationListener{
        private FrameLayout frameLayout;
        private LinearLayout front;
        private LinearLayout back;

        private DisplayNextView(FrameLayout frameLayout,LinearLayout front,LinearLayout back){
            this.frameLayout = frameLayout;
            this.front = front;
            this.back = back;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            frameLayout.post(new SwapViews(frameLayout,front,back));
            if (front.getVisibility() == View.VISIBLE)
                front.setVisibility(View.GONE);
            if (back.getVisibility() == View.GONE)
                back.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private final class SwapViews implements Runnable{
        private LinearLayout front;
        private LinearLayout back;
        private FrameLayout frameLayout;

        public SwapViews(FrameLayout frameLayout,LinearLayout front,LinearLayout back){
            this.front = front;
            this.back = back;
            this.frameLayout = frameLayout;
        }

        public void run(){
            showView(frameLayout,back,front,90,0);
        }
    }

    private void showView(FrameLayout frameLayout,LinearLayout showView,LinearLayout hiddenView,int startDegree,int endDegree){
        float centerX = showView.getWidth()/2.0f;
        float centerY = showView.getHeight()/2.0f;
        float centerZ = 120.0f;

        if (centerX == 0 || centerY == 0){
            showView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
            centerX = showView.getMeasuredWidth()/2.0f;
            centerY = showView.getMeasuredHeight()/2.0f;
        }
        hiddenView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
        Rotate3D rotation = new Rotate3D(startDegree,endDegree,centerX,centerY,centerZ,false);
        rotation.setDuration(300);
        rotation.setInterpolator(new DecelerateInterpolator());
        frameLayout.startAnimation(rotation);
    }
}