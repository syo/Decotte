package com.example.photomanipulationapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//import com.picoton.souzounurie.R;
//import com.picoton.souzounurie.MainActivity;

public abstract class DrawSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	public abstract int changeColor();

	public enum PenColorMode {
		TEST_COLOR, ERASER
	}

	public enum Mode {
		PAINT, ERASER, FIRST, MOVE, STAMP
	}

	// 太いペンの太さ
	public static float WIDE_SIZE = 20.0f;
	// 細いペンの太さ
	public static float NARROW_SIZE = 10.0f;
	// 通常時のスケール
	public static float SCALE_NORMAL = 1.0f;
	// 拡大時のスケール
	public static float SCALE_UP = 2.0f;

	public Mode mode;
	// 消しゴムは内部で作成している為モード切り替え
	public PenColorMode penColorMode;
	// 現在選択中のペンの色
	public int penColor;
	// 現在選択中のペンの太さ
	public float penSize;
	
	//スタンプの画像ID
	public int stampImageId;
	//スタンプのPoint
	private Point stampPoint;
	
	// Draw中の座標
	private Point newPoint;
	private Point oldPoint;
	// offscreen用surfaceViewのチラツキ防止
	public Bitmap subCanvas;
	
	
	//保存用
	public Bitmap compCanvas;
	
	
	//スタンプ用のキャンバス
	public Bitmap stampCanvas;
	
	//スタンプ用のフラッグ
	private boolean stampFLG;
	
	// 背景画像
	public Bitmap backImage;
	// 初回生成判定
	private Boolean firstFlag = false;
	// スケールを設定
	public float viewScale;
	// 移動用
	public int moveX = 0;
	public int moveY = 0;

	public DrawSurfaceView(Context context, AttributeSet attrs, Bitmap backImage) {
		super(context, attrs);
		this.backImage = backImage.copy(Bitmap.Config.ARGB_8888, true);
		initialize();
	}

	private void initialize() {

		// 半透明を設定
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// コールバック登録
		getHolder().addCallback(this);
		// フォーカス可
		setFocusable(true);
		// このViewをトップにする
		
		// SurfaceHolder の取得
		SurfaceHolder holder = getHolder();
		// SurfaceHolder に コールバックを設定
		holder.addCallback(this);
		holder.setFixedSize(getWidth(), getHeight());
		// 初期値
		newPoint = new Point(-1, -1);
		oldPoint = new Point(-1, -1);
		stampPoint = new Point(-1, -1);

		penSize = WIDE_SIZE;
		mode = Mode.FIRST;
		viewScale = SCALE_NORMAL;
	//	penColor = R.drawable.color0;
		penColor = android.R.color.black;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!firstFlag) {
			firstFlag = true;
			// オフスクリーン用のBitmapを生成する
			subCanvas = Bitmap.createBitmap(getWidth(), getHeight(),
					Config.ARGB_8888);
			stampCanvas = Bitmap.createBitmap(getWidth(), getHeight(),
					Config.ARGB_8888);
			
			compCanvas = Bitmap.createBitmap(getWidth(), getHeight(),
					Config.ARGB_8888);
			
			redraw();
			mode = Mode.PAINT;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Bitmapを解放する(GC待ち)
		/*
		if (subCanvas != null)
			subCanvas.recycle();
		if (backImage != null)
			backImage.recycle();
			*/
	}

	@Override
	public void draw(Canvas canvas) {

		// Holderに持ってるCanvasじゃないと、ダブルバッファが有効にならない
		// 多分このDrawはSurfaceViewが二つ持ってるCanvasのそれぞれに描画する関数

		canvas.save();
		// 透明色で一面上書き
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);

		// 取りあえず白で塗る
		// canvas.drawColor(getResources().getColor(android.R.color.white));
		Canvas offScreen = null;
		Canvas stampScreen = null;
		Canvas compScreen =null;

		// オフスクリーンバッファに描画する
		if (subCanvas != null) {
			// オフスクリーンバッファを生成
			offScreen = new Canvas(subCanvas);
			 stampScreen = new Canvas(stampCanvas);
			 
			 compScreen = new Canvas(compCanvas);
			 compScreen.drawColor(0, PorterDuff.Mode.CLEAR);
			 
			stampScreen.drawColor(0, PorterDuff.Mode.CLEAR);
			
			// offScreen.drawColor(0,PorterDuff.Mode.CLEAR);

			if (mode == Mode.PAINT) {
				// 線を引く
				if (oldPoint.x != -1 && oldPoint.y != -1) {
					// move中の場合
					Shader shader = new BitmapShader(
							BitmapFactory.decodeResource(getResources(),
									penColor), Shader.TileMode.REPEAT,
							Shader.TileMode.REPEAT);
					Paint paint = new Paint();
					paint.setStrokeWidth(penSize);
					paint.setShader(shader);
					paint.setAntiAlias(true);
					offScreen.drawCircle(newPoint.x, newPoint.y, penSize / 2,
							paint);
					offScreen.drawLine(oldPoint.x, oldPoint.y, newPoint.x,
							newPoint.y, paint);
				} else {

					// 始点の場合点を打って丸み出す
					Shader shader = new BitmapShader(
							BitmapFactory.decodeResource(getResources(),
									penColor), Shader.TileMode.REPEAT,
							Shader.TileMode.REPEAT);
					Paint paint = new Paint();
					paint.setShader(shader);
					paint.setAntiAlias(true);

					offScreen.drawCircle(newPoint.x, newPoint.y,
							(float) penSize / 2, paint);
				}
			} else if (mode == Mode.ERASER) {
				
				// 線を引く
				if (oldPoint.x != -1 && oldPoint.y != -1) {
					// move中の場合
					Paint paint = new Paint();
					paint.setStrokeWidth(penSize);
					// paint.setColor(getResources().getColor(android.R.color.white));
					paint.setXfermode(new PorterDuffXfermode(
							PorterDuff.Mode.SRC));
					paint.setARGB(0, 0, 0, 0);
					paint.setAntiAlias(true);
					// paint.setShader(shader);
					// 点を打って角を丸くする
					offScreen.drawCircle(newPoint.x, newPoint.y, penSize / 2,
							paint);
					offScreen.drawLine(oldPoint.x, oldPoint.y, newPoint.x,
							newPoint.y, paint);
				} else {
					// 始点の場合
					Paint paint = new Paint();
					paint.setXfermode(new PorterDuffXfermode(
							PorterDuff.Mode.SRC));
					paint.setARGB(0, 0, 0, 0);
					paint.setAntiAlias(true);
					// paint.setColor(getResources().getColor(R.color.clear));
					// paint.setShader(shader);
					offScreen.drawCircle(newPoint.x, newPoint.y, penSize / 2,
							paint);
				}
			}else if(mode == Mode.STAMP){
				stampScreen.drawBitmap(BitmapFactory.decodeResource(getResources(),stampImageId)
						, newPoint.x,newPoint.y, null);
				
				/*
				// move中の場合
				Shader shader = new BitmapShader(
						BitmapFactory.decodeResource(getResources(),
								penColor), Shader.TileMode.REPEAT,
						Shader.TileMode.REPEAT);
				Paint paint = new Paint();
				paint.setStrokeWidth(penSize);
				paint.setShader(shader);
				paint.setAntiAlias(true);
				offScreen.drawCircle(newPoint.x, newPoint.y, penSize / 2,
						paint);
				offScreen.drawLine(oldPoint.x, oldPoint.y, newPoint.x,
						newPoint.y, paint);
				
				*/
			}
		}

		canvas.scale(viewScale, viewScale);
		
		
		// 背景画像の描画
		canvas.drawBitmap(backImage, -moveX, -moveY, null);

		
		if(stampFLG && offScreen != null){
			offScreen.drawBitmap(stampCanvas, -moveX, -moveY,null);
			// オフスクリーンバッファを描画する
			canvas.drawBitmap(subCanvas, -moveX, -moveY, null);
			
		}else{
			// オフスクリーンバッファを描画する
			canvas.drawBitmap(subCanvas, -moveX, -moveY, null);
			//スタンプ用
			canvas.drawBitmap(stampCanvas,-moveX,-moveY,null);
		}
		
		compScreen.drawBitmap(backImage, -moveX,-moveY,null);
		compScreen.drawBitmap(subCanvas,-moveX,-moveY,null);
		
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		stampFLG = false;

		int eventX = (int) (event.getX() * (1 / viewScale) + moveX);
		int eventY = (int) (event.getY() * (1 / viewScale) + moveY);

		String action = "";
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			action = "ACTION_DOWN";
			
			// SEの再生開始
			/*
			if (mode == Mode.PAINT)
				MainActivity.playSound(DrawFragment.penSE + 7);
			else
				MainActivity.playSound(DrawFragment.penSE + 6);
			*/

			penColor = changeColor();

			newPoint.x = eventX;
			newPoint.y = eventY;
			oldPoint.x = -1;
			oldPoint.y = -1;

			break;
		case MotionEvent.ACTION_UP:
			action = "ACTION_UP";

			newPoint.x = eventX;
			newPoint.y = eventY;
			oldPoint.x = -1;
			oldPoint.y = -1;
			
			stampPoint.x = eventX;
			stampPoint.y = eventY;
			stampFLG = true;

			/*
			// SEの再生停止
			if (mode == Mode.PAINT)
				MainActivity.stopSound(DrawFragment.penSE + 7);
			else
				MainActivity.stopSound(DrawFragment.penSE + 6);
			*/
			break;
		case MotionEvent.ACTION_MOVE:
			action = "ACTION_MOVE";

			oldPoint.x = newPoint.x;
			oldPoint.y = newPoint.y;
			newPoint.x = eventX;
			newPoint.y = eventY;
			
			break;
		case MotionEvent.ACTION_CANCEL:
			action = "ACTION_CANCEL";

			break;
		default:
			break;
		}
		redraw();
		return true;
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable parent = super.onSaveInstanceState();
		Bundle b = new Bundle();
		b.putParcelable("Parent", parent);
		b.putParcelable("bmp", subCanvas);
		return b;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		firstFlag = true;

		if (!(state instanceof Bundle))
			return;

		Bundle b = (Bundle) state;
		super.onRestoreInstanceState(b.getParcelable("Parent"));
		subCanvas = b.getParcelable("bmp");
	}
	
	

	public void setViewScale(float scale) {
		viewScale = scale;
		redraw();
	}
	
	

	public void setMoveY(Boolean flag) {
		// ピクセルが偶数前提。奇数だと裏が見切れる
		if (flag && moveY > 0) {
			moveY -= 2;
		} else if (!flag && moveY < (getHeight() - getHeight() / SCALE_UP)) {
			moveY += 2;
		}
		redraw();
	}
	
	

	public void setMoveX(Boolean flag) {
		// ピクセルが偶数前提
		if (flag && moveX > 0) {
			moveX -= 2;
		} else if (!flag && moveX < (getWidth() - getWidth() / SCALE_UP)) {
			moveX += 2;
		}
		redraw();
	}

	public void redraw() {
		// ホルダーからキャンバスの取得
		Canvas canvas = getHolder().lockCanvas();
		// 描画処理
		draw(canvas);
		// 描画内容の確定
		getHolder().unlockCanvasAndPost(canvas);
	}
}
