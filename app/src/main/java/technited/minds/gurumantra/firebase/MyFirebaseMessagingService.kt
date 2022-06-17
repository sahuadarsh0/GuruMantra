package technited.minds.gurumantra.firebase

import android.app.Notification.DEFAULT_SOUND
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import technited.minds.gurumantra.BaseApplication.Companion.FCM_CHANNEL_ID
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.remote.UpdateService
import technited.minds.gurumantra.model.App
import technited.minds.gurumantra.ui.SplashActivity
import technited.minds.gurumantra.ui.login.LoginActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            if (title != null) {
                if (body != null) {
                    pushNotification(this, title, body)
                }
            }
        }
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            handleNow()
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    private fun pushNotification(context: Context, title: String, message: String) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val descriptionText = context.getString(R.string.zm_description_share)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(FCM_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val icon = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.logo
        )
        val mBuilder = NotificationCompat.Builder(context, FCM_CHANNEL_ID)
            .setLargeIcon(icon)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(R.color.red)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText(message)
            .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
        val resultIntent = Intent(context, SplashActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        mBuilder.setContentIntent(resultPendingIntent)
            .setFullScreenIntent(resultPendingIntent, true)

        val mNotifyMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mNotificationId = 101
        mNotifyMgr.notify(mNotificationId, mBuilder.build())
    }
    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
        val checkUserCall: Call<App> = UpdateService.create().updateToken(token)
        checkUserCall.enqueue(object : Callback<App?> {
            override fun onResponse(
                call: Call<App?>,
                response: retrofit2.Response<App?>
            ) {

            }
            override fun onFailure(call: Call<App?>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val TAG = "asa"
    }


}