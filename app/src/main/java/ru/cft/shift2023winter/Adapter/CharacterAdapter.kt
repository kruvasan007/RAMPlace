package ru.cft.shift2023winter.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.cft.shift2023winter.Model.Character
import ru.cft.shift2023winter.R

class CharacterAdapter(
    private val context: Context,
    private val characterModelList: List<Character>
) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)

        fun bind(list: Character) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = characterModelList[position]
        holder.bind(character)

        Picasso.get().load(characterModelList[position].image).into(holder.image)
        holder.name.text = characterModelList[position].name
    }

    override fun getItemCount() = characterModelList.size

}