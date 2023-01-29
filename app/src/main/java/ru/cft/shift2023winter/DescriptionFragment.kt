package ru.cft.shift2023winter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import ru.cft.shift2023winter.Data.CharacterViewModel
import ru.cft.shift2023winter.databinding.FragmentDescriptionBinding


class DescriptionFragment : Fragment() {
    val args: DescriptionFragmentArgs by navArgs()

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private var countCharacter: Int? = 0

    private val viewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionDescriptionFragmentToMainScreen()
            )
        }
        binding.nextCharacter.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionDescriptionFragmentSelf(args.characterId+1)
            )
        }
        binding.prevCharacter.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionDescriptionFragmentSelf(args.characterId-1)
            )
        }
        getCharacterData()
        getCountCharacter()
    }

    private fun getCharacterData() {
        val character = viewModel.getCharacterById(args.characterId)!!
        binding.name.text = character.name
        Picasso.get().load(character.image).into(binding.image)
    }

    private fun getCountCharacter(){
        countCharacter = viewModel.getCountCharacter()?.minus(1)
        if(args.characterId == countCharacter!!)
            binding.nextCharacter.visibility = View.INVISIBLE
        else
            binding.nextCharacter.isClickable = true
        if(args.characterId == 0)
            binding.prevCharacter.visibility = View.INVISIBLE
        else
            binding.nextCharacter.isClickable = true
    }
}