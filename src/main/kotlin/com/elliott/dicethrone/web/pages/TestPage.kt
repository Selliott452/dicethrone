package com.elliott.dicethrone.web.pages

import com.elliott.dicethrone.web.components.Counter
import com.elliott.dicethrone.web.components.DiceRoller
import com.elliott.dicethrone.web.components.EffectsTracker
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.stereotype.Component

@Route(value = "", layout = MainLayout::class)
@Component
@UIScope
class TestPage(
) : VerticalLayout() {


    init {
        this.className = "baseLayout"
    }

    override fun onAttach(attachEvent: AttachEvent?) {
        super.onAttach(attachEvent)

        this.add(
            HorizontalLayout(
                Counter("Health", 40).apply {
                    this.addClassName("health")
                },
                Counter("Combo Points", 1).apply {
                    this.addClassName("comboPoints")
                }
            ).apply {
                this.className = "counters"
            })

        this.add(EffectsTracker())
        this.add(DiceRoller())

    }
}