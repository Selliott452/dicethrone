package com.elliott.dicethrone.web.components

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.IntegerField

class Counter(
    private val name: String,
    private var count: Int = 0
) : VerticalLayout() {

    init {
        this.addClassName("counter")
        this.isSpacing = false
    }

    private val downButton: Button = Button().apply {
        this.icon = VaadinIcon.ANGLE_LEFT.create()

        this.addClickListener {
            count -= 1
            updateField()
        }
    }

    private val upButton: Button = Button().apply {
        this.icon = VaadinIcon.ANGLE_RIGHT.create()

        this.addClickListener {
            count += 1
            updateField()
        }
    }

    private val valueField: IntegerField = IntegerField().apply {
        this.step = 1
        this.min = 0
        this.addValueChangeListener {
            count = it.value
        }
        this.addClassName("counterField")
    }

    init {
        this.add(Div().apply { this.text = name })
        this.add(HorizontalLayout(downButton, valueField, upButton))
        updateField()

    }

    private fun updateField() {
        valueField.value = count
    }

}