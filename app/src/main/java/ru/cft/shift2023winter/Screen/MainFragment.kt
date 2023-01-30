package ru.cft.shift2023winter.Screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import ru.cft.shift2023winter.Adapter.CharacterAdapter
import ru.cft.shift2023winter.Data.CharacterViewModel
import ru.cft.shift2023winter.Model.Character
import ru.cft.shift2023winter.databinding.FragmentMainScreenBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private lateinit var adapter: CharacterAdapter
    private var recyclerView: RecyclerView? = null
    private val binding get() = _binding!!
    private var characterList: List<Character> = arrayListOf()

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
        postponeEnterTransition()
        adapter = CharacterAdapter(listener, characterList, false)
        recyclerView!!.adapter = adapter
        binding.serchButton.setOnClickListener {
            filter(binding.searchField.text.toString())
        }
        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    fun filter(text: String) {
        val temp = arrayListOf<Character>()
        for (character in characterList) {
            if (character.name?.contains(text) == true) {
                temp.add(character)
            }
        }
        characterList = temp
        adapter.updateList(characterList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fragmentTextUpdateObserver() {
        viewModel.getCharacterList().observe(viewLifecycleOwner) { data ->
            binding.loadingBar.visibility = View.INVISIBLE
            characterList = data
            adapter.updateList(characterList)
        }
    }

    private val listener = CharacterAdapter.OnClickListener { id, image ->
        exitTransition = MaterialElevationScale(false).apply {
            duration = 250
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 250
        }
        val extras = FragmentNavigatorExtras(
            image to id.toString()
        )
        findNavController().navigate(
            MainFragmentDirections.actionMainScreenToDescriptionFragment(id), extras
        )
    }
}