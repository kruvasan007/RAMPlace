package ru.cft.shift2023winter.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import ru.cft.shift2023winter.Model.Character
import ru.cft.shift2023winter.Model.Locations
import ru.cft.shift2023winter.Screen.LikesFragmentDirections
import ru.cft.shift2023winter.Screen.LocationFragment


class LocationAdapter(
    private var locationsList: List<Locations>,
    private val characterDataList: List<Character>
) :
    RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {
    private var currentPosition = 0
    private var lastPosition = 0

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(ru.cft.shift2023winter.R.id.name)
        val backButton: ImageView =
            itemView.findViewById(ru.cft.shift2023winter.R.id.go_to_description)
        val expandablePart: ConstraintLayout =
            itemView.findViewById(ru.cft.shift2023winter.R.id.expandable_container)

        fun bind(list: Locations) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val imageView: View = LayoutInflater.from(parent.context)
            .inflate(ru.cft.shift2023winter.R.layout.location_details, parent, false)
        return MyViewHolder(imageView)
    }

    fun updateLocationList(list: List<Locations>) {
        locationsList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = locationsList[position]
        holder.bind(location)
        holder.name.text = locationsList[position].name
        holder.expandablePart.visibility = View.GONE

        if (currentPosition == position) {
            val demension: TextView =
                holder.itemView.findViewById(ru.cft.shift2023winter.R.id.demension)
            val type: TextView = holder.itemView.findViewById(ru.cft.shift2023winter.R.id.type)
            val characterList: RecyclerView =
                holder.itemView.findViewById(ru.cft.shift2023winter.R.id.character_list)
            demension.text = "dimension: " + location.dimension
            type.text = "type: " + location.type
            val charactersId = mutableSetOf<Int>()
            for (character in characterDataList) {
                if (character.location?.name == location.name)
                    charactersId.add(characterDataList.indexOf(character))
            }
            characterList.adapter =
                CharacterAdapter(listener, characterDataList.slice(charactersId), true)
            val slideDown: Animation =
                AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    ru.cft.shift2023winter.R.anim.anim
                )
            holder.expandablePart.visibility = View.VISIBLE
            holder.expandablePart.startAnimation(slideDown)
        }
        holder.backButton.setOnClickListener {
            currentPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = locationsList.size

    private val listener = CharacterAdapter.OnClickListener { id, image ->
    }
}
