package com.api.articles.viewmodel

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.articles.ArticleActivity
import com.api.articles.ArticleViewAdapter
import com.api.articles.business.ArticleBusiness
import com.api.articles.model.Article
import com.api.articles.model.Media
import com.api.articles.utils.TimeAgo
import java.util.ArrayList
import kotlin.math.ln
import kotlin.math.pow

class ArticleViewModel : BaseViewModel() {

    private lateinit var mArticleBusiness: ArticleBusiness
    private lateinit var mArticleViewAdapter: ArticleViewAdapter
    val mError: MutableLiveData<String> = MutableLiveData()
    val mLoading: ObservableInt = ObservableInt()

    fun setUp(articleBusiness: ArticleBusiness, articleViewAdapter: ArticleViewAdapter) {
        mArticleBusiness = articleBusiness
        mArticleViewAdapter = articleViewAdapter
    }


    fun getNumberFormat(count: Int): String {
        if (count < 1000) return count.toString()
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    fun articleService(page: Int, limit: Int) {
        mLoading.set(View.VISIBLE)
        mArticleBusiness.articleService(page, limit, onSuccess, onError)
    }

    private var onSuccess: (List<Article>, Int) -> Unit = { articles: List<Article>, page: Int ->
        mLoading.set(View.GONE)
        when {
            page > 1 -> mArticleViewAdapter.addArticle(ArrayList(articles))
            else -> mArticleViewAdapter.setArticle(ArrayList(articles))
        }
    }

    fun isMediaNull(media: List<Media>): Boolean = media.isNullOrEmpty()

    fun getTimeAgo(date: String): String {
        return TimeAgo().getTimeAgo(date)
    }

    private var onError: (String) -> Unit = { error ->
        mLoading.set(View.GONE)
        mError.value = error
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setOnScrollListener(recyclerView: RecyclerView) {

        recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLastVisible(recyclerView)) {
                    val pageCount = (mArticleViewAdapter.itemCount / 10) + 1
                    articleService(pageCount, ArticleActivity.LIMIT)
                }
            }
        })
    }

    private fun isLastVisible(rv: RecyclerView): Boolean {
        val layoutManager = rv.layoutManager as LinearLayoutManager?
        var pos = 0
        if (layoutManager != null) {
            pos = layoutManager.findLastCompletelyVisibleItemPosition()
        }
        val numItems = mArticleViewAdapter.itemCount
        return pos >= numItems - 1
    }
}