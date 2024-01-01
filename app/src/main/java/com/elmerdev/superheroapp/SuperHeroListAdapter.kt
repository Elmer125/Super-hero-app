package com.elmerdev.superheroapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SuperHeroListAdapter(
  private var superHeroesList: List<SuperHeroItemResponse> = emptyList(),
  private val onItemSelected: (String) -> Unit
) :
  RecyclerView.Adapter<SuperHeroListViewHolder>() {

  fun updateList(superHeroesList: List<SuperHeroItemResponse>) {
    this.superHeroesList = superHeroesList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroListViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
    return SuperHeroListViewHolder(view)
  }

  override fun getItemCount(): Int {
    return superHeroesList.size
  }

  override fun onBindViewHolder(holder: SuperHeroListViewHolder, position: Int) {
    holder.render(superHeroesList[position], onItemSelected)
  }

}