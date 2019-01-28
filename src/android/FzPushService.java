package com.red_folder.phonegap.plugin.backgroundservice.sample;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.TimerTask;
import java.util.Timer;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.json.JSONArray;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.R;
import android.app.PendingIntent;
import android.net.Uri;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import static android.app.Notification.BADGE_ICON_SMALL;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class FzPushService extends BackgroundService {

	private final static String TAG = FzPushService.class.getSimpleName();

	private String host = "";
	private String userName = "";
	private String passWord = "";
	private String clientId = "";
	private String topic = "";
	private String qos = "";
	private String appid = "";

	private MqttClient client;
	private MqttConnectOptions connectOptions;

	private static final Integer MAX_VALUE = 100;

	private NotificationManager notificationManager;
	private android.support.v4.app.NotificationCompat.Builder builder;
	private boolean indeterminate;

	@Override
	protected JSONObject doWork() {
		JSONObject result = new JSONObject();
		// this.clientId = MqttClient.generateClientId();
		if (this.client == null || !this.client.isConnected()) {
			Log.d("fsServicePush", "clientId ：" + this.clientId);
			connect();
		}
		return result;

	}

	@Override
	protected JSONObject getConfig() {
		JSONObject result = new JSONObject();

		try {
			result.put("host", this.host);
			result.put("userName", this.userName);
			result.put("passWord", this.passWord);
			result.put("topic", this.topic);
			result.put("qos", this.qos);
			result.put("appid", this.appid);
			result.put("clientId", this.clientId);
		} catch (JSONException e) {
		}

		return result;
	}

	@Override
	protected void setConfig(JSONObject config) {
		try {
			if (config.has("host")) {
				this.host = config.getString("host");
			}

			if (config.has("userName")) {
				this.userName = config.getString("userName");
			}

			if (config.has("passWord")) {
				this.passWord = config.getString("passWord");
			}

			if (config.has("topic")) {
				this.topic = config.getString("topic");
			}

			if (config.has("qos")) {
				this.qos = config.getString("qos");
			}

			if (config.has("appid")) {
				this.appid = config.getString("appid");
			}

			if(config.has("clientId")){
				this.appid=config.getString("clientId");
			}
		} catch (JSONException e) {
		}

	}

	@Override
	protected JSONObject initialiseLatestResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onTimerEnabled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onTimerDisabled() {
		// TODO Auto-generated method stub

	}

	private void connect() {
		try {
			Log.d("fzServicePush", "connect :" + this.host);
			this.client = new MqttClient(this.host, this.clientId, new MemoryPersistence());
			this.connectOptions = new MqttConnectOptions();
			this.connectOptions.setUserName(this.userName);
			this.connectOptions.setPassword(passWord.toCharArray());
			this.client.setCallback(this.mqttCallback);
			this.client.connect(this.connectOptions);
			subscribe();
		} catch (MqttException e) {
			e.printStackTrace();
			Log.d("fzServicePush", "connect  error " + e);
			Reconnection();
		}
	}

	private void Reconnection() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Log.i("fzServicePush", "mqtt断线重连");
				connect();
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 5000);
	}

	public void subscribe() {
		if (this.client != null) {
			try {
				String[] d = this.qos.split(",");
				int[] ins = new int[d.length];
				for (int i = 0; i < d.length; i++) {
					ins[i] = Integer.parseInt(d[i]);
				}
				this.client.subscribe(this.topic.split(","), ins);
				Log.d("fzServicePush", "订阅topic : " + this.topic);
			} catch (MqttException e) {
				e.printStackTrace();
				Log.d("fzServicePush", "订阅topic  " + this.topic + "error :" + e);
			}
		}
	}

	private MqttCallback mqttCallback = new MqttCallback() {
		@Override
		public void connectionLost(Throwable cause) {
			Log.i("fzServicePush", "连接丢失");
			Reconnection();
		}

		@Override
		public void messageArrived(String topic, MqttMessage message) {
			try {
				Log.i("fzServicePush", "received topic : " + topic);
				String payload = new String(message.getPayload());
				Log.i("fzServicePush", "received msg : " + payload);

				JSONObject jsonObjSplit = new JSONObject(payload);
				String title = jsonObjSplit.getString("title");
				String content = jsonObjSplit.getString("content");

				sendNotification(title, content, payload);
			} catch (JSONException e) {
			}
		}

		private void sendNotification(String title, String content, String payload) {

			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			NotificationCompat.Builder builder = null;

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				NotificationChannel channel = new NotificationChannel("channelPushID", "消息推送通知",
						NotificationManager.IMPORTANCE_DEFAULT);
				channel.enableLights(true); // 设置开启指示灯，如果设备有的话
				channel.setLightColor(Color.RED); // 设置指示灯颜色
				channel.setShowBadge(true); // 设置是否显示角标
				channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);// 设置是否应在锁定屏幕上显示此频道的通知
				channel.setDescription("暂无描述");// 设置渠道描述
				channel.setVibrationPattern(new long[] { 100, 200, 300, 400, 500, 600 });// 设置震动频率
				channel.setBypassDnd(true);// 设置是否绕过免打扰模式
				mNotificationManager.createNotificationChannel(channel);
				builder = new NotificationCompat.Builder(getApplicationContext(), "channelPushID");
				builder.setBadgeIconType(BADGE_ICON_SMALL);// 设置显示角标的样式
				// builder.setNumber(3);//设置显示角标的数量
				// builder.setTimeoutAfter(5000);//设置通知被创建多长时间之后自动取消通知栏的通知。
			} else {
				builder = new NotificationCompat.Builder(getApplicationContext());
			}
			// setContentTitle 通知栏通知的标题
			builder.setContentTitle(title);
			// setContentText 通知栏通知的详细内容
			builder.setContentText(content);
			// setAutoCancel 点击通知的清除按钮是否清除该消息（true/false）
			builder.setAutoCancel(true);
			// setLargeIcon 通知消息上的大图标
			builder.setLargeIcon(
					BitmapFactory.decodeResource(getResources(), getApplicationContext().getApplicationInfo().icon));
			// setSmallIcon 通知上面的小图标
			builder.setSmallIcon(getApplicationContext().getApplicationInfo().icon);
			// 创建一个意图
			Intent intent = new Intent();
			intent.setClassName(FzPushService.this.appid, FzPushService.this.appid + ".MainActivity");
			intent.putExtra("payload", payload);
			// Intent intent =
			// getPackageManager().getLaunchIntentForPackage("com.hea.test");
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
			// setContentIntent 将意图设置到通知上
			builder.setContentIntent(pIntent);
			// 通知默认的声音 震动 呼吸灯
			builder.setDefaults(Notification.DEFAULT_ALL);
			// 构建通知
			Notification notification = builder.build();
			// 将构建好的通知添加到通知管理器中，执行通知
			mNotificationManager.notify(0, notification);
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			Log.i(TAG, "deliveryComplete");
		}
	};
}
