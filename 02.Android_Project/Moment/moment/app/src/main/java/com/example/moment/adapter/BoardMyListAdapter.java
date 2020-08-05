package com.example.moment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moment.R;
import com.example.moment.model.Board;

import java.util.List;

public class BoardMyListAdapter extends RecyclerView.Adapter<BoardMyListAdapter.ViewHolder> {

    private final List<Board> mDataList;
    private RecyclerViewClickListener mListener;

    public BoardMyListAdapter(List<Board> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public BoardMyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_board_cardviewlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardMyListAdapter.ViewHolder holder, int position) {
        Board item = mDataList.get(position);

        holder.b_nickname.setText(item.getB_nick());
        holder.b_local.setText(item.getB_local());
        holder.b_coment.setText(item.getB_title());
        holder.b_ddabong.setText("" + item.getB_ddabong());
        holder.b_readcnt.setText("" + item.getB_readcnt());

        Glide
                .with(holder.b_img)
                .load(item.getB_imgpath())
                .override(350, 150)
                .into(holder.b_img);

        Glide
                .with(holder.b_userprofile)
                .load(item.getB_profileimg())
                .into(holder.b_userprofile);

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
        ImageView b_userprofile;
        TextView b_nickname;
        TextView b_local;
        TextView b_coment;
        TextView b_ddabong;
        TextView b_readcnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b_img = itemView.findViewById(R.id.list_imgView);
            b_userprofile = itemView.findViewById(R.id.list_profileimg);
            b_local = itemView.findViewById(R.id.list_local);
            b_coment = itemView.findViewById(R.id.list_coment);
            b_ddabong = itemView.findViewById(R.id.list_ddabong);
            b_readcnt = itemView.findViewById(R.id.list_readcnt);
            b_nickname = itemView.findViewById(R.id.list_nickname);
        }
    }

    public void setOnClickListener(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onItemClicked(int position);
    }
}
