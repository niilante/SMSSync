package org.addhen.smssync;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SmsSyncService extends Service {
	
	private TimerTask mDoTask;
	private final Handler handler = new Handler();
	private Timer mT = new Timer();
	
	public void onCreate() {
		super.onCreate();
		
		this.startService();
	}
	
	private void startService() {
		mDoTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {

					public void run() {
						if (SmsSyncApplication.mDb.fetchMessagesCount() > 0)
							Util.snycToWeb(SmsSyncService.this);
					}
					
				});	
			}
			
		};
		mT.scheduleAtFixedRate(mDoTask, 300, 30000);
	}
	
	private void stopService() {
		if (mDoTask !=null) {
			mDoTask.cancel();
			mT.cancel();
			mT.purge();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.stopService();
	}
}
