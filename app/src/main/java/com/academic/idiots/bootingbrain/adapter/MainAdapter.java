package com.academic.idiots.bootingbrain.adapter;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.academic.idiots.bootingbrain.R;
import com.academic.idiots.bootingbrain.model.GridViewModel;

import java.util.ArrayDeque;
import java.util.List;

/**
 * Created by Thien on 3/20/2015.
 */
public class MainAdapter extends ArrayAdapter<GridViewModel> {

    LayoutInflater layoutInflater;

    public MainAdapter(Context context, int resource, List<GridViewModel> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_square,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgImageView = (ImageView) convertView.findViewById(R.id.img_logo);
            viewHolder.tvGameName = (TextView) convertView.findViewById(R.id.tv_game_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GridViewModel gridViewModel = getItem(position);
        viewHolder.imgImageView.setImageResource(gridViewModel.getResourceID());
        viewHolder.tvGameName.setText(gridViewModel.getGameName());
        return convertView;
    }

    class ViewHolder{
        ImageView imgImageView;
        TextView tvGameName;
    }
}
