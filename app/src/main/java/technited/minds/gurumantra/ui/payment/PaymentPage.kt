package technited.minds.gurumantra.ui.payment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import technited.minds.gurumantra.R
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class PaymentPage : AppCompatActivity(), PaymentResultListener {

    private lateinit var price: String
    private lateinit var title: String
    private lateinit var orderId: String
    private lateinit var type: String

    private val paymentsViewModel: PaymentViewModel by viewModels()

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        setContentView(R.layout.activity_payment_page)


        price = intent.getStringExtra("price").toString()
        title = intent.getStringExtra("title").toString()
        orderId = intent.getStringExtra("orderId").toString()
        type = intent.getStringExtra("type").toString()
        startPayment(price+"00")
    }

    private fun startPayment(amount: String) {
        val activity: Activity = this
        val co = Checkout()
        co.setImage(R.drawable.logo)

        try {
            val options = JSONObject()
            options.put("name", "Gurumantra.online")
            options.put("description", title)
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", amount)
            options.put("order_id", orderId)
            options.put("send_sms_hash", true)

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
            Toast.makeText(this, "Payment failed", Toast.LENGTH_LONG).show()
            Log.d("asa", "onPaymentError  $errorCode \n $response")
        } catch (e: Exception) {
            Log.e("asa", "Exception in onPaymentError  $errorCode \n $response", e)
        }
        finally {
            finish()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_LONG).show()
            Log.d("asa", "onPaymentSuccess $razorpayPaymentId")
            paymentsViewModel.purchase(userSharedPreferences["id"]!!,orderId,razorpayPaymentId!!,type)
        } catch (e: Exception) {
            Log.e("asa", "Exception in onPaymentSuccess", e)
        }
        finally {
            paymentsViewModel.purchase.observe(this) {
                when (it.status) {
                    Resource.Status.LOADING -> {

                    }
                    Resource.Status.SUCCESS -> {
                        val purchase = it.data

                        if (purchase != null) {
                            Log.d("asa", "onPaymentSuccess: $purchase")
//                            packagesAdapter.submitList(packages.pcks)

                        }

                    }
                    Resource.Status.ERROR -> {
                        MaterialDialog(this@PaymentPage).show {
                            title(text = "API ERROR")
                            message(text = it.message)
                            cornerRadius(16f)
                            positiveButton(text = "OK") { dialog ->
                                dialog.dismiss()
                            }
                        }
                    }

                }
            }
            finish()
        }
    }

}