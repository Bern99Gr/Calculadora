//
//  CalculadoraViewModel.ky
//  Calculadora
//
//  Creado por Bernardo García el 30/08/21.
//
//
// Descripción: El archivo es la conexión entre el controlador y el modelo para su correcto
// funcionamiento. Principalmente está para que la aplicación funcione sin problemas aún
// cuando se gire la pantalla.
//

package com.example.calculadora

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "CalculadoraViewModel"

class CalculadoraViewModel: ViewModel() {
    /*
        Se requieren variables para guardar los estados del
        display de la vista, display del estado de la operación,
        display de la memoria, display para radianes o grados,
        ver si el usuario está escribiendo algo, ver si el usuario ha usado
        el punto previamente,
     */
    val modeloCalculadora = ModeloCalculadora()
    var display = "0"
    var displayEstado = "0"
    var displayMemoria = "0"
    var displayRadianes = "RAD"
    var usuarioEstaEscribiendo = false
    var usuarioIngresaPunto = false
    var operacionEspera = false



    init {
        Log.d(TAG, "Instancia de ViewModel creada")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Instancia de ViewModel destruida")
    }
}