package com.example.leiyang.threenotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView textView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_menu:
                openSearch();
                return true;
            case R.id.exit_fun:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openSearch() {
        textView = findViewById(R.id.textView);
        textView.setText("我的世界");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onChange(View view) {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }

    public void onNormal(View view) {
        NormalNotification();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onFold(View view) {

        foldNotification();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onHang(View view) {
        hangNotification();
    }


    private void NormalNotification() {
        int notificationId = 001;
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("通知消息")
                        .setContentText("点击一下打开通知内容")
                        .setContentIntent(viewPendingIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void foldNotification() {
        int notificationId = 002;

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pendingIntent);
        builder.setContentText("点击查看内容");
        builder.setContentTitle("折叠式通知");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.fold_notification);

        Notification notification = builder.build();

        notification.bigContentView = remoteViews;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId,notification);

    }

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void hangNotification() {
        int notificationId = 003;
        Intent hangIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.baidu.com"));
        PendingIntent hangPendingIntent =
                PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder notificationBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("悬挂式通知消息")
                        .setContentText("点击一下打开通知内容")
                        .setContentIntent(hangPendingIntent);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this,xuanNotification.class);


        PendingIntent xuanPending = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setFullScreenIntent(xuanPending,true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
