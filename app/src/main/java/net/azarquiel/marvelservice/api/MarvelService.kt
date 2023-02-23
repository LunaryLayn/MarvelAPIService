package net.azarquiel.marvelservice.api

import kotlinx.coroutines.Deferred
import net.azarquiel.marvelservice.entities.Punto
import net.azarquiel.marvelservice.entities.Respuesta
import net.azarquiel.marvelservice.entities.Usuario
import retrofit2.Response
import retrofit2.http.*

interface MarvelService {
    @GET("hero")
    fun getAllHeroes(): Deferred<Response<Respuesta>>

    @GET("hero/{idhero}/avgpuntos")
    fun getAvgHero(@Path("idhero") idhero: Long): Deferred<Response<Respuesta>>

    // nick y pass como variables en la url?nick=paco&pass=paco
    @GET("usuario")
    fun getUserByNickAndPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Respuesta>>

    // post con objeto en json
    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @POST("hero/{idhero}/punto")
    fun savePuntoHero(
        @Path("idhero") idhero: Long,
        @Body punto: Punto
    ): Deferred<Response<Respuesta>>
}