/*
 * Copyright (C) 2019  All rights reserved for FaraSource (ABBAS GHASEMI)
 * https://farasource.com
 */
package com.syk.sykmm.ui.fragment;

import static com.syk.sykmm.BuildApp.divider;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import com.syk.sykmm.Application;
import com.syk.sykmm.BuildApp;
import com.syk.sykmm.R;
import com.syk.sykmm.general.SaveModel;
import com.syk.sykmm.ui.adapter.OnClickListener;
import com.syk.sykmm.ui.adapter.PostsAdapter;

public class BookMark extends BaseFragment {

    private PostsAdapter postsAdapter;
    private LinearLayout errorNet;

    @Override
    public void onCreateView(@NonNull LayoutInflater inflater) {
        frameLayout.addView(inflater.inflate(R.layout.fragment_list_main, null));
        setTitle(getResources().getString(R.string.mark_name));
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        SwipeRefreshLayout pullRefreshLayout = findViewById(R.id.pull);
        errorNet = findViewById(R.id.error_net);
        ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.sad);
        ((TextView) findViewById(R.id.check)).setText(getResources().getString(R.string.empty));
        pullRefreshLayout.setEnabled(false);
        pullRefreshLayout.setRefreshing(false);
        postsAdapter = new PostsAdapter();
        RecyclerView recView = findViewById(R.id.recycler_view);
        recView.setLayoutManager(new StaggeredGridLayoutManager(BuildApp.showType == 3 ? BuildApp.getCountPx() : 1, StaggeredGridLayoutManager.VERTICAL));
        postsAdapter.setDate(new ArrayList<>());
        postsAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                PostView baseFragment = new PostView();
                baseFragment.addDataArguments(postsAdapter.getDate().get(position));
                startFragment(baseFragment);
            }
        });
        recView.setAdapter(postsAdapter);
        if (divider) {
            DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), 1);
            recView.addItemDecoration(itemDecor);
        }
    }

    @Override
    public void onResumeFragment() {
        postsAdapter.getDate().clear();
        SaveModel saveModel = Application.easySave.retrieveModel("book_mark_info", SaveModel.class);
        if (saveModel != null) {
            errorNet.setVisibility(View.GONE);
            postsAdapter.getDate().addAll(saveModel.hashMapList);
        } else {
            errorNet.setVisibility(View.VISIBLE);
        }
        postsAdapter.notifyDataSetChanged();
    }
}
