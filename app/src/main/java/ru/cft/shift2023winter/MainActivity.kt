package ru.cft.shift2023winter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.cft.shift2023winter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val navController get() = findNavController(R.id.fragmentContainer)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        binding.homeButton.setOnClickListener {
            navController.navigate(R.id.mainScreen)
        }

        binding.locationButton.setOnClickListener {
            navController.navigate(R.id.locationFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}