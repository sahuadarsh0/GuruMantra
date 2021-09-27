package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListMeetingsBinding
import technited.minds.gurumantra.model.MeetingDetailsItem

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
        fun bind(meetingDetailsItem: MeetingDetailsItem, onItemClicked: (MeetingDetailsItem) -> Unit) {
            binding.details = meetingDetailsItem
            binding.root.setOnClickListener { onItemClicked(meetingDetailsItem) }
            binding.joinButton.setOnClickListener{

            }
        }
    }
}