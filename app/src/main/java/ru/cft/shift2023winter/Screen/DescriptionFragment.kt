package ru.cft.shift2023winter.Screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import ru.cft.shift2023winter.DataBase.CharacterDao
import ru.cft.shift2023winter.R
import ru.cft.shift2023winter.databinding.FragmentDescriptionBinding


class DescriptionFragment : Fragment() {
    val args: DescriptionFragmentArgs by navArgs()
    private var characterId: Int = 0
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var character: ru.cft.shift2023winter.Model.Character
    private var countCharacter: Int? = 0

    private val viewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root
        characterId = args.characterId - 1
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
            findNavController().navigateUp()
        }
        binding.nextCharacter.setOnClickListener {
            val bounce: Animation =
                AnimationUtils.loadAnimation(
                    binding.nextCharacter.context,
                    R.anim.slide_up
                )
            binding.nextCharacter.startAnimation(bounce)
            characterId += 1
            loadData()
        }
        binding.prevCharacter.setOnClickListener {
            val bounce: Animation =
                AnimationUtils.loadAnimation(
                    binding.prevCharacter.context,
                    R.anim.slide_up
                )
            binding.prevCharacter.startAnimation(bounce)
            characterId -= 1
            loadData()
        }
        binding.likeButton.setOnClickListener {
            if (character.liked!!) {
                binding.likeButton.setImageDrawable(requireContext().getDrawable(R.drawable.like_button))
                viewModel.deleteLike(character)
                character.liked = false
            } else {
                binding.likeButton.setImageDrawable(requireContext().getDrawable(R.drawable.activated_like_button))
                viewModel.setLike(character)
                character.liked = true
            }
        }
        loadData()
    }

    private fun loadData() {
        getCharacterData()
        getCountCharacter()
    }

    private fun getCharacterData() {
        character = viewModel.getCharacterById(characterId)!!
        character.liked = viewModel.findInsertionLikes(character)
        if (character.liked!!) {
            binding.likeButton.setImageDrawable(requireContext().getDrawable(R.drawable.activated_like_button))
        } else{
            binding.likeButton.setImageDrawable(requireContext().getDrawable(R.drawable.like_button))
        }
        Picasso.get().load(character.image).transform(CropCircleTransformation())
            .into(binding.image)
        binding.name.text = character.name
        binding.location.typeWrite(this, "location: " + character.location!!.name, 33L)
        binding.status.typeWrite(this, "status: " + character.status, 44L)
        binding.origin.typeWrite(this, "origin: " + character.origin!!.name, 33L)
        binding.gender.typeWrite(this, "gender: " + character.gender, 50L)
    }

    private fun getCountCharacter() {
        countCharacter = viewModel.getCountCharacter()?.minus(1)
        if (characterId == countCharacter!!)
            binding.nextCharacter.visibility = View.INVISIBLE
        else
            binding.nextCharacter.isClickable = true
        if (characterId == 0)
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