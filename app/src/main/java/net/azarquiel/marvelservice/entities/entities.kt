package net.azarquiel.marvelservice.entities

import java.io.Serializable

data class Data(var results:List<Hero> )
data class Hero(var id: Long, var name: String, var description: String , var thumbnail: Thumbnail):Serializable
data class Thumbnail(var path:String, var extension:String):Serializable
data class Punto(var id:Long, var idhero:Long, var puntos: Int)
data class Usuario (var id: Int, var nick: String, var pass: String):Serializable


data class Respuesta (
    val data: Data,
    val usuario:Usuario,
    val puntos:List<Punto>,
    val punto: Punto,
    val avg: Int,
    val msg: String
)