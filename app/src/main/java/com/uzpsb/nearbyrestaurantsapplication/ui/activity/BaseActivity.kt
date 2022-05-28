package com.uzpsb.nearbyrestaurantsapplication.ui.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.uzpsb.nearbyrestaurantsapplication.ui.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetTitle()
        setContentView(getLayout())
        setupViews()
    }

    abstract fun getLayout(): Int
    abstract fun setupViews()

    private fun resetTitle() {
        try {
            val label = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA).labelRes
            if (label != 0) {
                setTitle(label)
            }
        } catch (e: PackageManager.NameNotFoundException) {}
    }

    fun pushFragment(layoutId: Int, fragment: BaseFragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .add(layoutId, fragment)
            .disallowAddToBackStack()
            .commitAllowingStateLoss()

    }

    fun popBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}