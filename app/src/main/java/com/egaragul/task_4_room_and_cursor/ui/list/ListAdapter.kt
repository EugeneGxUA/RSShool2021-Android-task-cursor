package com.egaragul.task_4_room_and_cursor.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.egaragul.task_4_room_and_cursor.domain.data.Animal

class ListAdapter(private val callback : (Int) -> Unit) : ListAdapter<Animal, AnimalViewHolder>(comparator) {

    private companion object {
        val comparator = object : DiffUtil.ItemCallback<Animal>() {
            override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem.name == newItem.name && oldItem.age == newItem.age
            }

            override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder.create(parent, callback)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}