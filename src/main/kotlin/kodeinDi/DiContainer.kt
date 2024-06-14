package kodeinDi

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import screens.loginScreen.LoginScreenController

object DiContainer {
    val di = DI {
        bindSingleton {
            LoginScreenController(this@DiContainer)
        }
    }
}