package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ItemListMeetingsBinding
import technited.minds.gurumantra.model.MeetingDetailsItem
import technited.minds.gurumantra.ui.batch.BatchDetailsDirections
import technited.minds.gurumantra.utils.SharedPrefs

class MeetingsAdapter(private val onItemClicked: (MeetingDetailsItem) -> Unit) :
    ListAdapter<MeetingDetailsItem, MeetingsAdapter
    .MeetingsViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingsViewHolder =
        MeetingsViewHolder(
            ItemListMeetingsBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<MeetingDetailsItem>() {
            override fun areItemsTheSame(oldItem: MeetingDetailsItem, newItem: MeetingDetailsItem): Boolean =
                oldItem.batchId == newItem.batchId


            override fun areContentsTheSame(oldItem: MeetingDetailsItem, newItem: MeetingDetailsItem): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: MeetingsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class MeetingsViewHolder(private val binding: ItemListMeetingsBinding) : RecyclerView.ViewHolder(binding.root) {
        var userSharedPreferences: SharedPrefs = SharedPrefs(binding.root.context,"USER")
        fun bind(meetingDetailsItem: MeetingDetailsItem, onItemClicked: (MeetingDetailsItem) -> Unit) {
            binding.details = meetingDetailsItem
            binding.root.setOnClickListener { onItemClicked(meetingDetailsItem) }
            if (userSharedPreferences["type"].equals("Admin")||userSharedPreferences["type"].equals("Faculty"))
                binding.button.text = "Start"
            else
                binding.button.text = "Join"

            binding.button.setOnClickListener {
                val action = BatchDetailsDirections.actionBatchDetailsToZoomActivity(meetingDetailsItem.classId.toString())
                it.findNavController().navigate(action)
            }
        }
    }
}