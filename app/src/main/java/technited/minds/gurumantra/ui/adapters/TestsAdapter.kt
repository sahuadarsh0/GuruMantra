package technited.minds.gurumantra.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajat.pdfviewer.PdfViewerActivity
import technited.minds.gurumantra.databinding.ItemListTestsBinding
import technited.minds.gurumantra.model.Ts
import technited.minds.gurumantra.utils.Constants

class TestsAdapter(private val onItemClicked: (Ts) -> Unit) : ListAdapter<Ts, TestsAdapter
.TestsViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder =
        TestsViewHolder(
            ItemListTestsBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Ts>() {
            override fun areItemsTheSame(oldItem: Ts, newItem: Ts): Boolean =
                oldItem.tId == newItem.tId


            override fun areContentsTheSame(oldItem: Ts, newItem: Ts): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class TestsViewHolder(private val binding: ItemListTestsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ts: Ts, onItemClicked: (Ts) -> Unit) {
            Log.d("asa", "bind: " + ts.tId )
            binding.apply {
                test = ts
                if (ts.ptQuestions != null) {
                    cardView2.visibility = VISIBLE
                }
                cardView1.setOnClickListener {
                    if (ts.ptQuestions != null) {
                        it.context.startActivity(
                            PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                                it.context,
                                Constants.URL.toString() + ts.ptQuestions,                                // PDF URL in String format
                                ts.tName,                        // PDF Name/Title in String format
                                "",                  // If nothing specific, Put "" it will save to Downloads
                                enableDownload = false                    // This param is true by defualt.
                            )
                        )
                    } else
                        onItemClicked(ts)
                }
                cardView2.setOnClickListener { onItemClicked(ts) }
            }
        }
    }

}