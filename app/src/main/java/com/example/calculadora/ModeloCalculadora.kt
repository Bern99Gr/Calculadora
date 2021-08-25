package com.example.calculadora

class ModeloCalculadora {
    private var operandoSiguiente = 0.0
    private var operacionEnEsperaDeOperando = ""
    private var operando = 0.0

    /*
        fun ejecutaEspecial
        params String
        return Double

        Descripción: Regresa lo que se va a desplegar en el display después
        de haber presionado una de las siguientes funciones: limpia, ...

     */

    fun ejecutaEspecial(unSimbolo: String): Double{
        when(unSimbolo){
            "C"->limpia()
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
        fun asignaFlotante
        params Double
        return String

        Descripción: Si el valor recibido es de tipo Float/Double, lo regresa como String.
        Si es un Int escondido dentro de una variable tipo Foat/Double, la regresa como un valor
        entero dentro de un String

     */
    fun asignaFlotante(unValor: Double): String {
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
            "/"-> {
                if (operando != 0.0) {
                    operando = operandoSiguiente / operando
                }
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
    }

}