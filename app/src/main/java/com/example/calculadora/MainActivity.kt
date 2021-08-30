//
//  MainActivity.ky
//  Calculadora
//
//  Creado por Bernardo García el 18/08/21.
//
//
// Descripción: El archivo es el controlador de nuestro sistema, el cual se encarga de
// unir la información entre el modelo y la vista para que así los otros dos sean
// independientes y puedan funcionar en diferentes contxtos.
//
//

package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    /*
        Se requieren variables para: controlar el display de la vista,
        ver si el usuario está escribiendo algo, ver si el usuario ha usado
        el punto previamente, el resultado a desplegar en el diaplay y el modelo
        de la calculadora.
    */
    private lateinit var display : TextView
    private lateinit var displayEstado : TextView
    private lateinit var displayMemoria : TextView
    private var usuarioEstaEscribiendo = false
    private var usuarioIngresaPunto = false
    private var resultado = 0.0
    private var modeloCalculadora = ModeloCalculadora()

    /*
        override fun constructor

        Descripción: Inicializamos la aplicación y nuestros displays de la pantalla

     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.display)
        displayEstado = findViewById(R.id.displayEstado)
        displayMemoria = findViewById(R.id.displayMemoria)

        Log.d(TAG, "En onCreate")

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "En onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "En onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "En onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "En onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "En onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "En onDestroy")
    }

    /*
       fun puntoPresionado
       params View
       return ---

       Descripción: La función se activa únicamente cuando el usuario presiona un botón.
       Se hacen las verificaciones necesarias para que el usuario no use el punto indebidamente.

    */

    fun puntoPresionado(unBoton: View){
        val resTemporal = display.text.toString()
        if(!usuarioEstaEscribiendo){
            display.text="0."
            usuarioEstaEscribiendo = true
            usuarioIngresaPunto = true
        }
        else if(!resTemporal.contains(".")){
            display.append(".")
            display.text = display.text
            usuarioEstaEscribiendo = true
            usuarioIngresaPunto = true
        }else
            Toast.makeText(applicationContext, "ERROR: Ya existe un punto", Toast.LENGTH_SHORT).show()



    }

    /*
       fun digitoPresionado
       params View
       return ---

       Descripción: El dígito presionado se agrega al display para su visualización.
       Se hacen las verificaciones necesarias para que el display se adapte a lo que
       el usuario ve (distinguir entre valores enteros y flotantes).

    */

    fun digitoPresionado(unBoton : View){
        val digito = (unBoton as Button).text
        val tmp = display.text.toString().toDouble()
        if ((tmp == 0.0 && !usuarioIngresaPunto) || !usuarioEstaEscribiendo) {
            display.text = digito.toString()
            usuarioEstaEscribiendo = true
        }
        else {
            display.append(digito)
            display.text = display.text
        }
    }

    /*
       fun operacionPresionada
       params View
       return ---

       Descripción: La función es para aquellas operaciones que requieren dos operandos.

    */

    fun operacionPresionada(unBoton : View){
        val operacionPresionada = (unBoton as Button).text.toString()
        if (usuarioEstaEscribiendo){
            //operando = display.text.toString().toInt()
                modeloCalculadora.setOperando(display.text.toString().toDouble())
            usuarioEstaEscribiendo = false
            usuarioIngresaPunto = false

        }
        resultado = modeloCalculadora.ejecutaOperacion(operacionPresionada)
        display.text = modeloCalculadora.entregaResultado(resultado)
        verificarError()
    }

    /*
        fun especialPresionada
        params View
        return ---

        Descripción: La función es para aquellas operaciones que requieren un operando.

    */
    fun especialPresionado(unBoton: View){
        val simboloBoton = (unBoton as Button).text.toString()
        if (usuarioEstaEscribiendo){
            //operando = display.text.toString().toInt()
            modeloCalculadora.setOperando(display.text.toString().toDouble())
            usuarioEstaEscribiendo = false
            usuarioIngresaPunto = false
        }
        resultado = modeloCalculadora.ejecutaEspecial(simboloBoton)
        display.text = modeloCalculadora.entregaResultado(resultado)
        verificarError()


    }
    /*
        fun memoriaPresionada
        params View
        return ---

        Descripción: La función se encarga de las operaciones de memoria
        para desplegarla en el display de la memoria.
     */

    fun memoriaPresionado(unBoton: View){
        val opcion = (unBoton as Button).text.toString()
        if(opcion!="Recall"){
            val valor = display.text.toString().toDouble()
            modeloCalculadora.ejecutaMemoria(opcion, valor)
            displayMemoria.text = modeloCalculadora.returnMemoria()
        }else{
            display.text = modeloCalculadora.returnMemoria()
        }

    }

    /*
        fun backspacePresionado
        params View
        return ---

        Descripción: La función se encarga de hacer los ajustes necesarios en el controlador
        y en la vista para que se borre el último dígito ingresado al display principal.
        De igual manera, se hacen las validaciones necesarias para el funcionamiento correcto
        del botón.
     */

    fun backspacePresionado(unBoton: View){
        val tmp = display.text.toString().dropLast(1)
        if(tmp.isEmpty() || tmp == "0"){
            display.text = "0"
            usuarioEstaEscribiendo = false
        }
        else{
            display.text = tmp
            usuarioEstaEscribiendo = true
        }

    }

    private fun actualizaDisplayEstado(){


    }

    /*

     */

    private fun verificarError(){
        if(modeloCalculadora.hayError())
            Toast.makeText(applicationContext, "ERROR: " + modeloCalculadora.getError(), Toast.LENGTH_SHORT).show()
    }

}