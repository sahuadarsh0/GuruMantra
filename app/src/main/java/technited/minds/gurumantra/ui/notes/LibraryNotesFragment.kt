package technited.minds.gurumantra.ui.notes

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.rajat.pdfviewer.PdfViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentLibraryNotesBinding
import technited.minds.gurumantra.databinding.FragmentNotesBinding
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.Gal
import technited.minds.gurumantra.model.Note
import technited.minds.gurumantra.ui.adapters.NotesAdapter
import technited.minds.gurumantra.utils.Constants
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class LibraryNotesFragment : Fragment() {

    private var _binding: FragmentLibraryNotesBinding? = null
    private val notesViewModel: NotesViewModel by viewModels()
    private val sampleNotesAdapter = NotesAdapter(this::onItemClicked)
    private val caNotesAdapter = NotesAdapter(this::onItemClicked)
    private val ncertNotesAdapter = NotesAdapter(this::onItemClicked)
    private val allNotesAdapter = NotesAdapter(this::onItemClicked)
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.viewAll1.setOnClickListener {
            openNotes("Sample")
        }
        binding.viewAll2.setOnClickListener {
            openNotes("Ca")
        }
        binding.viewAll3.setOnClickListener {
            openNotes("Ncert")
        }
        binding.viewAll4.setOnClickListener {
            openNotes("All")
        }

        binding.filter.setOnClickListener{
            openNotes("All")
        }
        binding.searchNotes.setOnClickListener{
            openNotes("All")
        }
        setupRecyclerView()
        setupObservers()
        return root
    }


    private fun setupRecyclerView() {
        binding.notesList1.adapter = sampleNotesAdapter
        binding.notesList2.adapter = caNotesAdapter
        binding.notesList3.adapter = ncertNotesAdapter
        binding.notesList4.adapter = allNotesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.page.visibility = View.GONE

        notesViewModel.libraryNotes.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.page.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val getNotes = it.data

                    if (getNotes != null) {

                        sampleNotesAdapter.submitList(getNotes.smnts)
                        caNotesAdapter.submitList(getNotes.cas)
                        ncertNotesAdapter.submitList(getNotes.ncerts)
                        allNotesAdapter.submitList(getNotes.notes)

                        binding.animationView.visibility = View.GONE
                        binding.page.visibility = View.VISIBLE

                    }

                }
                Resource.Status.ERROR -> {
                    MaterialDialog(requireContext()).show {
                        title(text = "API ERROR")
                        message(text = it.message)
                        cornerRadius(16f)
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                    }
                    binding.animationView.visibility = View.GONE
                    binding.page.visibility = View.VISIBLE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(note: Note) {
        startActivity(
            PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                context,
                Constants.URL.toString() + note.notesPDF,                                // PDF URL in String format
                note.notesTitle,                        // PDF Name/Title in String format
                "",                  // If nothing specific, Put "" it will save to Downloads
                enableDownload = false                    // This param is true by defualt.
            )
        )
    }

    private fun openNotes(type: String) {
        findNavController().navigate(
            R.id.action_navigation_library_notes_to_navigation_notes,
            bundleOf(
                "type" to type
            )
        )
    }
}