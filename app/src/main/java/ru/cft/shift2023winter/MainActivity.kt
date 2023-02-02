package ru.cft.shift2023winter

import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.room.Room
import ru.cft.shift2023winter.DataBase.AppDatabase
import ru.cft.shift2023winter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val navController get() = findNavController(R.id.fragmentContainer)
    private lateinit var binding: ActivityMainBinding
    private var activatedColor: Int = 0
    private var unactivatedColor: Int = 0
    private lateinit var lastButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        lastButton = binding.homeButton
        activatedColor = getColor(R.color.blue)
        unactivatedColor = getColor(R.color.black)
        binding.homeButton.setColorFilter(activatedColor)

        binding.homeButton.setOnClickListener {
            val anim =
                AnimationUtils.loadAnimation(
                    binding.homeButton.context,
                    R.anim.slide_up
                )
            binding.homeButton.startAnimation(anim)
            lastButton.setColorFilter(unactivatedColor)
            binding.homeButton.setColorFilter(activatedColor)
            lastButton = binding.homeButton
            navController.navigate(R.id.mainScreen)
        }

        binding.locationButton.setOnClickListener {
            val anim =
                AnimationUtils.loadAnimation(
                    binding.locationButton.context,
                    R.anim.slide_up
                )
            binding.locationButton.startAnimation(anim)
            lastButton.setColorFilter(unactivatedColor)
            binding.locationButton.setColorFilter(activatedColor)
            lastButton = binding.locationButton
            navController.navigate(R.id.locationFragment)
        }

        binding.episodesButton.setOnClickListener {
            val anim =
                AnimationUtils.loadAnimation(
                    binding.episodesButton.context,
                    R.anim.slide_up
                )
            binding.episodesButton.startAnimation(anim)
            lastButton.setColorFilter(unactivatedColor)
            binding.episodesButton.setColorFilter(activatedColor)
            lastButton = binding.episodesButton
            navController.navigate(R.id.likesFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}