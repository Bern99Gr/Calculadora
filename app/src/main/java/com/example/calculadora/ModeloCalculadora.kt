//
//  ModeloCalculadora.ky
//  Calculadora
//
//  Creado por Bernardo García el 18/08/21.
//
//
// Descripción: La clase ModeloCalculadora se encarga de realizar todas las funciones necesarias
// para la lógica de la calculadora. De igual forma, se encarga de ciertos procesos adicionales que
// requiere el controlador para la verificación y buen funcionamiento de la misma calculadora.
//
//

package com.example.calculadora

import kotlin.math.*

class ModeloCalculadora {
    /*
        Se requieren variables para guardar los dos operandos distintos, el operador que
        se va a ejecutar, una variable para usarlo como argumento y determine si los radianes
        están activados o no.
    */
    private var operandoSiguiente = 0.0
    private var operando = 0.0
    private var operacionEnEsperaDeOperando = ""
    private var radDeg = 1.0

    /*
        Se requieren una variable en donde se pueda almacenar el mensaje de error si
        existe alguno
    */
    private var mensajeError = ""

    /*
        Se requieren una variable que sirva de memoria para almacenar el valor
        que el usuario pida
    */
    private var memoria = 0.0

    /*
        fun ejecutaEspecial
        params String
        return Double

        Descripción: Regresa lo que se va a desplegar en el display después
        de haber presionado una de las siguientes operaciones de un solo
        operando: limpia, cambio de signo, ...

     */

    fun ejecutaEspecial(unSimbolo: String): Double{
        when(unSimbolo){
            "C" -> limpia()
            "+/-" -> operando*=-1
            "sin(x)" -> operando = sin(operando*radDeg)
            "cos(x)" -> operando = cos(operando*radDeg)
            "10^x" -> operando = 10.0.pow(operando)
            "1/x" -> {
                if(operando!=0.0)
                    operando = 1/operando
                else
                    mensajeError = "División entre 0"

            }
            "sqrt(x)" -> {
                if(operando>=0.0)
                    operando = sqrt(operando)
                else
                    mensajeError = "Raíz cuadrada de número negativo"
            }
        }
        return operando
    }
    /*
        fun setOperando
        params Double
        return ---

        Descripción: Se le asigna a la variable operando la variable de tipo Double
        que se recibió.

     */

    fun setOperando(unOperando: Double){
         operando = unOperando
    }

    /*
       fun ejecutaOperacion
       params String
       return Double

       Descripción: Recibe la operación a realizar como una variable String y
       se encarga de regresar el resultado y hacer los preparativos para la
       siguiente operación a realizar.

    */
    fun ejecutaOperacion(operacion : String) : Double {
        ejecutaOperacionEnEspera()
        operacionEnEsperaDeOperando = operacion
        operandoSiguiente = operando
        return operando
    }

    /*
        fun entregaResultado
        params Double
        return String

        Descripción: Si el valor recibido es de tipo Float/Double, lo regresa como String.
        Si es un Int escondido dentro de una variable tipo Foat/Double, la regresa como un valor
        entero dentro de un String

     */
    fun entregaResultado(unValor: Double): String {
        return if (unValor.toInt().toDouble() != unValor)
            unValor.toString()
        else
            unValor.toInt().toString()
    }
    /*
        private fun asignaFlotante
        params ---
        return ---

        Descripción: La variable de la clase "operacionEnEsperaDeOperando" asigna
        la operación a realizar para la variable privada "operando".
        "Operando" va a operar junto con la variable privada "operandoSiguiente".
        Se va a poder hacer las operaciones siguientes: suma, resta, multiplicación,
        división...

     */
    private fun ejecutaOperacionEnEspera(){
        when (operacionEnEsperaDeOperando){
            "+"-> operando += operandoSiguiente
            "-"-> operando = operandoSiguiente - operando
            "*"-> operando *= operandoSiguiente
            "x^n" -> operando = operando.pow(operandoSiguiente)
            "/"-> {
                if (operando != 0.0) {
                    operando = operandoSiguiente / operando
                }
                else
                    mensajeError = "División entre 0"
            }

        }

    }

    /*
        fun limpia
        params ---
        return ---

        Descripción: Se encarga de reiniciar todos los valores a 0 (o vacíos)
        para tener la calculadora lista para usar nuevamente desde cero.

     */

    private fun limpia(){
        operando = 0.0
        operandoSiguiente = 0.0
        operacionEnEsperaDeOperando = ""
        memoria = 0.0
    }
    /*
        fun cambiaRadianes
        params ---
        return ---

        Descripción: La función se encarga de hacer la variable "radDeg (Double)"
        adaptarse a usarse como un operando multiplicativo dentro de una función trigonométrica
        y corresponda a radianes o grades.

        Ejemplo: Las funciones trigonométricas en Kotlin funcionan en radianes. El argumento
        de las funciones tiene que estar multiplicado por 1 en ese caso. En el caso que se quiera
        en grados, se va a necesitar multiplicar por el factor adecuado al cambio (180 / PI)
     */
    fun cambiaRadianes(){
        radDeg = if(radDeg==1.0)
            180/ PI
        else
            1.0
    }

    /*
        fun ejecutaMemoria
        params String, Double
        return String

        Descripción: Ejecuta las funciones relacionadas a la memoria que no tengan que modificar
        el display principal de la aplicación. Solo hace cambios en el almacenamiento de la memoria
        y regresa la memoria como un String.

     */

    fun ejecutaMemoria(unaFuncion: String, unValor: Double): String{
        when(unaFuncion) {
            "Store" -> memoria = unValor
            "Mem+" -> memoria += unValor
            "Mem-" -> memoria -= unValor
            "MC" -> memoria = 0.0
        }
        return entregaResultado(memoria)
    }

    /*
    fun returnMemoria
    params ---
    return String

    Descripción: Regresa lo almacenado en memoria como un String para poder desplegarlo en
    el display principal.

    */

    fun returnMemoria(): String {
        return entregaResultado(memoria)
    }

    /*
        fun esError
        params ---
        return ---

        Descripción: Devuelve verdadero si existe un mensaje de error
     */

    fun hayError() : Boolean{
        return mensajeError.isNotEmpty()
    }

    /*
        fun getError
        params ---
        return String

        Descripción: Devuelve el mensaje de error y vacía la variable "mensajeError"
     */

    fun getError() : String{
        val tmp = mensajeError
        mensajeError = ""
        return tmp
    }
}