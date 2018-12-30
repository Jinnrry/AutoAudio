package cn.xjiangwei.autoaudio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xjiangwei.autoaudio.vo.Item;

public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Item> mData;
    private OnItemClickListener mOnItemClickListener;

    public NormalRecyclerViewAdapter(ArrayList<Item> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<Item> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position).getRule());
        holder.mValue.setText(mData.get(position).getValue());

        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(mData.get(position).getId());
                }
            });
            holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(mData.get(position).getId());
                    return false;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onClick(long position);
        void onLongClick(long position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTv;
        TextView mValue;
        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
            mValue = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
