package com.api.articles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * View Model Provider Factory
 * @param <V>
 */


@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory<T>(private val viewModel: T) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ArticleViewModel::class.java)){
            return viewModel as T
        }

        throw NullPointerException("Unknown class name")
    }
}
