package com.api.articles.business

import com.api.articles.apiInterface.ArticleService
import com.api.articles.model.Article
import com.api.articles.apiInterface.RetrofitHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleBusiness(private val mRetrofitHelper: RetrofitHelper) {

    fun articleService(
        page: Int, limit: Int,
        onSuccess: (List<Article>, Int) -> Unit,
        onError: (String) -> Unit
    ) {

        val articleService = mRetrofitHelper.createService(ArticleService::class.java)
        articleService.getArticles(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Article>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(articleResult: List<Article>) {
                    onSuccess(articleResult, page)
                }

                override fun onError(e: Throwable) {
                    onError(e.message!!)
                }

                override fun onComplete() {

                }
            })

    }
}