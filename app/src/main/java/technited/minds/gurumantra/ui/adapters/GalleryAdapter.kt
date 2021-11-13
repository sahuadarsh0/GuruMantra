package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListGalleryBinding
import technited.minds.gurumantra.model.Gal

class GalleryAdapter(private val onItemClicked: (Gal) -> Unit) : ListAdapter<Gal, GalleryAdapter
.GalleryViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(
            ItemListGalleryBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Gal>() {
            override fun areItemsTheSame(oldItem: Gal, newItem: Gal): Boolean =
                oldItem.gId == newItem.gId


            override fun areContentsTheSame(oldItem: Gal, newItem: Gal): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class GalleryViewHolder(private val binding: ItemListGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Gal: Gal, onItemClicked: (Gal) -> Unit) {
            binding.gallery = Gal
            binding.root.setOnClickListener { onItemClicked(Gal) }
        }
    }
}