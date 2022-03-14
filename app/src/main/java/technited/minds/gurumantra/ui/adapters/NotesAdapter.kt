package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListNotesBinding
import technited.minds.gurumantra.model.Note

class NotesAdapter(private val onItemClicked: (Note) -> Unit) : ListAdapter<Note, NotesAdapter
.GalleryViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(
            ItemListNotesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.noteId == newItem.noteId


            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class GalleryViewHolder(private val binding: ItemListNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, onItemClicked: (Note) -> Unit) {
            binding.note = note
            binding.root.setOnClickListener { onItemClicked(note) }
        }
    }
}