package com.example.photomanipulationapp.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.photomanipulationapp.DrawFragmentSurfaceView;
import com.example.photomanipulationapp.DrawSurfaceView;
import com.example.photomanipulationapp.R;

public class DrawFragment extends Fragment{
	
	private ImageView imageView;
	private DrawSurfaceView surFaceView;
	public int penColorMain = R.drawable.color0;
	
	private FrameLayout drawFrameLayout;
	private int mData;
	
	private ImageButton changeColorButton;
	private ImageButton eraserButton;
	private ImageButton stampButton;
	
	private ImageView stampImage0;
	private ImageView stampImage1;
	private ImageView stampImage2;
	private ImageView stampImage3;
	private ImageView stampImage4;
	private ImageView stampImage5;
	private ImageView stampImage6;
	
	private ImageButton saveButton;
	
	
	
	
	
	private ScrollView stampScroll;
	
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
		
		View view = inflater.inflate(R.layout.layout_fragment_draw, container,false);
		
		imageView = (ImageView)view.findViewById(R.id.imageView);
		//surFaceView = (DrawFragmentSurfaceView)view.findViewById(R.id.drawSurFaceView);
		drawFrameLayout = (FrameLayout)view.findViewById(R.id.drawFrameLayout);
		
		changeColorButton =(ImageButton) view.findViewById(R.id.changeColorButton);
		eraserButton = (ImageButton)view.findViewById(R.id.eraseButton);
		stampButton = (ImageButton)view.findViewById(R.id.stampButton);
		stampScroll = (ScrollView)view.findViewById(R.id.drawStampScroll);
		
		stampImage0 = (ImageView)view.findViewById(R.id.stampImage0);
		stampImage1 = (ImageView)view.findViewById(R.id.stampImage1);
		stampImage2 = (ImageView)view.findViewById(R.id.stampImage2);
		stampImage3 = (ImageView)view.findViewById(R.id.stampImage3);
		stampImage4 = (ImageView)view.findViewById(R.id.stampImage4);
		stampImage5 = (ImageView)view.findViewById(R.id.stampImage5);
		stampImage6 = (ImageView)view.findViewById(R.id.stampImage6);
		
		saveButton = (ImageButton) view.findViewById(R.id.saveButton);
		
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
		
		//if(bmp != null && imageView != null)
		
		//surFaceView.setFixedSize(bmp.getWidth(),bmp.getHeight());
		//imageView.setImageBitmap(this.scale(bmp));
		
		//BitmapDrawable ob = new BitmapDrawable(bmp);
		//surFaceView.setBackgroundDrawable(ob);
		
		//imageView.setImageBitmap(this.scale(bmp));
		
		bmp = this.scale(bmp);
		
		surFaceView =  new DrawSurfaceView(getActivity(), null, bmp) {
			@Override
			public int changeColor() {
				return penColorMain;
			}
		};
		
		LayoutParams params = new LayoutParams(bmp.getWidth(),
				bmp.getHeight());
		
		bmp.recycle();
		bmp = null;
		surFaceView.setLayoutParams(params);
		//scrollInFrame.addView(drawView);
		
		drawFrameLayout.addView(surFaceView);
		
		
		
		changeColorButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				stampScroll.setVisibility(View.GONE);
				
