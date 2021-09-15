package com.egaragul.task_4_room_and_cursor.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.egaragul.task_4_room_and_cursor.R
import com.egaragul.task_4_room_and_cursor.databinding.ItemAnimalBinding
import com.egaragul.task_4_room_and_cursor.domain.data.Animal

class AnimalViewHolder(private val binding : ItemAnimalBinding, private val callback : (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent : ViewGroup, callback : (Int) -> Unit) : AnimalViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_animal, parent, false)
            val binding = ItemAnimalBinding.bind(view)

            return AnimalViewHolder(binding, callback)
        }
    }

    fun bind(animal: Animal) {
        with(binding) {
            tvName.text = animal.name
            tvAge.text = animal.age.toString()
            tvBreed.text = animal.breed

            root.setOnClickListener {
                callback(animal._id)
            }
        }

    }
}