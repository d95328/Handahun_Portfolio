package com.example.moment.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.moment.R;
import com.example.moment.activity.MainActivity;
import com.example.moment.activity.board.DetailActivity;
import com.example.moment.adapter.BoardLatelyListAdapter;
import com.example.moment.adapter.BoardPanoListAdapter;
import com.example.moment.common.Common;
import com.example.moment.model.Board;
import com.example.moment.trancefer.AsyncBoardList;
import com.example.moment.trancefer.AsyncPanoList;

import java.util.ArrayList;

public class PanoListFragment extends Fragment implements BoardPanoListAdapter.RecyclerViewClickListener {
    private RecyclerView mRecyclerView;
    private BoardPanoListAdapter mBoardPanoListAdapter;
    private RequestManager mRequestManager;
    private ArrayList<Board> mBoardList = new ArrayList<>();
    SwipeRefreshLayout refresh;

    String url = Common.SERVER_URL + "/panoAndroidList.pa";

    private LatelyListFragment.OnSearchResultListener onSearchResultListener;

    public PanoListFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pano_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.pano_ListRv);

        //true면 변경불가 false면 변경가능
        mRecyclerView.setHasFixedSize(false);

        mBoardPanoListAdapter = new BoardPanoListAdapter(mBoardList, mRequestManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mBoardPanoListAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mBoardPanoListAdapter);

        //애니메이션 추가
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        animator.setMoveDuration(1000);
        animator.setChangeDuration(1000);
        mRecyclerView.setItemAnimator(animator);

        refresh = rootView.findViewById(R.id.panoListRefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mBoardList = new AsyncPanoList(getActivity()).execute(url).get();
                    mBoardPanoListAdapter = new BoardPanoListAdapter(mBoardList, mRequestManager);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mBoardPanoListAdapter.setOnClickListener(PanoListFragment.this);
                    mRecyclerView.setAdapter(mBoardPanoListAdapter);
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

        //쓰레드작업
        mRequestManager = Glide.with(this);
        try {
            mBoardList = new AsyncPanoList(getActivity()).execute(url).get();
            onSearchResultListener.onSearchResultSet(mBoardList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("b_no", mBoardList.get(position).getB_no());
        intent.putExtra("b_pano","Y");
        intent.putExtra("Activity", MainActivity.class);
        startActivity(intent);
    }


    //메인액티비티에서 구현할 인터페이스 선언
    public interface OnSearchResultListener {
        void onSearchResultSet(ArrayList<Board> board);
    }

    //mBoardList에 담겨온 리스트 정보를 메인액티비티에 넘겨주기 위한 onAttatch 메소드(프래그먼트의 라이프사이클 메소드임)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof LatelyListFragment.OnSearchResultListener) {
            onSearchResultListener = (LatelyListFragment.OnSearchResultListener) context;
        } else {
            throw new RuntimeException(context.toString() + "OnSearchResultListener 구현 안됨.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSearchResultListener = null;
    }
}
