package ru.cft.shift2023winter.Screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import ru.cft.shift2023winter.Adapter.CharacterAdapter
import ru.cft.shift2023winter.Data.CharacterViewModel
import ru.cft.shift2023winter.DataBase.CharacterDto
import ru.cft.shift2023winter.databinding.LikesFragmentBinding

class LikesFragment : Fragment() {
    private val viewModel: CharacterViewModel by activityViewModels()
    private var _binding: LikesFragmentBinding? = null
    private lateinit var adapter: CharacterAdapter
    private var recyclerView: RecyclerView? = null
    private var characterList: List<CharacterDto> = arrayListOf()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LikesFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CharacterAdapter(listener, characterList.map { toCharacter(it) }, true)
        recyclerView!!.adapter = adapter
        fragmentTextUpdateObserver()
    }

    private fun fragmentTextUpdateObserver() {
        viewModel.getLikes().observe(viewLifecycleOwner) { data ->
            characterList = data
            adapter.updateList(characterList.map { toCharacter(it) })
        }
    }

    private fun toCharacter(char: CharacterDto): ru.cft.shift2023winter.Model.Character {
        var data = ru.cft.shift2023winter.Model.Character()
        data.id = char.id
        data.name = char.name
        data.status = char.status
        data.image = char.image
        return data
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
            LikesFragmentDirections.actionLikesFragmentToDescriptionFragment(id), extras
        )
    }
}