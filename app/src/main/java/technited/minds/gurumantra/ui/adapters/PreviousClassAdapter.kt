package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListPreviousBinding
import technited.minds.gurumantra.model.PreviousClassItem

class PreviousClassAdapter(private val onItemClicked: (PreviousClassItem) -> Unit) :
    ListAdapter<PreviousClassItem, PreviousClassAdapter
    .PreviousClassViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousClassViewHolder =
        PreviousClassViewHolder(
            ItemListPreviousBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<PreviousClassItem>() {
            override fun areItemsTheSame(oldItem: PreviousClassItem, newItem: PreviousClassItem): Boolean =
                oldItem.pcId == newItem.pcId


            override fun areContentsTheSame(oldItem: PreviousClassItem, newItem: PreviousClassItem): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: PreviousClassViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class PreviousClassViewHolder(private val binding: ItemListPreviousBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(previousClassItem: PreviousClassItem, onItemClicked: (PreviousClassItem) -> Unit) {
            binding.prev = previousClassItem
            binding.root.setOnClickListener { onItemClicked(previousClassItem) }
        }
    }
}