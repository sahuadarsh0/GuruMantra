package technited.minds.gurumantra.ui.coupons

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.databinding.FragmentCouponsBinding
import technited.minds.gurumantra.model.Coupon
import technited.minds.gurumantra.ui.adapters.CouponsAdapter
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class CouponsFragment : Fragment() {

    private var _binding: FragmentCouponsBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private val couponsViewModel: CouponsViewModel by viewModels()
    private val couponsAdapter = CouponsAdapter(this::onItemClicked)
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCouponsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        couponsViewModel.getCoupons(userSharedPreferences["id"]!!)
        setupRecyclerView()
        setupObservers()
        return root
    }


    private fun setupRecyclerView() {
        binding.couponsList.adapter = couponsAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.couponsList.visibility = View.GONE

        couponsViewModel.coupons.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.couponsList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val coupons = it.data

                    if (coupons != null) {

                        couponsAdapter.submitList(coupons.coupons)
                        binding.animationView.visibility = View.GONE
                        binding.couponsList.visibility = View.VISIBLE

                        if (coupons.coupons.isNullOrEmpty()) {
                            binding.noCoupon.visibility = View.VISIBLE
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
                    binding.animationView.visibility = View.GONE
                    binding.couponsList.visibility = View.VISIBLE

                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(coupon: Coupon) {
//        val i = Intent(activity, PaymentPage::class.java)
//        i.putExtra("price", pck.pckPrice.toString())
//        i.putExtra("title", pck.pckName)
    }
}