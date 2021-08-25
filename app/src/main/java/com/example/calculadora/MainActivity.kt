package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var display : TextView
    private var usuarioEstaEscribiendo = false
    private var resultado = 0.0

    private var modeloCalculadora = ModeloCalculadora()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializamos el display junto con nuestra aplicación
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.display)
    }


    fun digitoPresionado(unBoton : View){
        val digito = (unBoton as Button).text
        if (display.text.toString().toDouble() == 0.0 || !usuarioEstaEscribiendo) {
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
                modeloCalculadora.setOperando(display.text.toString().toDouble())
            usuarioEstaEscribiendo = false
        }
        resultado = modeloCalculadora.ejecutaOperacion(operacionPresionada)
        display.text = modeloCalculadora.asignaFlotante(resultado)
    }

    fun especialPresionado(unBoton: View){
        val simboloBoton = (unBoton as Button).text.toString()
        resultado = modeloCalculadora.ejecutaEspecial(simboloBoton)
    }

}