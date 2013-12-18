package com.example.photomanipulationapp;

import com.example.photomanipulationapp.fragment.DrawFragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class ChangeFragmentClass {
	
	public static void changeDrawFragment(FragmentActivity activity,int resouceId,Long id) {
	    // フラグメントのインスタンスを生成する。
	    Fragment newFragment = DrawFragment.newInstance(2,id);
	 
	    // ActivityにFragmentを登録する。
	    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
	    // Layout位置先の指定
	    ft.replace(resouceId, newFragment);
	    // Fragmentの変化時のアニメーションを指定
	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    ft.addToBackStack(null);
	    ft.commit();
	}

}
