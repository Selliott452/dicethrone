package com.elliott.dicethrone.web.components

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class DiceRoller : HorizontalLayout() {

    private val diceValues: MutableList<Dice> = mutableListOf(
        Dice(0, false),
        Dice(0, false),
        Dice(0, false),
        Dice(0, false),
        Dice(0, false)
    )

    private val diceButtons = listOf(Button(),Button(),Button(),Button(),Button())
    private val diceLayout: HorizontalLayout = HorizontalLayout().apply {
        diceButtons.forEach {
            this.add(it)
        }
    }

    private val rollButton: Button = Button().apply {
        this.icon = VaadinIcon.REFRESH.create()
        this.className = "rollButton"
        this.addClickListener { roll() }
    }

    init {
        diceButtons.forEachIndexed { index, button ->
            button.apply {
                this.className = "dice"
                this.addClickListener {
                    diceValues[index].locked = !diceValues[index].locked

                    if(diceValues[index].locked) {
                        this.addClassName("locked")
                    } else {
                        this.removeClassName("locked")
                    }
                }
            }
        }

        this.add(diceLayout, rollButton)

        this.className = "roller"
        this.isPadding = true
    }

    private fun roll() {
        diceValues.forEachIndexed { index, dice ->
            if(!dice.locked) {
                dice.value = (1..6).random()
                diceButtons[index].text = dice.value.toString()
            }
        }
    }
}

data class Dice(
    var value: Int,
    var locked: Boolean
)