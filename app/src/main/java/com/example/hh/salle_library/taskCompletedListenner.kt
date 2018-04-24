package com.example.hh.salle_library


interface taskCompletedListenner {

    /**
     * Función con el fin de usarse como callback, para informar la nueva lista una vez terminada la tarea asincrona.
     */
    fun taskCompleted(mList : ArrayList<Book>)

}