package net.azarquiel.marvelservice.api

import net.azarquiel.marvelservice.entities.Hero
import net.azarquiel.marvelservice.entities.Punto
import net.azarquiel.marvelservice.entities.Usuario

class MainRepository {
    val service = WebAccess.marvelService

    suspend fun getAllHeroes(): List<Hero> {
        val webResponse = service.getAllHeroes().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.data.results
        }
        return emptyList()
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun getUserByNickAndPass(nick:String, pass:String): Usuario? {
        val webResponse = service.getUserByNickAndPass(nick, pass).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun getAvgHero(idhero:Long): Int {
        val webResponse = service.getAvgHero(idhero).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.avg
        }
        return 0
    }

    suspend fun savePuntoHero(idhero: Long, punto: Punto): Punto? {
        var puntoresponse: Punto? = null
        val webResponse = service.savePuntoHero(idhero,punto).await()
        if (webResponse.isSuccessful) {
            puntoresponse = webResponse.body()!!.punto
        }
        return puntoresponse
    }

}