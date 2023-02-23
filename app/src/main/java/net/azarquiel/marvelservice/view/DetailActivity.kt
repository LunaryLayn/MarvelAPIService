package net.azarquiel.marvelservice.view

import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.marvelservice.R
import net.azarquiel.marvelservice.entities.Hero
import net.azarquiel.marvelservice.entities.Punto
import net.azarquiel.marvelservice.entities.Usuario
import net.azarquiel.marvelservice.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvDetailName : TextView
    private lateinit var tvDetailDesc : TextView
    private lateinit var ivDetailHero : ImageView
    private lateinit var ratDetail: RatingBar
    private lateinit var ratDetailPoint: RatingBar
    private lateinit var hero: Hero
    private var thisrating: Float = 0.0f
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        hero = intent.getSerializableExtra("hero") as Hero
        usuario = intent.getSerializableExtra("usuario") as Usuario?

        tvDetailName = findViewById(R.id.tvDetailName)
        tvDetailDesc = findViewById(R.id.tvDetailDesc)
        ivDetailHero = findViewById(R.id.ivDetailHero)
        ratDetail = findViewById(R.id.ratDetail)
        ratDetailPoint = findViewById(R.id.ratDetailPoint)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        getAvgRating()
        showHero()
        ratDetailPoint.setOnRatingBarChangeListener { ratingBar, _, _ ->
            ratingOnClick(ratDetailPoint.rating)
        }
    }

    private fun ratingOnClick(rating: Float) {
        if(usuario == null) {
            Toast.makeText(this, "Debes estar logueado para puntuar", Toast.LENGTH_SHORT).show()
            ratDetailPoint.rating=0f
            return
        } else {
            var estrellas = rating.toInt()
            viewModel.savePuntoHero(hero.id, Punto(0, hero.id, estrellas)).observe( this, Observer { it ->
                it?.let{
                    Toast.makeText(this, "Puntuación guardada", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun getAvgRating() {
        viewModel.getAvgHero(hero.id).observe(this, Observer { it ->
            it?.let{
                thisrating = it.toFloat()
            }
        })
    }

    private fun showHero() {
        tvDetailName.text = hero.name
        tvDetailDesc.text = hero.description
        Picasso.get().load("${hero.thumbnail.path}/standard_fantastic.${hero.thumbnail.extension}").into(ivDetailHero)
        ratDetail.rating = 5f
    }
}