package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListPackageBinding
import technited.minds.gurumantra.model.Pck

class PackagesAdapter(private val onItemClicked: (Pck) -> Unit) : ListAdapter<Pck, PackagesAdapter
.PackagesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesViewHolder =
        PackagesViewHolder(
            ItemListPackageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Pck>() {
            override fun areItemsTheSame(oldItem: Pck, newItem: Pck): Boolean =
                oldItem.pckId == newItem.pckId


            override fun areContentsTheSame(oldItem: Pck, newItem: Pck): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class PackagesViewHolder(private val binding: ItemListPackageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pck: Pck, onItemClicked: (Pck) -> Unit) {
            binding.packages = pck
            binding.root.setOnClickListener { onItemClicked(pck) }
        }
    }
}