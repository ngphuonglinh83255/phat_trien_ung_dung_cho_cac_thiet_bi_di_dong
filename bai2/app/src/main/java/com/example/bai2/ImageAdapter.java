package com.example.bai2;

import android.content.Context;
import android.view.*;
import android.widget.*;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int[] imageIds;
    private String[] imageNames;

    public ImageAdapter(Context context, int[] imageIds, String[] imageNames) {
        this.context = context;
        this.imageIds = imageIds;
        this.imageNames = imageNames;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return imageIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);
        imageView.setImageResource(imageIds[position]);
        textView.setText(imageNames[position]);
        return convertView;
    }
}
