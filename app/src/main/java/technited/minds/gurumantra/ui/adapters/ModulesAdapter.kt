package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListModulesBinding
import technited.minds.gurumantra.model.Module
import technited.minds.gurumantra.utils.SharedPrefs

class ModulesAdapter(private val onItemClicked: (Module) -> Unit) :
    ListAdapter<Module, ModulesAdapter
    .ModulesViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder =
        ModulesViewHolder(
            ItemListModulesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Module>() {
            override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean =
                oldItem.moduleId == newItem.moduleId


            override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class ModulesViewHolder(private val binding: ItemListModulesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Module: Module, onItemClicked: (Module) -> Unit) {
            binding.module = Module
            binding.root.setOnClickListener { onItemClicked(Module) }
        }
    }
}