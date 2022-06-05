package technited.minds.gurumantra.ui.packages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.utils.Resource
import com.razorpay.Checkout
import technited.minds.gurumantra.databinding.FragmentPackagesBinding
import technited.minds.gurumantra.model.Pck
import technited.minds.gurumantra.ui.payment.PaymentPage
import technited.minds.gurumantra.ui.adapters.PackagesAdapter
import technited.minds.gurumantra.ui.payment.PaymentViewModel
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class PackagesFragment : Fragment() {

    private var _binding: FragmentPackagesBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private val packagesViewModel: PackagesViewModel by viewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()
    private val packagesAdapter = PackagesAdapter(this::onItemClicked)
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPackagesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        return root
    }


    private fun setupRecyclerView() {
        binding.packageList.adapter = packagesAdapter
        Checkout.preload(context)
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.packageList.visibility = View.GONE

        packagesViewModel.packages.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.packageList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val packages = it.data

                    if (packages != null) {

                        packagesAdapter.submitList(packages.pcks)
                        binding.animationView.visibility = View.GONE
                        binding.packageList.visibility = View.VISIBLE

                    }

                }
                Resource.Status.ERROR -> {
                    MaterialDialog(requireContext()).show {
                        title(text = "API ERROR")
                        message(text = it.message)
                        cornerRadius(16f)
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                    }
                    binding.animationView.visibility = View.GONE
                    binding.packageList.visibility = View.VISIBLE

                }

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(pck: Pck) {
        val i = Intent(activity, PaymentPage::class.java)
        i.putExtra("price", pck.pckPrice.toString())
        i.putExtra("title", pck.pckName)

        paymentViewModel.payment.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
//                    binding.animationView.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    val payment = it.data
                    if (payment != null) {
                        if (payment.status == 2)
                            Toast.makeText(requireContext(), payment.message, Toast.LENGTH_SHORT).show()
                        else {
                            i.putExtra("orderId", payment.data.orderId)
                            i.putExtra("type", "package")
                            Log.d("Asa", "before PaymentPage :  ${pck.pckPrice}")
                            Log.d("Asa", "before PaymentPage : ${pck.pckName}")
                            Log.d("Asa", "before PaymentPage : ${payment.data.orderId}")
                            startActivity(i)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    MaterialDialog(requireContext()).show {
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
        val dialog: MaterialDialog =  MaterialDialog(requireContext()).show {
            title(text = "Apply Coupons ?")
            message(text = "Enter coupon code")
            cornerRadius(16f)
            input(allowEmpty = true) { dialog, text ->
//                val coupon: EditText = dialog.getInputField()
                paymentViewModel.getPaymentData(userSharedPreferences["id"]!!, pck.pckId.toString(), "package",text.toString())


            }
            positiveButton(text = "OK") { dialog ->
                dialog.dismiss()
            }
            negativeButton(text = "No Coupons") { dialog ->

                paymentViewModel.getPaymentData(userSharedPreferences["id"]!!, pck.pckId.toString(), "package")

            }
        }
    }
}