package com.api.articles

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.articles.business.ArticleBusiness
import com.api.articles.databinding.ActivityArticleBinding
import com.api.articles.apiInterface.RetrofitHelper
import com.api.articles.viewmodel.ArticleViewModel
import com.api.articles.viewmodel.ViewModelProviderFactory

class ArticleActivity : BaseActivity<ActivityArticleBinding, ArticleViewModel>() {

    private lateinit var mArticleViewAdapter: ArticleViewAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialSetUp()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initialSetUp() {
        mArticleViewAdapter = ArticleViewAdapter(
            R.layout.article_view,
            ArrayList(), mViewModel
        )
        mViewModel.setUp(ArticleBusiness(RetrofitHelper()), mArticleViewAdapter)
        mViewDataBinding.recycler.adapter = mArticleViewAdapter
        mViewDataBinding.recycler.layoutManager = LinearLayoutManager(this)

        mViewModel.setOnScrollListener(mViewDataBinding.recycler)

        mViewModel.articleService(PAGE, LIMIT)
        mViewModel.mError.observe(this, Observer<String> { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }


    override val bindingVariable: Int
        get() = BR.articleVM

    override val layoutId: Int
        get() = R.layout.activity_article

    override val viewModel: ArticleViewModel
        get() {
            val viewModelProviderFactory = ViewModelProviderFactory(ArticleViewModel())
            return ViewModelProviders.of(this, viewModelProviderFactory)
                .get(ArticleViewModel::class.java)
        }

    companion object {
        const val PAGE: Int = 1
        const val LIMIT = 10
    }

}
