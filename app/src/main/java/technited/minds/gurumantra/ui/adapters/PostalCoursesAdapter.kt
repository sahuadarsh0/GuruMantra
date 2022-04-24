package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListCourseBinding
import technited.minds.gurumantra.databinding.ItemListPackageBinding
import technited.minds.gurumantra.databinding.ItemListPostalCourseBinding
import technited.minds.gurumantra.model.Course
import technited.minds.gurumantra.model.Pck
import technited.minds.gurumantra.model.PostalCourseItem
import technited.minds.gurumantra.model.PreviousClassItem

class PostalCoursesAdapter(private val onItemClicked: (PostalCourseItem) -> Unit) : ListAdapter<PostalCourseItem, PostalCoursesAdapter
.CoursesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder =
        CoursesViewHolder(
            ItemListPostalCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<PostalCourseItem>() {
            override fun areItemsTheSame(oldItem: PostalCourseItem, newItem: PostalCourseItem): Boolean =
                oldItem.pcsId == newItem.pcsId


            override fun areContentsTheSame(oldItem: PostalCourseItem, newItem: PostalCourseItem): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class CoursesViewHolder(private val binding: ItemListPostalCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postalCourseItem: PostalCourseItem, onItemClicked: (PostalCourseItem) -> Unit) {
            binding.postal = postalCourseItem
            binding.buy.setOnClickListener { onItemClicked(postalCourseItem) }
        }
    }
}