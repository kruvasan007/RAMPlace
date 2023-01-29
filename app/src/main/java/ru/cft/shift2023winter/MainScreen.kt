package ru.cft.shift2023winter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.cft.shift2023winter.Adapter.CharacterAdapter
import ru.cft.shift2023winter.Data.CharacterViewModel
import ru.cft.shift2023winter.databinding.FragmentMainScreenBinding

class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private lateinit var adapter: CharacterAdapter
    private var recyclerView: RecyclerView? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.characterList
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTextUpdateObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fragmentTextUpdateObserver() {
        viewModel.getCharacterList().observe(viewLifecycleOwner) { characterList ->
            binding.progressBar.visibility = View.INVISIBLE
            adapter = CharacterAdapter(listener, characterList)
            recyclerView!!.adapter = adapter
        }
    }

    private val listener = CharacterAdapter.OnClickListener { id ->
        findNavController().navigate(
            MainScreenDirections.actionMainScreenToDescriptionFragment(id)
        )
    }
}