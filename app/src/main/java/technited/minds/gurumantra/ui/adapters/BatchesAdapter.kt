package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListBatchesBinding
import technited.minds.gurumantra.model.BatchDetailsItem

class BatchesAdapter(private val onItemClicked: (BatchDetailsItem) -> Unit) : ListAdapter<BatchDetailsItem, BatchesAdapter
.BatchesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchesViewHolder =
        BatchesViewHolder(ItemListBatchesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<BatchDetailsItem>() {
            override fun areItemsTheSame(oldItem: BatchDetailsItem, newItem: BatchDetailsItem): Boolean =
                oldItem.batchId == newItem.batchId


            override fun areContentsTheSame(oldItem: BatchDetailsItem, newItem: BatchDetailsItem): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: BatchesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class BatchesViewHolder(private val binding: ItemListBatchesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(batchDetailsItem: BatchDetailsItem, onItemClicked: (BatchDetailsItem) -> Unit) {
            binding.batch = batchDetailsItem
            binding.root.setOnClickListener { onItemClicked(batchDetailsItem) }
        }
    }
}