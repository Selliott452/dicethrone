package com.elliott.dicethrone

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.startup.VaadinAppShellInitializer
import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Theme("dicethrone")
class DicethroneApplication : AppShellConfigurator

fun main(args: Array<String>) {
	runApplication<DicethroneApplication>(*args)
}
