package technited.minds.gurumantra.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListCourseBinding
import technited.minds.gurumantra.databinding.ItemListPackageBinding
import technited.minds.gurumantra.databinding.ItemListPostalCourseBinding
import technited.minds.gurumantra.databinding.ItemListPostalOrderBinding
import technited.minds.gurumantra.model.Course
import technited.minds.gurumantra.model.Pck
import technited.minds.gurumantra.model.PCOrder
import technited.minds.gurumantra.model.PreviousClassItem

class PostalOrdersAdapter : ListAdapter<PCOrder, PostalOrdersAdapter
.CoursesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder =
        CoursesViewHolder(
            ItemListPostalOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<PCOrder>() {
            override fun areItemsTheSame(oldItem: PCOrder, newItem: PCOrder): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: PCOrder, newItem: PCOrder): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class CoursesViewHolder(private val binding: ItemListPostalOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(PCOrder: PCOrder) {
            binding.order = PCOrder
        }
    }
}