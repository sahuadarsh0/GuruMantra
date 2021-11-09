package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListBlogBinding
import technited.minds.gurumantra.databinding.ItemListTestSeriesBinding
import technited.minds.gurumantra.model.Blog

class BlogsAdapter(private val onItemClicked: (Blog) -> Unit) : ListAdapter<Blog, BlogsAdapter
.BatchesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchesViewHolder =
        BatchesViewHolder(
            ItemListBlogBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean =
                oldItem.blogId == newItem.blogId


            override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: BatchesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class BatchesViewHolder(private val binding: ItemListBlogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Blog: Blog, onItemClicked: (Blog) -> Unit) {
            binding.blog = Blog
            binding.root.setOnClickListener { onItemClicked(Blog) }
        }
    }
}