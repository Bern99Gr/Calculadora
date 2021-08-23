package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

abstract class MainActivity : AppCompatActivity() {

    //private lateinit var miPrimerBoton : Button
    private lateinit var display : TextView
    private var usuarioEstaEscribiendo = false
    private var resultado = 0


    /*
    Variables de la clase ModeloCalculadora

    private var operandoSiguiente = 0
    private var operacionEnEsperaDeOperando = ""
    private var operando = 0
    */

    private var modeloCalculadora = ModeloCalculadora()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //miPrimerBoton = findViewById(R.id.miPrimerBoton)
        display = findViewById(R.id.display)
        /*miPrimerBoton.setOnClickListener {
            Log.d(TAG, "Presionaron Aceptar")
            contador = display.text.toString().toInt()
            contador++
            display.text = contador.toString()
        }*/

    }


    // Mostrar cómo ignorar los warnings para el compilador
    //@Suppress("UNUSED_PARAMETER")
    /*fun botonPresionado(unBoton : View){
        contador = display.text.toString().toInt()
        contador--
        display.text = contador.toString()
        Log.d(TAG, "Presionaron Cancelar")
    }*/

    fun digitoPresionado(unBoton : View){
        val digito = (unBoton as Button).text
        if (display.text.toString().toInt() == 0 || !usuarioEstaEscribiendo) {
            display.text = digito.toString()
            usuarioEstaEscribiendo = true
        }
        else {
            display.append(digito)
        }
    }

    fun operacionPresionada(unBoton : View){
        val operacionPresionada = (unBoton as Button).text.toString()
        if (usuarioEstaEscribiendo){
            //operando = display.text.toString().toInt()
                modeloCalculadora.setOperando(display.text.toString().toInt())
            usuarioEstaEscribiendo = false
        }
        resultado = modeloCalculadora.ejecutaOperacion(operacionPresionada)
        display.text = resultado.toString()

    }
    /*
    private fun ejecutaOperacion(operacion : String) : Int {
        ejecutaOperacionEnEspera()
        operacionEnEsperaDeOperando = operacion
        operandoSiguiente = operando
        return operando
    }

    private fun ejecutaOperacionEnEspera() : Boolean{
        when (operacionEnEsperaDeOperando){
            "+"-> operando += operandoSiguiente
            "-"-> operando = operandoSiguiente - operando
            "*"-> operando *= operandoSiguiente
            "/"-> {
                if(operando != 0) {
                    operando = operandoSiguiente / operando
                }
            }
            "="-> return false

        }
        Log.d(TAG, operacionEnEsperaDeOperando)
        return true
    }*/

}