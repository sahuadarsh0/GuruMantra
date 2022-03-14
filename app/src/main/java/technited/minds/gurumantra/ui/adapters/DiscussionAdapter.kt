package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListDiscussionBinding
import technited.minds.gurumantra.model.Dcs

class DiscussionAdapter(private val onItemClicked: (Dcs) -> Unit) : ListAdapter<Dcs, DiscussionAdapter
.BatchesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchesViewHolder =
        BatchesViewHolder(
            ItemListDiscussionBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Dcs>() {
            override fun areItemsTheSame(oldItem: Dcs, newItem: Dcs): Boolean =
                oldItem.dId == newItem.dId


            override fun areContentsTheSame(oldItem: Dcs, newItem: Dcs): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: BatchesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class BatchesViewHolder(private val binding: ItemListDiscussionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dcs: Dcs, onItemClicked: (Dcs) -> Unit) {
            binding.discussion = dcs
            binding.discussionAns.setOnClickListener { onItemClicked(dcs) }
        }
    }
}