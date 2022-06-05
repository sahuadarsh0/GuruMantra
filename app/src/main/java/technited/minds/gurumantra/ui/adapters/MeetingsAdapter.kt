package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import technited.minds.gurumantra.databinding.ItemListMeetingsBinding
import technited.minds.gurumantra.model.MeetingDetailsItem
import technited.minds.gurumantra.ui.live.BatchDetailsDirections
import technited.minds.gurumantra.utils.SharedPrefs

class MeetingsAdapter(private val onItemClicked: (MeetingDetailsItem, String) -> Unit) :
    ListAdapter<MeetingDetailsItem, MeetingsAdapter
    .MeetingsViewHolder>(DIFFUTIL_CALLBACK) {

    private var previousExpandedPosition = -1
    private var mExpandedPosition = -1
    private lateinit var recyclerView: RecyclerView

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

    override fun onBindViewHolder(holder: MeetingsViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
        val isExpanded: Boolean = position == mExpandedPosition
        when {
            isExpanded -> {
                holder.desc.visibility = View.VISIBLE
                holder.more.visibility = View.INVISIBLE
                previousExpandedPosition = holder.bindingAdapterPosition
            }
            else -> {
                holder.desc.visibility = View.GONE
                holder.more.visibility = View.VISIBLE
            }
        }
        holder.more.isActivated = isExpanded
        holder.more.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            TransitionManager.beginDelayedTransition(recyclerView)
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class MeetingsViewHolder(private val binding: ItemListMeetingsBinding) : RecyclerView.ViewHolder(binding.root) {
        var userSharedPreferences: SharedPrefs = SharedPrefs(binding.root.context, "USER")
        val desc = binding.description
        val more = binding.more
        fun bind(meetingDetailsItem: MeetingDetailsItem, onItemClicked: (MeetingDetailsItem, String) -> Unit) {
            binding.details = meetingDetailsItem
            binding.button.setOnClickListener { onItemClicked(meetingDetailsItem, "join") }
            binding.previous.setOnClickListener { onItemClicked(meetingDetailsItem, "previous") }
            if (userSharedPreferences["type"].equals("Admin") || userSharedPreferences["type"].equals("Faculty"))
                binding.button.text = "Start"
            else
                binding.button.text = "Join"
        }
    }
}