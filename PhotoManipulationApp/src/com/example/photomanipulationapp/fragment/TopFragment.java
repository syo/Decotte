package com.example.photomanipulationapp.fragment;

import java.util.ArrayList;

import com.example.photomanipulationapp.ChangeFragmentClass;
import com.example.photomanipulationapp.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class TopFragment extends Fragment{
	
	
	private int mData;
	private Context context;
	
	private ArrayList<Long> idList;
	
	public static TopFragment newInstance(int index) {
		// TODO Auto-generated constructor stub
		
		TopFragment fragment = new TopFragment();
		
		Bundle args = new Bundle();
		args.putInt("index", index);
		fragment.setArguments(args);
		
		
		
		
		
		return fragment;
	}
	
    private ArrayList<Bitmap> load() {
        ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        idList = new ArrayList<Long>();
        ContentResolver cr = this.getActivity().getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor c = this.getActivity().managedQuery(uri, null, null, null, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            long id = c.getLong(c.getColumnIndexOrThrow("_id"));
            Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            list.add(bmp);
            idList.add(id);
            c.moveToNext();
        }
        return list;
    }
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		View view = inflater.inflate(R.layout.layout_fragment_top, container,false);
		
		final ArrayList<Bitmap> list = load();
		BitmapAdapter adapter = new BitmapAdapter(
                this.getActivity().getApplicationContext(), R.layout.layout_grid_image,
                list);
 
        GridView gridView = (GridView) view.findViewById(R.id.imageGridView);
        gridView.setAdapter(adapter);
        
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//画像がタップされた時の処理
				 ChangeFragmentClass.changeDrawFragment(getActivity(), R.id.mainLayout, idList.get(arg2)); //画像のidだけ渡してやる。
			}
		});
		
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
		//int index = args.getInt("index");
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("data", mData);
	}
	
	
	
	
	

}
