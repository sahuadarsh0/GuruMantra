package technited.minds.gurumantra.ui.packages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
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
    private val paymentsViewModel: PaymentViewModel by viewModels()
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

        packagesViewModel.packages.observe(viewLifecycleOwner, {
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
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(pck: Pck) {
        val i = Intent(activity, PaymentPage::class.java)
        i.putExtra("price", pck.pckPrice.toString())
        i.putExtra("title", pck.pckName)
        paymentsViewModel.getPaymentData(userSharedPreferences["id"]!!, pck.pckId.toString())
        paymentsViewModel.payment.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
//                    binding.animationView.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    val payment = it.data
                    if (payment != null) {
                        i.putExtra("orderId", payment.data.orderId)
                        i.putExtra("type", "purchase")
                        Log.d("Asa", "before PaymentPage :  ${pck.pckPrice}")
                        Log.d("Asa", "before PaymentPage : ${pck.pckName}")
                        Log.d("Asa", "before PaymentPage : ${payment.data.orderId}")
                        startActivity(i)
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
//                    binding.animationView.visibility = View.GONE

                }
            }
        })
    }
}