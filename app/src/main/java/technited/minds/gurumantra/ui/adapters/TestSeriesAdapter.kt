package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListTestSeriesBinding
import technited.minds.gurumantra.model.TestSeriesItem

class TestSeriesAdapter(private val onItemClicked: (TestSeriesItem) -> Unit) : ListAdapter<TestSeriesItem, TestSeriesAdapter
.BatchesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchesViewHolder =
        BatchesViewHolder(
            ItemListTestSeriesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<TestSeriesItem>() {
            override fun areItemsTheSame(oldItem: TestSeriesItem, newItem: TestSeriesItem): Boolean =
                oldItem.tsId == newItem.tsId


            override fun areContentsTheSame(oldItem: TestSeriesItem, newItem: TestSeriesItem): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: BatchesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class BatchesViewHolder(private val binding: ItemListTestSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(TestSeriesItem: TestSeriesItem, onItemClicked: (TestSeriesItem) -> Unit) {
            binding.testSeries = TestSeriesItem
            binding.root.setOnClickListener { onItemClicked(TestSeriesItem) }
        }
    }
}