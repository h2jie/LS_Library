package com.example.hh.salle_library

import android.net.Uri
import java.io.Serializable
import java.net.URL

/**
 * Clase Book
 *
 * Esta clase es para almazenar la información de un específico libro.
 *
 * @property imageUrl La imagen del libro, si este tiene.
 * @property bookTitle El título del libro
 * @property bookAuthor Nombre del autor del libro.
 * @property bookDate Fecha de estreno del libro
 * @property bookDesc Descripción breve del libro.
 *
 * @constructor Defines un libro con todas sus propiedades.
 */
data class Book(var imageUrl : URL, var bookTitle : String, var bookAuthor : String, var bookDate : String, var bookDesc : String ) : Serializable