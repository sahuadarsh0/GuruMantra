package technited.minds.gurumantra.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import technited.minds.gurumantra.R
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class PaymentPage : AppCompatActivity(), PaymentResultListener {
    private lateinit var packId: String
    private lateinit var packPrice: String
    private lateinit var packTitle: String

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        setContentView(R.layout.activity_payment_page)

        packId = intent.getStringExtra("id").toString()
        packPrice = intent.getStringExtra("price").toString()
        packTitle = intent.getStringExtra("title").toString()
        Checkout.preload(applicationContext)
        startPayment(packPrice+"00")
    }

    private fun startPayment(amount: String) {
        val activity: Activity = this
        val co = Checkout()
        co.setImage(R.drawable.logo)

        try {
            val options = JSONObject()
            options.put("name", "Gurumantra.online")
            options.put("description", packTitle)
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", amount)
            options.put("send_sms_hash", true);

            val prefill = JSONObject()
            prefill.put("email", userSharedPreferences["email"])
            prefill.put("contact", userSharedPreferences["contact"])

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " , Toast.LENGTH_LONG).show()
            Log.d("asa", "Error in payment" + e.message)
            e.printStackTrace()
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try {
            Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("asa", "Exception in onPaymentSuccess", e)
        }
        finally {
            finish()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try {
            Toast.makeText(this, "Payment Successful $razorpayPaymentId", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("asa", "Exception in onPaymentSuccess", e)
        }
    }

}