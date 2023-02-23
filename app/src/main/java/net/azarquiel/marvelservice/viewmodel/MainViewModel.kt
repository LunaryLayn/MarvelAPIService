package net.azarquiel.marvelservice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.marvelservice.api.MainRepository
import net.azarquiel.marvelservice.entities.Hero
import net.azarquiel.marvelservice.entities.Punto
import net.azarquiel.marvelservice.entities.Usuario

class MainViewModel : ViewModel() {


    private var repository: MainRepository = MainRepository()

    fun getAllHeroes(): MutableLiveData<List<Hero>> {
        val heroes = MutableLiveData<List<Hero>>()
        GlobalScope.launch(Main) {
            heroes.value = repository.getAllHeroes()
        }
        return heroes
    }

    fun getAvgHero(idhero:Long): MutableLiveData<Int> {
        val avg = MutableLiveData<Int>()
        GlobalScope.launch(Main) {
            avg.value = repository.getAvgHero(idhero)
        }
        return avg
    }

    fun getUserByNickAndPass(nick:String, pass:String):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.getUserByNickAndPass(nick, pass)
        }
        return usuarioresponse
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.saveUsuario(usuario)
        }
        return usuarioresponse
    }

    fun savePuntoHero(idhero: Long, punto: Punto): MutableLiveData<Punto> {
        val responsepunto = MutableLiveData<Punto>()
        GlobalScope.launch(Main) {
            responsepunto.value = repository.savePuntoHero(idhero, punto)
        }
        return responsepunto
    }

}