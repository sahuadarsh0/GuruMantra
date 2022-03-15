package technited.minds.gurumantra.ui.notes

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.rajat.pdfviewer.PdfViewerActivity
import com.skydoves.powerspinner.PowerSpinnerView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentNotesBinding
import technited.minds.gurumantra.model.*
import technited.minds.gurumantra.ui.CategoryViewModel
import technited.minds.gurumantra.ui.adapters.NotesAdapter
import technited.minds.gurumantra.utils.Constants
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val notesViewModel: NotesViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val notesAdapter = NotesAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var notes: List<Note>
    private lateinit var filterDialog: Dialog
    private lateinit var filterCatView: PowerSpinnerView
    private lateinit var filterSubCatView: PowerSpinnerView
    private lateinit var apply: Button
    private lateinit var cts: List<Ct>
    private lateinit var scts: List<Sct>
    private var cId = ""
    private var scId = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.getString("type")?.let { notesViewModel.getNotes(it) }



        setupRecyclerView()
        setupObservers()
        setupFilter()

        binding.searchNotes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    setupSearch(s.toString())
                }
            }
        })

        binding.filter.setOnClickListener {
            filterDialog.show()
            categoryViewModel.category.observe(viewLifecycleOwner, {
                if (it.data != null) {
                    cts = it.data.cts
                    val names = arrayListOf<String>()
                    cts.forEach { names.add(it.cId.toString() + " " + it.cName) }
                    filterCatView.setItems(names)

                }
            })
        }

        filterCatView.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            cId = cts[newIndex].cId.toString()
            categoryViewModel.getSubCategory(cId)
        }
        filterSubCatView.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            scId = scts[newIndex].scId.toString()
        }
        categoryViewModel.subCategory.observe(viewLifecycleOwner, {
            if (it.data != null) {
                scts = it.data.scts
                val names = arrayListOf<String>()
                scts.forEach { names.add(it.scId.toString() + " " + it.scName) }
                filterSubCatView.setItems(names)
            }
        })
        apply.setOnClickListener {
            notesViewModel.filterNotes(cId, scId)
            filterDialog.dismiss()
        }

        return root
    }

    fun setupSearch(strTyped: String) {
        val filteredList = arrayListOf<Note>()

        for (note in notes) {
            if (note.notesTitle.contains(strTyped)) {
                filteredList.add(note)
            }
            else if (note.notesTitle.lowercase().contains(strTyped.lowercase())) {
                filteredList.add(note)
            }
        }
        notesAdapter.submitList(filteredList)
    }

    private fun setupFilter() {
        filterDialog = Dialog(requireActivity())
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog.setContentView(R.layout.dialog_filter_category)
        val window: Window? = filterDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawableResource(R.color.transparent)
        filterDialog.setCancelable(true)
        filterCatView = this.filterDialog.findViewById(R.id.category)
        filterSubCatView = this.filterDialog.findViewById(R.id.sub_category)
        apply = this.filterDialog.findViewById(R.id.apply)
    }

    private fun setupRecyclerView() {
        binding.notesList.adapter = notesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.notesList.visibility = View.GONE

        notesViewModel.notes.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.notesList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val getNotes = it.data

                    if (getNotes != null) {
                        notes = getNotes.notes
                        notesAdapter.submitList(getNotes.notes)
                        if (getNotes.nts != null)
                            binding.title.text = getNotes.nts
                        binding.animationView.visibility = View.GONE
                        binding.notesList.visibility = View.VISIBLE

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
                    binding.notesList.visibility = View.VISIBLE

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
}