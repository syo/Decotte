package com.example.photomanipulationapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DrawFragmentSurfaceView extends SurfaceView implements Runnable,SurfaceHolder,SurfaceHolder.Callback{

	public  DrawFragmentSurfaceView(Context context, AttributeSet attrs){
		super(context, attrs);
		
		// 半透明を設定
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        // コールバック登録
        getHolder().addCallback(this);
        // フォーカス可
        setFocusable(true);
        // このViewをトップにする
        setZOrderOnTop(true);
		
	}
	
	public DrawFragmentSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		// 半透明を設定
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        // コールバック登録
        getHolder().addCallback(this);
        // フォーカス可
        setFocusable(true);
        // このViewをトップにする
        setZOrderOnTop(true);
		
	}

	@Override
	public void addCallback(Callback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Surface getSurface() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rect getSurfaceFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCreating() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Canvas lockCanvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas lockCanvas(Rect dirty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCallback(Callback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFixedSize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFormat(int format) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSizeFromLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Deprecated
	public void setType(int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockCanvasAndPost(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
}
