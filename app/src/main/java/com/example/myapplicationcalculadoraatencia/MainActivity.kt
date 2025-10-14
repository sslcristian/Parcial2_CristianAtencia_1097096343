package com.example.myapplicationcalculadoraatencia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var tvResultado: TextView
    private var operador = ""
    private var valor1 = 0.0
    private var nuevoValor = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResultado = findViewById(R.id.txtResultado)

        // --- Lista de botones numéricos ---
        val botonesNumeros = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btnPunto
        )

        // --- Lista de operadores ---
        val botonesOperadores = listOf(
            R.id.btnSumar, R.id.btnRestar, R.id.btnMultiplicar,
            R.id.btnDividir, R.id.btnPorcentaje
        )

        // --- Lógica de los números ---
        for (id in botonesNumeros) {
            findViewById<Button>(id).setOnClickListener {
                var textoActual = tvResultado.text.toString()
                val boton = (it as Button).text.toString()

                // Evita acumulación de ceros o sobreescritura
                if (nuevoValor || textoActual == "0") {
                    textoActual = ""
                    nuevoValor = false
                }

                tvResultado.text = String.format(Locale.getDefault(), "%s%s", textoActual, boton)
            }
        }

        // --- Lógica de los operadores ---
        for (id in botonesOperadores) {
            findViewById<Button>(id).setOnClickListener {
                valor1 = tvResultado.text.toString().toDoubleOrNull() ?: 0.0
                operador = (it as Button).text.toString()
                nuevoValor = true
            }
        }

        // --- Botón Igual ---
        findViewById<Button>(R.id.btnIgual).setOnClickListener {
            val valor2 = tvResultado.text.toString().toDoubleOrNull() ?: 0.0
            val resultado = when (operador) {
                "+", "＋" -> valor1 + valor2
                "-", "−" -> valor1 - valor2
                "×", "*" -> valor1 * valor2
                "÷", "/" -> if (valor2 != 0.0) valor1 / valor2 else Double.NaN
                "%" -> valor1 * (valor2 / 100)
                else -> valor2
            }

            // Mostrar el resultado con formato usando Locale
            tvResultado.text = if (resultado % 1 == 0.0)
                String.format(Locale.getDefault(), "%d", resultado.toInt())
            else
                String.format(Locale.getDefault(), "%.4f", resultado)

            nuevoValor = true
        }

        // --- Botón Clear ---
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            tvResultado.text = "0"
            valor1 = 0.0
            operador = ""
            nuevoValor = false
        }
    }
}

