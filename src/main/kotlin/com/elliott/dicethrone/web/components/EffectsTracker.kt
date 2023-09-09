package com.elliott.dicethrone.web.components

import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.contextmenu.ContextMenu
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.server.InputStreamFactory
import com.vaadin.flow.server.StreamResource

class EffectsTracker : HorizontalLayout() {

    private val currentEffects = mutableMapOf<Effect, Int>()

    private val effects = mutableMapOf<Effect, Avatar>()

    private val iconTray = HorizontalLayout().apply {
        this.className = "effectTray"
    }

    private val addEffectButton = Button().apply {
        this.icon = VaadinIcon.PLUS.create()
        this.className = "addEffect"
    }

    val addMenu = ContextMenu().apply {
        this.target = addEffectButton
        this.isOpenOnClick = true
        Effect.values().forEach { effect ->
            this.addItem(effect.effectName
            ) {
                if (currentEffects.containsKey(effect)) {
                    currentEffects[effect] = currentEffects[effect]?.plus(1) ?: 0
                } else {
                    currentEffects[effect] = 1
                }
                update()
            }
        }
    }


    init {
        this.className = "effectTracker"

        this.add(iconTray)
        this.add(addEffectButton)

    }

    fun update() {
        currentEffects.keys.minus(effects.keys).forEach { effect ->
            val effectAvatar = Avatar(effect.effectName).apply {
                this.className = "effectAvatar"
                this.imageResource = StreamResource(effect.icon,
                    InputStreamFactory { javaClass.getResourceAsStream("/images/${effect.icon}") })
            }
            effects[effect] = effectAvatar
            val menu = ContextMenu()
            menu.target = effectAvatar
            menu.isOpenOnClick = true
            menu.add(
                getInfoLayout(effect, menu)
            )
            iconTray.add(effectAvatar)
        }

        effects.keys.minus(currentEffects.keys).forEach {
            iconTray.remove(effects[it])
            effects.remove(it)
        }
    }

    fun getInfoLayout(effect: Effect, menu: ContextMenu) =
        VerticalLayout().apply {
            this.add(Div().apply {
                this.text = effect.description
            })

            this.add(HorizontalLayout().apply {
                this.className = "removeEffectLayout"
                this.add(Button().apply {
                    this.text = "Remove"
                    this.addClickListener {
                        currentEffects.remove(effect)
                        update()
                        menu.close()
                    }
                })
            })
        }
}


enum class Effect(
    val effectName: String,
    val description: String,
    val icon: String,
    val stackLimit: Int
) {

    KNOCK_DOWN("Knock Down", "To remove this token, a player afflicted with it must spend 2 CP before the start of their Offensive Roll Phase.  If the player does not, they must skip their Offensive Roll Phase and then remove this token.", "knockdown.png", 1),
    DELAYED_POISON("Delayed Poison", "A player inflicted with this token removed it at the conclusion of their turn and receives 3 dmg.", "delayedpoison.png", 1),
    NINJUTSU("Ninjutsu", "After Attacking, a player with this token may spend it and roll 1 dice: on 1-3, add 1 dmg.  On 4-5 add 2 dmg, on 6, choose to either add 2 dmg, inflicted Delayed Poison, or make your attack undefendable.", "ninjutsu.png", 1)

}
