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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.cft.shift2023winter.Adapter.CharacterAdapter
import ru.cft.shift2023winter.Adapter.LocationAdapter
import ru.cft.shift2023winter.Data.CharacterViewModel
import ru.cft.shift2023winter.Data.LocationViewModel
import ru.cft.shift2023winter.Model.Character
import ru.cft.shift2023winter.Model.Locations
import ru.cft.shift2023winter.databinding.FragmentMainScreenBinding

class LocationFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private var adapter: LocationAdapter? = null
    private var recyclerView: RecyclerView? = null
    private val binding get() = _binding!!
    private var characterList: ArrayList<Character> = arrayListOf()
    private var locationsList: ArrayList<Locations> = arrayListOf()

    private val viewModelLocation: LocationViewModel by activityViewModels()
    private val viewModelCharacter: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.characterList
        recyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = LocationAdapter(locationsList, characterList)
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
        return view
    }

    fun filter(text: String) {
        val temp = arrayListOf<Locations>()
        for (location in locationsList) {
            if (location.name.contains(text)) {
                temp.add(location)
            }
        }
        adapter?.updateLocationList(temp)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTextUpdateObserver()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fragmentTextUpdateObserver() {
        viewModelCharacter.getCharacterList().observe(viewLifecycleOwner) { data ->
            characterList.addAll(data)
            println(characterList)
            adapter?.notifyDataSetChanged()
        }
        viewModelLocation.getLocationList().observe(viewLifecycleOwner) { data ->
            binding.loadingBar.visibility = View.INVISIBLE
            locationsList.addAll(data)
            adapter!!.notifyDataSetChanged()
        }
    }
}