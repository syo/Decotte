package com.example.photomanipulationapp.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.photomanipulationapp.DrawFragmentSurfaceView;
import com.example.photomanipulationapp.R;

public class DrawFragment extends Fragment{
	
	private ImageView imageView;
	private DrawFragmentSurfaceView surFaceView;

	
	private int mData;
	
	public static DrawFragment newInstance(int index, Long id) {
		// TODO Auto-generated constructor stub
		
		DrawFragment fragment = new DrawFragment();
		
		Log.e("TAG","index = " + index);
		
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putLong("imageId", id);
		
		fragment.setArguments(args);
		
		return fragment;
	}
	
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.layout_fragment_draw, container,false);
		
		Log.e("TAG","aaaaa");
		
		//imageView = (ImageView)view.findViewById(R.id.backImageView);
		surFaceView = (DrawFragmentSurfaceView)view.findViewById(R.id.drawSurFaceView);
	
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if(savedInstanceState != null){
			mData = savedInstanceState.getInt("data");
		}
		
		Bundle args = getArguments();
		
		Log.e("TAG","what's?? " + args.getInt("index"));
		
		
		
		Bitmap bmp = null;
		try {
			bmp = MediaStore.Images.Media
			        .getBitmap(getActivity().getContentResolver(), ContentUris.withAppendedId(
					        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, args.getLong("imageId")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.e("TAG","bbbb");
		
		//if(bmp != null && imageView != null)
		
		surFaceView.setFixedSize(bmp.getWidth(),bmp.getHeight());
		//imageView.setImageBitmap(this.scale(bmp));
		
		
		BitmapDrawable ob = new BitmapDrawable(bmp);
		surFaceView.setBackgroundDrawable(ob);
		
		
		
		//int index = args.getInt("index");
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("data", mData);
	}
	
	
	 private Bitmap scale(Bitmap src)
	  {
	   int width = src.getWidth();
	   int height = src.getHeight();
	   final int maxWidth = 2048;
	   final int maxHeight = 2048;		//全バージョンサポートのテクスチャ限界
	   if (maxWidth < 0 || maxHeight < 0)
	    return src;
	   if (width < maxWidth && height < maxHeight)
	    return src;
	   
	   if (width > height)
	   {
	    height = (int)(height * (maxWidth / (float)width));
	    width = maxWidth;
	   }
	   if (width < height)
	   {
	    width = (int)(width * (maxHeight / (float)height));
	    height = maxHeight;
	   }
	   Log.d("AIL", String.format("scaling: (%d, %d) -> (%d, %d)", src.getWidth(), src.getHeight(), width, height));
	   return Bitmap.createScaledBitmap(src, width, height, true);  
	  }
	

}
