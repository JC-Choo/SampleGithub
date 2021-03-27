package dev.chu.githubsample

import android.content.res.Resources
import dev.chu.githubsample.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.chu.chulibrary.util.extensions.toast
import dev.chu.chulibrary.arch.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun connEssentialViews(): EssentialView {
        return buildEssentialView {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            navController = navHostFragment.navController
            navController.addOnDestinationChangedListener { _, destination, _ ->
                val dest: String = try {
                    resources.getResourceName(destination.id)
                } catch (e: Resources.NotFoundException) {
                    destination.id.toString()
                }

                toast("Navigated to $dest")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}