package kodeinDi

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import screens.loginScreen.LoginScreenController
import storage.ApplicationLocalStorage

object DiContainer {
    val di = DI {
        bindSingleton {
            LoginScreenController(this@DiContainer)
        }
        bindSingleton {
            ApplicationLocalStorage()
        }
    }
}