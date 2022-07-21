package com.xiongtao.im.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import cn.bmob.v3.Bmob
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.hyphenate.chat.EMTextMessageBody
import com.xiongtao.im.BuildConfig
import com.xiongtao.im.R
import com.xiongtao.im.ui.activity.ChatActivity
import android.app.NotificationChannel
import android.os.Build
import androidx.core.app.NotificationCompat


class IMApplication : Application() {

    companion object{
        lateinit var instance: IMApplication
    }
    val soundPool = SoundPool(2,AudioManager.STREAM_MUSIC,0)
    val duan by lazy {
        soundPool.load(instance, R.raw.duan,0)
    }
    val messageListener = object : EMMessageListener{
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            if(isForeground()){
                soundPool.play(duan,1f,1f,0,0,1f)
            }else{
                soundPool.play(duan,1f,1f,0,0,1f)
                showNodification(p0)
            }

        }

    }

    private fun showNodification(p0: MutableList<EMMessage>?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationChannel: NotificationChannel? = null
        p0?.forEach {
            var contextText = "非文本消息"
            if(it.type == EMMessage.Type.TXT){
                val emTextMessageBody = it.body as EMTextMessageBody
                contextText = emTextMessageBody.message
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = NotificationChannel("001", "channel_name",NotificationManager.IMPORTANCE_LOW)
                notificationManager.createNotificationChannel(notificationChannel!!)
            }

            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("userName",it.conversationId())
            val taskStackBuilder = TaskStackBuilder.create(this).addParentStack(ChatActivity::class.java).addNextIntent(intent)
            val pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
//            val pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val notification = NotificationCompat
                .Builder(this,"001")
                .setContentTitle("新消息")
                .setContentText(contextText)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.avatar2)
                .setAutoCancel(true)
                .notification


            notificationManager.notify(1,notification)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions());
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);

        Bmob.initialize(applicationContext, "603a51f085fb0ed57a536383310dc5fa");

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    fun isForeground() : Boolean{
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        for (runningProcess in runningAppProcesses){
            if(runningProcess.processName == packageName){
                return runningProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }



}