				surFaceView.mode = DrawSurfaceView.Mode.PAINT;
				if(surFaceView.penColor == R.drawable.color0){
					penColorMain = R.drawable.color1;
				} else if(surFaceView.penColor == R.drawable.color1){
					penColorMain = R.drawable.color2;
				}else if(surFaceView.penColor == R.drawable.color2){
					penColorMain = R.drawable.color3;
				}else if(surFaceView.penColor == R.drawable.color3){
					penColorMain = R.drawable.color4;
				}else if(surFaceView.penColor == R.drawable.color4){
					penColorMain = R.drawable.color5;
				}else if(surFaceView.penColor == R.drawable.color5){
					penColorMain = R.drawable.color6;
				}else if(surFaceView.penColor == R.drawable.color6){
					penColorMain = R.drawable.color7;
				}else if(surFaceView.penColor == R.drawable.color7){
					penColorMain = R.drawable.color8;
				}else if(surFaceView.penColor == R.drawable.color8){
					penColorMain = R.drawable.color9;
				}else if(surFaceView.penColor == R.drawable.color9){
					penColorMain = R.drawable.color10;
				}else if(surFaceView.penColor == R.drawable.color10){
					penColorMain = R.drawable.color100;
				}else if(surFaceView.penColor == R.drawable.color100){
					penColorMain = R.drawable.color0;
				}
			}
		});
		
		
		eraserButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stampScroll.setVisibility(View.GONE);
				
				surFaceView.mode = DrawSurfaceView.Mode.ERASER;
			}
		});
		
		stampButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stampScroll.setVisibility(View.VISIBLE);
			}
		});
		
		stampImage0.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp0;
				stampScroll.setVisibility(View.GONE);
				
			}
		});
		
		stampImage1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp1;
				stampScroll.setVisibility(View.GONE);
			}
		});
		
		stampImage2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp2;
				stampScroll.setVisibility(View.GONE);
			}
		});
		
		stampImage3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp3;
				stampScroll.setVisibility(View.GONE);
			}
		});
		
		stampImage4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp4;
				stampScroll.setVisibility(View.GONE);
			}
		});
		
		stampImage5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp5;
				stampScroll.setVisibility(View.GONE);
			}
		});
		
		stampImage6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				surFaceView.mode = DrawSurfaceView.Mode.STAMP;
				surFaceView.stampImageId = R.drawable.stamp6;
				stampScroll.setVisibility(View.GONE);
			}
		});
		
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		        // アラートダイアログのタイトルを設定します
		        alertDialogBuilder.setTitle("保存しますか？");
		        // アラートダイアログのメッセージを設定します
		        // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		        alertDialogBuilder.setPositiveButton("はい",
		                new DialogInterface.OnClickListener() {
		                    @Override
		                    public void onClick(DialogInterface dialog, int which) {
		                    	//saveBitmapToSd(surFaceView.subCanvas);
		                    	
		                    	try {
									saveBitmap(surFaceView.subCanvas);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                    	
		                    }
		                });

		        // アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		        alertDialogBuilder.setNegativeButton("いいえ",
		                new DialogInterface.OnClickListener() {
		                    @Override
		                    public void onClick(DialogInterface dialog, int which) {
		                    }
		                });
		        // アラートダイアログのキャンセルが可能かどうかを設定します
		        alertDialogBuilder.setCancelable(true);
		        AlertDialog alertDialog = alertDialogBuilder.create();
		        // アラートダイアログを表示します
		        alertDialog.show();
				
				
				
			}
		});
		
		
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
	 
	 
	 public void saveBitmapToSd(Bitmap mBitmap) {
		 try {
		 // sdcardフォルダを指定
		 File root = Environment.getExternalStorageDirectory();

		 // 日付でファイル名を作成　
		 Date mDate = new Date();
		 SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss");

		 // 保存処理開始
		 FileOutputStream fos = null;
		 fos = new FileOutputStream(new File(root, fileName.format(mDate) + ".jpg"));

		 // jpegで保存
		 mBitmap.compress(CompressFormat.JPEG, 100, fos);

		 // 保存処理終了
		 fos.close();
		 } catch (Exception e) {
		 Log.e("Error", "" + e.toString());
		 }
		}
	 
	 
	 public void saveBitmap(Bitmap saveImage) throws IOException {

		    final String SAVE_DIR = "/MyPhoto/";
		    File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
		    try{
		        if(!file.exists()){
		            file.mkdir();
		        }
		    }catch(SecurityException e){
		        e.printStackTrace();
		        throw e;
		    }

		    Date mDate = new Date();
		    SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
		    String fileName = fileNameDate.format(mDate) + ".jpg";
		    String AttachName = file.getAbsolutePath() + "/" + fileName;

		    try {
		        FileOutputStream out = new FileOutputStream(AttachName);
		        saveImage.compress(CompressFormat.JPEG, 100, out);
		        out.flush();
		        out.close();
		    } catch(IOException e) {
		        e.printStackTrace();
		        throw e;
		    }
		    
		        // save index
		    ContentValues values = new ContentValues();
		    ContentResolver contentResolver = getActivity().getContentResolver();
		    values.put(Images.Media.MIME_TYPE, "image/jpeg");
		    values.put(Images.Media.TITLE, fileName); 
		    values.put("_data", AttachName);
		    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		}
}
