package technited.minds.gurumantra.ui.packages

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentGalleryBinding
import technited.minds.gurumantra.model.Gal
import technited.minds.gurumantra.ui.adapters.GalleryAdapter
import technited.minds.gurumantra.utils.Resource
import com.github.chrisbanes.photoview.PhotoView
import technited.minds.gurumantra.databinding.FragmentPackagesBinding
import technited.minds.gurumantra.model.Pck
import technited.minds.gurumantra.ui.PaymentPage
import technited.minds.gurumantra.ui.adapters.PackagesAdapter
import technited.minds.gurumantra.ui.gallery.GalleryViewModel
import technited.minds.gurumantra.ui.test.ExamActivity
import technited.minds.gurumantra.utils.Constants

@AndroidEntryPoint
class PackagesFragment : Fragment() {

    private var _binding: FragmentPackagesBinding? = null
    private val packagesViewModel: PackagesViewModel by viewModels()
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
        i.putExtra("id", pck.pckId.toString())
        i.putExtra("price", pck.pckPrice.toString())
        i.putExtra("title", pck.pckName)
        startActivity(i)
    }
}