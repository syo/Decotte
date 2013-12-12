package com.example.photomanipulationapp;

import com.example.photomanipulationapp.fragment.TopFragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//BluetoothAdapter取得
		BluetoothAdapter Bt = BluetoothAdapter.getDefaultAdapter();
		if(!Bt.equals(null)){
		    //Bluetooth対応端末の場合の処理
		    Log.e("DEBUG","Bluetoothがサポートされてます。");
		}else{
		    //Bluetooth非対応端末の場合の処理
		    Log.e("DEBUG","Bluetoothがサポートれていません。");
		    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		    
	        // アラートダイアログのタイトルを設定します
	        alertDialogBuilder.setTitle("Bluetoothが未対応です");
	        // アラートダイアログのメッセージを設定します
	        alertDialogBuilder.setMessage("アプリを終了します");
	        // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
	        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// アプリの終了
					finish();
				}
			});
	        
	        // アラートダイアログのキャンセルが可能かどうかを設定します
	        alertDialogBuilder.setCancelable(false);
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        // アラートダイアログを表示します
	        alertDialog.show();
		}
		FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainLayout, new TopFragment());
        transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
