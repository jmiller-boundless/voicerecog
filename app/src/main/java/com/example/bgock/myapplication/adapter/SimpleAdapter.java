package com.example.bgock.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bgock.myapplication.R;
import com.example.bgock.myapplication.model.SimpleViewModel;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends Adapter {
    private List<SimpleViewModel> models = new ArrayList<>();
    private Context context;

    /**
     * Adapter constructor
     *
     * @param viewModels
     *         A collection of viewmodels which will contain the data that will be used in each ViewHolder
     */
    public SimpleAdapter(final List<SimpleViewModel> viewModels, Context context) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
        }
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        final RecyclerView.ViewHolder holder = new SimpleViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Toast.makeText(context, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SimpleViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_simple_itemview;
    }



}
