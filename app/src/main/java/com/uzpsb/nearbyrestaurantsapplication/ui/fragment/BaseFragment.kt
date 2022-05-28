package com.uzpsb.nearbyrestaurantsapplication.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uzpsb.nearbyrestaurantsapplication.ui.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment :Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    abstract fun setupViews()

    fun getBaseActivity(run: (BaseActivity) -> (Unit)) {
        (activity as? BaseActivity).let {
            it?.let { it1 ->
                run(it1)
            }
        }
    }
}