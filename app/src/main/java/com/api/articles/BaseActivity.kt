package com.api.articles

import android.os.Bundle

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.api.articles.viewmodel.BaseViewModel

/**
 * Name - Base Activity
 * Purpose - Common methods can be written in base activity and which is available to extended classes
 */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {


    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }


    /**
     * Perform data binding
     */
    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = viewModel
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }
}
