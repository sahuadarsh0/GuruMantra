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
import technited.minds.gurumantra.databinding.FragmentPreviousClassBinding
import technited.minds.gurumantra.model.BatchDetailsItem
import technited.minds.gurumantra.model.PreviousClassItem
import technited.minds.gurumantra.model.PreviousClasses
import technited.minds.gurumantra.ui.adapters.BatchesAdapter
import technited.minds.gurumantra.ui.adapters.PreviousClassAdapter
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class PreviousClassFragment : Fragment() {

    private val liveClassViewModel: LiveClassViewModel by viewModels()
    private var _binding: FragmentPreviousClassBinding? = null
    private val previousClassAdapter = PreviousClassAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var id: String
    private var type = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviousClassBinding.inflate(inflater, container, false)
        val root: View = binding.root
        arguments?.getInt("type")?.let { type = it }
        arguments?.getString("id")?.let { id = it }
        liveClassViewModel.getPreviousClasses(id, type)
        setupRecyclerView()
        setupObservers()

        return root
    }

    private fun setupRecyclerView() {
        binding.prevList.adapter = previousClassAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        binding.prevList.visibility = GONE

        liveClassViewModel.previousClasses.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE
                    binding.prevList.visibility = GONE

                }
                Resource.Status.SUCCESS -> {
                    val classes = it.data

                    if (classes != null) {

                        previousClassAdapter.submitList(classes.previousClasses)
                        binding.animationView.visibility = GONE
                        binding.prevList.visibility = VISIBLE

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
                    binding.prevList.visibility = VISIBLE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(previousClassItem: PreviousClassItem) {
        val action =
            PreviousClassFragmentDirections.actionPreviousToPlayNComments(
                previousClassItem.pcId.toString(),
                previousClassItem.pcVideo
            )
        findNavController().navigate(action)
    }
}