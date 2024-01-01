package com.elmerdev.superheroapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.elmerdev.superheroapp.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperHeroListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val binding = ItemSuperheroBinding.bind(view)
  fun render(superHeroItemResponse: SuperHeroItemResponse, onItemSelected: (String) -> Unit) {
    binding.tvSuperHeroName.text = superHeroItemResponse.name
    Picasso.get().load(superHeroItemResponse.image.url).into(binding.ivSuperheroImage)
    binding.root.setOnClickListener { onItemSelected(superHeroItemResponse.id) }
  }
}