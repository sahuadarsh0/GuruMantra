package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ItemZoomImageBinding
import technited.minds.gurumantra.model.Gal
import technited.minds.gurumantra.utils.Constants

class ImageZoomAdapter() : ListAdapter<Gal, ImageZoomAdapter
.GalleryViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(
            ItemZoomImageBinding
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
        holder.bind(getItem(position))


    inner class GalleryViewHolder(private val binding: ItemZoomImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gal: Gal) {
            val image = binding.image as PhotoView
            Glide
                .with(binding.image.context)
                .load(Constants.URL.toString() + gal.gImage)
                .placeholder(R.drawable.notebook)
                .into(image)
        }
    }
}