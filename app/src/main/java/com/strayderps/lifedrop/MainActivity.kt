package com.strayderps.lifedrop

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.strayderps.lifedrop.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("Experimental again")

        // Health TextViews
        val tvPlayerOneHealth = binding.tvPlayerOneHealth
        val tvPlayerTwoHealth = binding.tvPlayerTwoHealth

        // Plus and minus Image Buttons
        val ibPlayerOnePlus = binding.ibPlayerOnePlus
        val ibPlayerOneMinus = binding.ibPlayerOneMinus
        val ibPlayerTwoPlus = binding.ibPlayerTwoPlus
        val ibPlayerTwoMinus = binding.ibPlayerTwoMinus

        // Floating Action Buttons
        val fabPalette = binding.fabPalette
        val fabRestart = binding.fabRestart

        // Play reset animation for all main buttons, health texts, and fab
        playResetAnimations(tvPlayerOneHealth, ibPlayerOnePlus, ibPlayerOneMinus, tvPlayerTwoHealth, ibPlayerTwoPlus, ibPlayerTwoMinus)

        // Constraint Layouts
        val clPlayerOne = binding.clPlayerOne
        val clPlayerTwo = binding.clPlayerTwo

        // Number Edit Text for the Reset Dialog
        val startingHealthString = "20"
        val maxHealthCharacters = 4
        val resetEditText = EditText(this)
        resetEditText.inputType = InputType.TYPE_CLASS_NUMBER
        resetEditText.setText(startingHealthString)
        resetEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxHealthCharacters))
        resetEditText.gravity = Gravity.CENTER

        // Reset Dialog
        val resetDialog = AlertDialog.Builder(this)
            .setTitle("Confirm starting health")
            .setIcon(R.drawable.heart_plus)
            .setView(resetEditText)
            .setPositiveButton("Restart") { _, _ ->
                playResetAnimations(tvPlayerOneHealth, ibPlayerOnePlus, ibPlayerOneMinus, tvPlayerTwoHealth, ibPlayerTwoPlus, ibPlayerTwoMinus)
                resetHealth(tvPlayerOneHealth, resetEditText.text.toString().toInt())
                resetHealth(tvPlayerTwoHealth, resetEditText.text.toString().toInt())
                Toast.makeText(this, "New game started", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->
            }.create()

        val colorOptions = arrayOf("White", "Blue", "Black", "Red", "Green")

        // Palette Dialog 2
        val paletteDialog2 = AlertDialog.Builder(this)
            .setTitle("Choose player 2 color")
            .setIcon(R.drawable.palette)
            .setSingleChoiceItems(colorOptions, 2) {dialogInterface, i ->
                changePlayerColors(clPlayerOne, clPlayerTwo, tvPlayerTwoHealth, fabPalette, fabRestart, ibPlayerOnePlus, ibPlayerOneMinus, ibPlayerTwoPlus, ibPlayerTwoMinus, colorOptions[i])
            }
            .setPositiveButton("Done") { _, _ ->
                Toast.makeText(this, "Player colors updated", Toast.LENGTH_SHORT).show()
            }.create()

        // Palette Dialog 1
        val paletteDialog1 = AlertDialog.Builder(this)
            .setTitle("Choose player 1 color")
            .setIcon(R.drawable.palette)
            .setSingleChoiceItems(colorOptions, 0) {dialogInterface, i ->
                changePlayerColors(clPlayerOne, clPlayerOne, tvPlayerOneHealth, fabPalette, fabRestart, ibPlayerOnePlus, ibPlayerOneMinus, ibPlayerTwoPlus, ibPlayerTwoMinus, colorOptions[i])
            }
            .setPositiveButton("Next") { _, _ ->
                paletteDialog2.show()
            }.create()

        // Click Listeners
        ibPlayerOnePlus.setOnClickListener {
            ibPlayerOnePlus.scaleDown(125L, 0L)
            tvPlayerOneHealth.scaleUp(125L, 0L)
            changeHealth(tvPlayerOneHealth, 1)
        }

        ibPlayerOneMinus.setOnClickListener {
            ibPlayerOneMinus.scaleDown(125L, 0L)
            tvPlayerOneHealth.scaleDown(125L, 0L)
            changeHealth(tvPlayerOneHealth, -1)
        }

        ibPlayerTwoPlus.setOnClickListener {
            ibPlayerTwoPlus.scaleDown(125L, 0L)
            tvPlayerTwoHealth.scaleUp(125L, 0L)
            changeHealth(tvPlayerTwoHealth, 1)
        }

        ibPlayerTwoMinus.setOnClickListener {
            ibPlayerTwoMinus.scaleDown(125L, 0L)
            tvPlayerTwoHealth.scaleDown(125L, 0L)
            changeHealth(tvPlayerTwoHealth, -1)
        }

        fabPalette.setOnClickListener() {
            paletteDialog1.show()
        }

        fabRestart.setOnClickListener() {
            resetDialog.show()
        }

        window.statusBarColor = getColor(R.color.blackButton)
    }

    private fun changeHealth(playerHealth: TextView, amount: Int) {
        var currentHealth = playerHealth.text.toString().toInt()
        currentHealth += amount
        playerHealth.text = currentHealth.toString()
    }

    private fun playResetAnimations(
        tvPlayerOneHealth: TextView,
        ibPlayerOnePlus: ImageButton,
        ibPlayerOneMinus: ImageButton,
        tvPlayerTwoHealth: TextView,
        ibPlayerTwoPlus: ImageButton,
        ibPlayerTwoMinus: ImageButton,) {
        tvPlayerOneHealth.slideUp(1000L, 0L)
        ibPlayerOnePlus.slideUp(1000L, 0L)
        ibPlayerOneMinus.slideUp(1000L, 0L)
        tvPlayerTwoHealth.slideDown(1000L, 0L)
        ibPlayerTwoPlus.slideDown(1000L, 0L)
        ibPlayerTwoMinus.slideDown(1000L, 0L)
    }

    private fun changePlayerColors(
        playerOneConstraint: ConstraintLayout,
        constraintLayout: ConstraintLayout,
        playerHealth: TextView,
        fabPalette: FloatingActionButton,
        fabRestart: FloatingActionButton,
        ibPlayerOnePlus: ImageButton,
        ibPlayerOneMinus: ImageButton,
        ibPlayerTwoPlus: ImageButton,
        ibPlayerTwoMinus: ImageButton,
        newColor: String) {
        val primaryColor: Int
        val textColor: Int
        val buttonColor: Int
        when (newColor) {
            "White" -> {
                primaryColor = R.color.whiteBackground
                textColor = R.color.whiteText
                buttonColor = R.color.whiteButton
            }
            "Blue" -> {
                primaryColor = R.color.blueBackground
                textColor = R.color.blueText
                buttonColor = R.color.blueButton
            }
            "Black" -> {
                primaryColor = R.color.blackBackground
                textColor = R.color.blackText
                buttonColor = R.color.blackButton
            }
            "Red" -> {
                primaryColor = R.color.redBackground
                textColor = R.color.redText
                buttonColor = R.color.redButton
            }
            else -> {
                primaryColor = R.color.greenBackground
                textColor = R.color.greenText
                buttonColor = R.color.greenButton
            }
        }
        constraintLayout.setBackgroundColor(ContextCompat.getColor(this, primaryColor))
        playerHealth.setTextColor(ContextCompat.getColor(this, textColor))
        if (constraintLayout == playerOneConstraint) {
            fabPalette.backgroundTintList = ColorStateList.valueOf(resources.getColor(buttonColor, null))
            fabPalette.imageTintList = ColorStateList.valueOf(resources.getColor(primaryColor, null))
            fabRestart.backgroundTintList = ColorStateList.valueOf(resources.getColor(buttonColor, null))
            fabRestart.imageTintList = ColorStateList.valueOf(resources.getColor(primaryColor, null))
            ibPlayerOnePlus.imageTintList = ColorStateList.valueOf(resources.getColor(textColor, null))
            ibPlayerOneMinus.imageTintList = ColorStateList.valueOf(resources.getColor(textColor, null))
        } else {
            window.statusBarColor = getColor(buttonColor)
            ibPlayerTwoPlus.imageTintList = ColorStateList.valueOf(resources.getColor(textColor, null))
            ibPlayerTwoMinus.imageTintList = ColorStateList.valueOf(resources.getColor(textColor, null))
        }
    }

    private fun resetHealth(playerHealth: TextView, healthTotal: Int) {
        playerHealth.text =  healthTotal.toString()
    }

}