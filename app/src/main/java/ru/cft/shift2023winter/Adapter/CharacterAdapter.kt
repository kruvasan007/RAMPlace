package ru.cft.shift2023winter.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.cft.shift2023winter.Model.Character
import ru.cft.shift2023winter.R

class CharacterAdapter(
    private val onClickListener: OnClickListener,
    private var characterModelList: List<Character>,
    private val minimalising: Boolean
) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        fun bind(list: Character) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedView: View = when (minimalising) {
            true -> layoutInflater.inflate(R.layout.character_minimalism_item, parent, false)
            else -> layoutInflater.inflate(R.layout.character_item, parent, false)
        }
        return MyViewHolder(inflatedView)
    }

    fun updateList(list: List<Character>) {
        characterModelList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = characterModelList[position]
        holder.bind(character)
        holder.image.transitionName = position.toString()
        holder.image.setOnClickListener {
            onClickListener.onClick(character.id!!, holder.image)
        }
        Picasso.get().load(characterModelList[position].image).transform(CropCircleTransformation())
            .into(holder.image)
        holder.name.text = characterModelList[position].name
    }

    override fun getItemCount() = characterModelList.size

    class OnClickListener(val clickListener: (id: Int, image: ImageView) -> Unit) {
        fun onClick(id: Int, image: ImageView) =
            clickListener(id, image)
    }
}