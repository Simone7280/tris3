package com.example.tris3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var Breset: Button
    private lateinit var celle: Array<Button>
    private var griglia: Array<IntArray> = Array(3) { IntArray(3) { 0 } }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Breset = findViewById(R.id.r1)
        Breset.setOnClickListener() { Freset() }
        val gridLayout: androidx.gridlayout.widget.GridLayout  = findViewById(R.id.g1)

        celle = Array(9) { i ->
            gridLayout.getChildAt(i) as Button
        }

        celle.forEachIndexed { i, Button ->
            Button.setOnClickListener {
                Mossa(i)
            }
        }
    }
    private fun Freset() {
        for (cella in celle) {
            cella.setText("")
            cella.isClickable = true
            griglia = Array(3) { IntArray(3) { 0 } }
            Breset.setText("Ricomincia")


        }
    }

    private fun Mossa(i:Int){
        val row = i / 3
        val col = i % 3
        celle.get(i).setText("X")
        celle.get(i).isClickable = false
        griglia[row][col] = 1
        if (checkvincitore()) {

             Breset.setText("Hai Vinto - Ricomincia")
                celle.forEachIndexed { i, Button ->
                Button.isClickable = false}
        }
        else {
            if (!checkMossa()) {
               Mossaapp()
            }
            else {
                Breset.setText("Pari - Ricomincia")
            }


        }
    }

    private fun checkMossa(): Boolean {
        return griglia.all { row -> row.all { it != 0 } }
    }

    private fun checkvincitore(): Boolean {
        for (i in 0..2) {
            if (griglia[i][0] != 0 && griglia[i][0] == griglia[i][1] && griglia[i][1] == griglia[i][2]) {
                return true
            }
            if (griglia[0][i] != 0 && griglia[0][i] == griglia[1][i] &&griglia[1][i] == griglia[2][i]) {
                return true
            }
        }
        if (griglia[0][0] != 0 && griglia[0][0] == griglia[1][1] && griglia[1][1] == griglia[2][2]) {
            return true
        }
        if (griglia[0][2] != 0 && griglia[0][2] == griglia[1][1] && griglia[1][1] == griglia[2][0]) {
            return true
        }
        return false
    }

    private fun Mossaapp() {
        if (!Mossaapp_ext2()) {
            if (!Mossaapp_ext(2)) {
                Mossaapp_ext(1)
            }
        }

        if (checkvincitore()) {

            Breset.setText("Hai Perso - Ricomincia")
            celle.forEachIndexed { i, Button ->
                Button.isClickable = false}
        }
        else {
            if (checkMossa()) {
                Breset.setText("Pari - Ricomincia")
            }


        }
    }

    private fun Mossaapp_ext(m: Int) : Boolean    {
        // qui andrebbero implementate tutte le possibili mosse
        var flag = 0
        for (i in 0..2) {
            if (flag == 0) {
                for (j in 0..2) {
                    if (griglia[i][j] == m) {
                        checkLibero(i, j)
                        flag = 1
                        break
                    }
                }
            }
        }
        return if (flag == 0) false else true
    }

    // cerca di vincere computer
    private fun Mossaapp_ext2() : Boolean {
        var flag = 0
        var k = 0

        for (i in 0..2) {
            if (flag == 0) {
                for (j in 0..2) {
                    if (griglia[i][j] == 0) {
                        griglia[i][j] = 2
                        if (checkvincitore()) {
                            k = 3 * i + j
                            celle.get(k).setText("O")
                            celle.get(k).isClickable = false
                            flag = 1
                            break
                        } else {
                            griglia[i][j] = 0
                        }
                    }
                }
            }
        }

        if (flag == 0)
        {
        //  Blocca l'avversario computer
        for (i in 0..2) {
            if (flag == 0) {
                for (j in 0..2) {
                    if (griglia[i][j] == 0) {
                        griglia[i][j] = 1
                        if (checkvincitore()) {
                            griglia[i][j] = 2
                            k = 3 * i + j
                            celle.get(k).setText("O")
                            celle.get(k).isClickable = false
                            flag = 1
                            break
                        } else {
                            griglia[i][j] = 0
                        }
                    }
                }
            }
        }
    }


        return if (flag == 0) false else true
    }

    private fun checkLibero(row: Int, col: Int){
        var flag = 0
        for (i in -1..1) {
            if (flag == 0) {
                for (j in -1..1) {
                    val newRow = row + i
                    val newCol = col + j
                    if (newRow in 0..2 && newCol in 0..2 && griglia[newRow][newCol] == 0) {
                        griglia[newRow][newCol] = 2
                        val k = 3 * newRow + newCol
                        celle.get(k).setText("O")
                        celle.get(k).isClickable = false
                        flag = 1
                        break

                    }
                }
            }
        }

    }



}