package ru.cft.shift2023winter.Screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.cft.shift2023winter.Data.CharacterViewModel
import ru.cft.shift2023winter.R
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
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.image
            duration = 250
            scrimColor = Color.TRANSPARENT
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.transitionName = args.characterId.toString()

        binding.backButton.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionDescriptionFragmentToMainScreen()
            )
        }
        binding.nextCharacter.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionDescriptionFragmentSelf(args.characterId + 1)
            )
        }
        binding.prevCharacter.setOnClickListener {
            findNavController().navigate(
                DescriptionFragmentDirections.actionDescriptionFragmentSelf(args.characterId - 1)
            )
        }
        getCharacterData()
        getCountCharacter()
    }

    private fun getCharacterData() {
        val character = viewModel.getCharacterById(args.characterId)!!
        Picasso.get().load(character.image).transform(CropCircleTransformation())
            .into(binding.image)
        binding.name.text = character.name
        binding.location.typeWrite(this, "location: " +character.location!!.name, 33L)
        binding.status.typeWrite(this, "status: " + character.status, 44L)
        binding.origin.typeWrite(this, "origin: " +character.origin!!.name, 33L)
        binding.gender.typeWrite(this, "gender: " +character.gender, 50L)
    }

    private fun getCountCharacter() {
        countCharacter = viewModel.getCountCharacter()?.minus(1)
        if (args.characterId == countCharacter!!)
            binding.nextCharacter.visibility = View.INVISIBLE
        else
            binding.nextCharacter.isClickable = true
        if (args.characterId == 0)
            binding.prevCharacter.visibility = View.INVISIBLE
        else
            binding.nextCharacter.isClickable = true
    }

    fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long) {
        this@typeWrite.text = ""
        lifecycleOwner.lifecycleScope.launch {
            repeat(text.length) {
                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)
            }
        }
    }
}