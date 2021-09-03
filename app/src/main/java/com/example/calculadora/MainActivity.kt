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

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(){
    /*
        Se requieren variables para: controlar el display de la vista, display del
        estado de la operación, display de la memoria, display para radianes o grados,
        el resultado a desplegar en el diaplay y el modelo de la calculadora.
    */
    private lateinit var display : TextView
    private lateinit var displayEstado : TextView
    private lateinit var displayMemoria : TextView
    private lateinit var displayRadianes : TextView
    private var resultado = 0.0
    private val calculadoraViewModel : CalculadoraViewModel by lazy {
        ViewModelProvider(this).get(CalculadoraViewModel::class.java)
    }


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
        displayRadianes = findViewById(R.id.displayRadianes)

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
        display.text = calculadoraViewModel.display
        displayEstado.text = calculadoraViewModel.displayEstado
        displayRadianes.text = calculadoraViewModel.displayRadianes
        displayMemoria.text = calculadoraViewModel.displayMemoria

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "En onDestroy")
        calculadoraViewModel.display = display.text.toString()
        calculadoraViewModel.displayEstado = displayEstado.text.toString()
        calculadoraViewModel.displayMemoria = displayMemoria.text.toString()
        calculadoraViewModel.displayRadianes = displayRadianes.text.toString()

    }

    /*
       fun puntoPresionado
       params View
       return ---

       Descripción: La función se activa únicamente cuando el usuario presiona un botón.
       Se hacen las verificaciones necesarias para que el usuario no use el punto indebidamente.

    */
    @Suppress("UNUSED_PARAMETER")
    fun puntoPresionado(unBoton: View){
        val resTemporal = display.text.toString()
        if(!calculadoraViewModel.usuarioEstaEscribiendo){
            display.text="0."
            calculadoraViewModel.usuarioEstaEscribiendo = true
            calculadoraViewModel.usuarioIngresaPunto = true
        }
        else if(!resTemporal.contains(".")){
            display.append(".")
            display.text = display.text
            calculadoraViewModel.usuarioEstaEscribiendo = true
            calculadoraViewModel.usuarioIngresaPunto = true
        }else
            Toast.makeText(applicationContext, "ERROR: Ya existe un punto", Toast.LENGTH_SHORT).show()

        verificarError()
        actualizaDisplayEstado()


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
        if ((tmp == 0.0 && !calculadoraViewModel.usuarioIngresaPunto) || !calculadoraViewModel.usuarioEstaEscribiendo) {
            display.text = digito.toString()
            calculadoraViewModel.usuarioEstaEscribiendo = true
        }
        else {
            display.append(digito)
            display.text = display.text
        }
        calculadoraViewModel.operacionEspera = false
        verificarError()
        actualizaDisplayEstado()
    }

    /*
       fun operacionPresionada
       params View
       return ---

       Descripción: La función es para aquellas operaciones que requieren dos operandos.

    */

    fun operacionPresionada(unBoton : View){
        val operacionPresionada = (unBoton as Button).text.toString()
        if (calculadoraViewModel.usuarioEstaEscribiendo || calculadoraViewModel.operacionEspera){
            calculadoraViewModel.modeloCalculadora.setOperando(display.text.toString().toDouble())
            calculadoraViewModel.usuarioEstaEscribiendo = false
            calculadoraViewModel.usuarioIngresaPunto = false
            calculadoraViewModel.operacionEspera = false

        }
        resultado = calculadoraViewModel.modeloCalculadora.ejecutaOperacion(operacionPresionada)
        display.text = calculadoraViewModel.modeloCalculadora.entregaResultado(resultado)
        verificarError()
        actualizaDisplayEstado()
    }

    /*
        fun especialPresionada
        params View
        return ---

        Descripción: La función es para aquellas operaciones que requieren un operando.

    */
    fun especialPresionado(unBoton: View){
        val simboloBoton = (unBoton as Button).text.toString()
        if (calculadoraViewModel.usuarioEstaEscribiendo){
            //operando = display.text.toString().toInt()
            calculadoraViewModel.modeloCalculadora.setOperando(display.text.toString().toDouble())
            calculadoraViewModel.usuarioEstaEscribiendo = false
            calculadoraViewModel.usuarioIngresaPunto = false
        }
        calculadoraViewModel.operacionEspera = true
        resultado = calculadoraViewModel.modeloCalculadora.ejecutaEspecial(simboloBoton)
        display.text = calculadoraViewModel.modeloCalculadora.entregaResultado(resultado)
        displayMemoria.text = calculadoraViewModel.modeloCalculadora.returnMemoria()
        verificarError()
        actualizaDisplayEstado()


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
            calculadoraViewModel.modeloCalculadora.ejecutaMemoria(opcion, valor)
            displayMemoria.text = calculadoraViewModel.modeloCalculadora.returnMemoria()
        }else{
            display.text = calculadoraViewModel.modeloCalculadora.returnMemoria()
        }
        verificarError()
        actualizaDisplayEstado()

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

    @Suppress("UNUSED_PARAMETER")
    fun backspacePresionado(unBoton: View){
        val tmp = display.text.toString().dropLast(1)
        if(tmp.isEmpty() || tmp == "0"){
            display.text = "0"
            calculadoraViewModel.usuarioEstaEscribiendo = false
        }
        else{
            display.text = tmp
            calculadoraViewModel.usuarioEstaEscribiendo = true
        }
        actualizaDisplayEstado()

    }


    /*
        fun deg2radPresionado
        params View
        return ---

        Descripción: Se encarga de hacerle saber al modelo y a la vista que se ha cambiado
        de radianes a grados (o viceversa).
     */

    @SuppressLint("SetTextI18n")
    fun deg2radPresionado(unSwitch: View){
        val checked = (unSwitch as SwitchCompat).isChecked
        calculadoraViewModel.modeloCalculadora.cambiaRadianes(checked)
        if(checked)
            displayRadianes.text = "DEG"
        else
            displayRadianes.text = "RAD"

    }

    /*
        fun actualizaDisplayEstado
        params ---
        return ---

        Descripción: Se encarga de conectarse con el modelo de la calculadora
        para poder desplegar en el displayEstado lo que está sucediendo con el
        modelo. Le muestra al usuario la operación que está por realizarse.
        Esta función se tiene que mandar a llamar con cada método que haga cambios
        en la vista, ya que con cada acción generada, este displat también debe de cambiar.
    */

    private fun actualizaDisplayEstado(){
        val valor1 = display.text.toString()
        val valor2 = calculadoraViewModel.modeloCalculadora.getOperando()
        val operacion = calculadoraViewModel.modeloCalculadora.getOperacion()

        when {
            operacion.isEmpty() -> displayEstado.text = display.text
            !calculadoraViewModel.usuarioEstaEscribiendo && !calculadoraViewModel.operacionEspera-> displayEstado.text = getString(R.string.contenidoEstado, valor2, operacion, "")
            else -> displayEstado.text = getString(R.string.contenidoEstado, valor2, operacion, valor1)
        }


    }

    /*
        fun verificarError
        params ---
        return ---

        Descripción: Se conecta con el modelo de la calculadora para verificar
        si hubo un error en alguna de las operaciones. El modelo guarda el error
        en una variable String que se puede retornar con el método hayError().
        Esta función se tiene que llamar después de haber presionado alguna operación.
    */

    private fun verificarError(){
        if(calculadoraViewModel.modeloCalculadora.hayError())
            Toast.makeText(applicationContext, "ERROR: " + calculadoraViewModel.modeloCalculadora.getError(), Toast.LENGTH_SHORT).show()
        if(display.text.length>=80){
            Toast.makeText(applicationContext, "ERROR: Se excedió el número de dígitos.", Toast.LENGTH_SHORT).show()
            display.text = "0"
        }

    }



}