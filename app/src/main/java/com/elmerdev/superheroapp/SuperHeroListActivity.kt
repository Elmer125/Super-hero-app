package com.elmerdev.superheroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmerdev.superheroapp.DetailSuperheroActivity.Companion.EXTRA_ID
import com.elmerdev.superheroapp.databinding.ActivitySuperHeroListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class SuperHeroListActivity : AppCompatActivity() {

  // Get elements with binding
  private lateinit var binding: ActivitySuperHeroListBinding
  private lateinit var retrofit: Retrofit
  private lateinit var adapter: SuperHeroListAdapter
  //  private lateinit var searchView : SearchView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySuperHeroListBinding.inflate(layoutInflater)
    /*setContentView(R.layout.activity_super_hero_list)*/
    setContentView(binding.root)
    binding.searchView
    binding.rvSuperHeroList
    // Init retrofit
    retrofit = getRetrofit()
    initUI()
  }

  private fun initUI() {
    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        searchByName(query.orEmpty())
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }

    })
    adapter = SuperHeroListAdapter{ navigateToDetail(it) }
    binding.rvSuperHeroList.setHasFixedSize(true)
    binding.rvSuperHeroList.layoutManager = LinearLayoutManager(this)
    binding.rvSuperHeroList.adapter = adapter
  }

  private fun searchByName(query: String) {
    binding.progressBar.isVisible = true
    CoroutineScope(Dispatchers.IO).launch {
      val myResponse: Response<SuperHeroDataResponse> =
        retrofit.create(ApiService::class.java).getSuperHeroes(query)

      if (myResponse.isSuccessful) {
        Log.i("mauricio", "Working")
        val response: SuperHeroDataResponse? = myResponse.body()
        if(response !=null) {
          Log.i("mauricio", response.toString())

          // send to principal thread
          runOnUiThread {
            adapter.updateList(response.superheroes)
            binding.progressBar.isVisible = false
          }
        }
      } else {
        Log.i("mauricio", "Not working")
      }
    }
  }

  private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://superheroapi.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  private fun navigateToDetail(id:String){
    val intent = Intent(this,DetailSuperheroActivity::class.java)
    intent.putExtra(EXTRA_ID,id)
    startActivity(intent)
  }
}