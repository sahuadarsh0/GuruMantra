package technited.minds.gurumantra.ui.gallery

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentGalleryBinding
import technited.minds.gurumantra.model.Gal
import technited.minds.gurumantra.ui.adapters.GalleryAdapter
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.ui.adapters.ImageZoomAdapter


@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val galleryViewModel: GalleryViewModel by viewModels()
    private val galleryAdapter = GalleryAdapter(this::onItemClicked)
    private val zoomAdapter = ImageZoomAdapter()
    private val binding get() = _binding!!
    private lateinit var zoomImageDialog: Dialog
    private lateinit var gallery: List<Gal>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        galleryViewModel.gallery.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.galleryList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    gallery = it.data!!
                    galleryAdapter.submitList(gallery)
                    zoomAdapter.submitList(gallery)
                    binding.animationView.visibility = View.GONE
                    binding.galleryList.visibility = View.VISIBLE

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
                    binding.galleryList.visibility = View.VISIBLE

                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(gal: Gal) {
        val galleryRecyclerView: RecyclerView = this.zoomImageDialog.findViewById(R.id.recycler_zoom_image)
        galleryRecyclerView.adapter = zoomAdapter
        galleryRecyclerView.layoutManager?.scrollToPosition(gallery.indexOf(gal))
        zoomImageDialog.show()
    }
}
