package technited.minds.gurumantra.ui.gallery

import android.app.Dialog
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
import technited.minds.gurumantra.utils.Constants

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val galleryAdapter = GalleryAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var zoomImageDialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        setupZoomImage()
        return root
    }

    private fun setupZoomImage() {
        zoomImageDialog = Dialog(requireActivity())
        zoomImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        zoomImageDialog.setContentView(R.layout.dialog_zoom_image)
        val window: Window? = zoomImageDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawableResource(R.color.semi_transparent)
        zoomImageDialog.setCancelable(true)
    }

    private fun setupRecyclerView() {
        binding.galleryList.adapter = galleryAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.galleryList.visibility = View.GONE

        galleryViewModel.gallery.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.galleryList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val gallery = it.data

                    if (gallery != null) {

                        galleryAdapter.submitList(gallery)
                        binding.animationView.visibility = View.GONE
                        binding.galleryList.visibility = View.VISIBLE

                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    binding.galleryList.visibility = View.VISIBLE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(gal: Gal) {
        zoomImageDialog.show()
        val image = zoomImageDialog.findViewById(R.id.image) as PhotoView
        Glide
            .with(requireActivity())
            .load(Constants.URL.toString()+gal.gImage)
            .placeholder(R.drawable.notebook)
            .into(image)
    }
}