package com.example.moment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.activity.board.DetailActivity;
import com.example.moment.adapter.BoardLatelyListAdapter;
import com.example.moment.adapter.BoardPopularListAdapter;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularListFragment extends Fragment implements BoardPopularListAdapter.RecyclerViewClickListener {

    private RecyclerView mRecyclerView;
    private BoardPopularListAdapter mBoardPopularListAdapter;
    private RequestManager mRequestManager;
    private ArrayList<Board> mBoardList = new ArrayList<>();
    SwipeRefreshLayout refresh;

    String url = Common.SERVER_URL + "/PopularListAction.mo";

    public PopularListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_popular_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.popular_ListRv);

        //true면 변경불가 false면 변경가능
        mRecyclerView.setHasFixedSize(false);

        mBoardPopularListAdapter = new BoardPopularListAdapter(mBoardList, mRequestManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mBoardPopularListAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mBoardPopularListAdapter);

        //애니메이션 추가
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        animator.setMoveDuration(1000);
        animator.setChangeDuration(1000);
        mRecyclerView.setItemAnimator(animator);

        refresh = rootView.findViewById(R.id.popularListRefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mBoardList = new AsyncBoardList(getActivity()).execute(url).get();
                    mBoardPopularListAdapter = new BoardPopularListAdapter(mBoardList, mRequestManager);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mBoardPopularListAdapter.setOnClickListener(PopularListFragment.this);
                    mRecyclerView.setAdapter(mBoardPopularListAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
                refresh.setRefreshing(false);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestManager = Glide.with(this);
        try {
            mBoardList = new AsyncBoardList(getActivity()).execute(url).get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("b_no", mBoardList.get(position).getB_no());
        intent.putExtra("Activity", MainActivity.class);
        intent.putExtra("b_pano", "N");
        startActivity(intent);
    }

}
