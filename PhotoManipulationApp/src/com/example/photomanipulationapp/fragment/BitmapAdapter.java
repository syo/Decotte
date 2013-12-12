package com.example.photomanipulationapp.fragment;

import java.util.List;

import com.example.photomanipulationapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class BitmapAdapter extends ArrayAdapter<Bitmap>{
	
	private int resourceId;
	 
    public BitmapAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	 if (convertView == null) {
             LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inflater.inflate(R.layout.layout_grid_image, null);
         }
    	 
  
         ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
         imageView.setImageBitmap(getItem(position));
  
         return convertView;
    }
}
