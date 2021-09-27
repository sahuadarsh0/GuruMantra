package technited.minds.gurumantra.ui.batch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.databinding.FragmentBatchDetailsBinding
import technited.minds.gurumantra.model.MeetingDetailsItem
import technited.minds.gurumantra.ui.adapters.MeetingsAdapter
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class BatchDetails : Fragment() {

    private val batchDetailsViewModel: BatchDetailsViewModel by viewModels()
    private var _binding: FragmentBatchDetailsBinding? = null
    private val meetingsAdapter = MeetingsAdapter(this::onItemClicked)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatchDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        arguments?.getString("id")?.let { batchDetailsViewModel.getBatchDetails(it) }
        arguments?.getString("id")?.let { batchDetailsViewModel.getMeetings(it) }
        Toast.makeText(requireContext(), arguments?.getString("id"), Toast.LENGTH_SHORT).show()

        return root
    }

    private fun setupRecyclerView() {
        binding.meetingsList.adapter = meetingsAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        binding.meetingsList.visibility = GONE

        batchDetailsViewModel.meetings.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE
                    binding.meetingsList.visibility = GONE

                }
                Resource.Status.SUCCESS -> {
                    val meetings = it.data

                    if (meetings != null) {

                        meetingsAdapter.submitList(meetings.details)
                        binding.animationView.visibility = GONE
                        binding.meetingsList.visibility = VISIBLE

                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = GONE
                    binding.meetingsList.visibility = VISIBLE
                }

            }
        })
        batchDetailsViewModel.batchDetails.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val details = it.data

                    if (details != null) {

                        binding.details = details
                        binding.animationView.visibility = GONE

                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = GONE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(meetingDetailsItem: MeetingDetailsItem) {
//        userSharedPreferences["member_id_temp"] = batchDetailsItem.memberId
//        findNavController().navigate(R.id.action_navigation_home_to_navigation_account)
    }
}