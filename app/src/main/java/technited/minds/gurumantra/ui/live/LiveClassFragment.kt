package technited.minds.gurumantra.ui.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentLiveClassBinding
import technited.minds.gurumantra.model.BatchDetailsItem
import technited.minds.gurumantra.ui.adapters.BatchesAdapter
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class LiveClassFragment : Fragment() {

    private val liveClassViewModel: LiveClassViewModel by viewModels()
    private var _binding: FragmentLiveClassBinding? = null
    private val batchesAdapter = BatchesAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveClassBinding.inflate(inflater, container, false)
        val root: View = binding.root
        arguments?.getString("type")?.let { type = it }
        liveClassViewModel.getBatches(type)
        setupRecyclerView()
        setupObservers()
        return root
    }

    private fun setupRecyclerView() {
        binding.batchesList.adapter = batchesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        binding.batchesList.visibility = GONE

        liveClassViewModel.batches.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE
                    binding.batchesList.visibility = GONE

                }
                Resource.Status.SUCCESS -> {
                    val batches = it.data

                    if (batches != null) {

                        batchesAdapter.submitList(batches)
                        binding.animationView.visibility = GONE
                        binding.batchesList.visibility = VISIBLE

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
                    binding.animationView.visibility = GONE
                    binding.batchesList.visibility = VISIBLE

                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(batchDetailsItem: BatchDetailsItem) {
        findNavController().navigate(
            R.id.action_navigation_live_class_to_batchDetails,
            bundleOf(
                "id" to batchDetailsItem.batchId.toString(),
                "type" to batchDetailsItem.batchType)
        )
    }
}