package com.example.moment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.moment.R;
import com.example.moment.model.Board;

import java.util.List;

public class BoardPopularListAdapter extends RecyclerView.Adapter<BoardPopularListAdapter.ViewHolder> {

    private final List<Board> mDataList;
    private RecyclerViewClickListener mListener;
    private RequestManager requestManager;

    public BoardPopularListAdapter(List<Board> mDataList, RequestManager requestManager) {
        this.mDataList = mDataList;
        this.requestManager = requestManager;
    }


    @NonNull
    @Override
    public BoardPopularListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_board_cardviewlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Board item = mDataList.get(position);

        holder.b_nickname.setText(item.getB_nick());
        holder.b_local.setText(item.getB_local());
        holder.b_coment.setText(item.getB_title());
        holder.b_ddabong.setText("" + item.getB_ddabong());
        holder.b_readcnt.setText("" + item.getB_readcnt());

        requestManager
                .load(item.getB_imgpath())
                .override(350,150)
                .into(holder.b_img);

        requestManager
                .load(item.getB_profileimg())
                .into(holder.b_profileimg);

        if( mListener != null ) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView b_img;
        ImageView b_profileimg;
        TextView b_nickname;
        TextView b_local;
        TextView b_coment;
        TextView b_ddabong;
        TextView b_readcnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b_img = itemView.findViewById(R.id.list_imgView);
            b_profileimg = itemView.findViewById(R.id.list_profileimg);
            b_nickname = itemView.findViewById(R.id.list_nickname);
            b_local = itemView.findViewById(R.id.list_local);
            b_coment = itemView.findViewById(R.id.list_coment);
            b_ddabong = itemView.findViewById(R.id.list_ddabong);
            b_readcnt = itemView.findViewById(R.id.list_readcnt);
        }
    }

    public void setOnClickListener(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onItemClicked(int position);
    }
}
