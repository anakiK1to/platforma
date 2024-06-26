package kodeinDi

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import screens.loginScreen.LoginScreenController
import screens.mainScreen.floorAllocation.FloorAllocationController
import screens.mainScreen.menuConfiguration.MenuConfigurationController
import screens.mainScreen.registrationScreen.RegistrationController
import storage.ApplicationLocalStorage

object DiContainer {
    val di = DI {
        bindSingleton {
            LoginScreenController(this@DiContainer)
        }
        bindSingleton {
            MenuConfigurationController()
        }
        bindSingleton {
            RegistrationController(this@DiContainer)
        }
        bindSingleton {
            FloorAllocationController()
        }
    }
}