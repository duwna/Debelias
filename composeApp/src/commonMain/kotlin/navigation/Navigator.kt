package navigation

import androidx.navigation.NavController
import presentation.Screen

interface Navigator {

    fun setController(navController: NavController)

    fun navigate(screen: Screen)

    fun navigate(route: String)

    fun popBackStack()

    fun clear()

}

class NavigatorImpl : Navigator {

    private var navController: NavController? = null

    override fun setController(navController: NavController) {
        this.navController = navController
    }

    override fun navigate(screen: Screen) {
        navController?.navigate(screen.route)
    }

    override fun navigate(route: String) {
        navController?.navigate(route)
    }

    override fun popBackStack() {
        navController?.popBackStack()
    }

    override fun clear() {
        navController = null
    }
}
