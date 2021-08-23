package com.example.calculadora

class ModeloCalculadora {
    private var operandoSiguiente = 0
    private var operacionEnEsperaDeOperando = ""
    private var operando = 0

    fun setOperando(unOperando: Int){
         operando = unOperando
    }

    fun ejecutaOperacion(operacion : String) : Int {
        ejecutaOperacionEnEspera()
        operacionEnEsperaDeOperando = operacion
        operandoSiguiente = operando
        return operando
    }

    private fun ejecutaOperacionEnEspera(){
        when (operacionEnEsperaDeOperando){
            "+"-> operando += operandoSiguiente
            "-"-> operando = operandoSiguiente - operando
            "*"-> operando *= operandoSiguiente
            "/"-> {
                if (operando != 0) {
                    operando = operandoSiguiente / operando
                }
            }

        }

    }

}