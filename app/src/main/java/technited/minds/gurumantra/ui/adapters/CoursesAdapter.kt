package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListCourseBinding
import technited.minds.gurumantra.databinding.ItemListPackageBinding
import technited.minds.gurumantra.model.Course
import technited.minds.gurumantra.model.Pck

class CoursesAdapter(private val onItemClicked: (Course) -> Unit) : ListAdapter<Course, CoursesAdapter
.CoursesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder =
        CoursesViewHolder(
            ItemListCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
                oldItem.cId == newItem.cId


            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class CoursesViewHolder(private val binding: ItemListCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course, onItemClicked: (Course) -> Unit) {
            binding.course = course
            binding.root.setOnClickListener { onItemClicked(course) }
        }
    }
}