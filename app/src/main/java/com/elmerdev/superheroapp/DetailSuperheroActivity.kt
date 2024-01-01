package com.elmerdev.superheroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.view.isVisible
import com.elmerdev.superheroapp.databinding.ActivityDetailSuperheroBinding
import com.elmerdev.superheroapp.databinding.ActivitySuperHeroListBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailSuperheroBinding
  private lateinit var retrofit: Retrofit

  companion object {
    const val EXTRA_ID = "extra_id"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
    retrofit = getRetrofit()
    getSuperHeroInformation(id)
  }

  private fun getSuperHeroInformation(id: String) {
    binding.progressBar.isVisible = true
    CoroutineScope(Dispatchers.IO).launch {
      val myResponse = retrofit.create(ApiService::class.java).getSuperHeroDetail(id)
      if (myResponse.isSuccessful && myResponse.body() != null) {
        Log.i("mauricio", myResponse.body().toString())
        runOnUiThread {
          renderUI(myResponse.body()!!)
          binding.progressBar.isVisible = false
        }
      } else {
        Log.i("mauricio", myResponse.body().toString())
      }
    }
  }

  private fun renderUI(superheroResponse: SuperHeroDetailResponse) {
    Picasso.get().load(superheroResponse.image.url).into(binding.ivSuperHero)
    binding.tvSuperHeroName.text = superheroResponse.name
    prepareStats(superheroResponse.powerStats)
    binding.tvSuperHeroRealName.text = superheroResponse.biography.fullName
    binding.tvPublisher.text = superheroResponse.biography.publisher
  }

  private fun prepareStats(powerStats: PowerStatsResponse) {
    updateHeight(binding.viewIntelligence, powerStats.intelligence)
    updateHeight(binding.viewCombat, powerStats.combat)
    updateHeight(binding.viewDurability, powerStats.durability)
    updateHeight(binding.viewPower, powerStats.power)
    updateHeight(binding.viewSpeed, powerStats.speed)
    updateHeight(binding.viewStrength, powerStats.speed)
  }

  private fun updateHeight(view:View, stat:String){
    val params = view.layoutParams
    params.height = pxToDp(stat.toFloat())
    view.layoutParams = params
  }

  private fun pxToDp(px:Float):Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
  }

  private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://superheroapi.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
}