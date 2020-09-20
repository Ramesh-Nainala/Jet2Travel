package com.api.articles


import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.api.articles.databinding.ArticleViewBinding
import com.api.articles.model.Article
import com.api.articles.viewmodel.ArticleViewModel

/**
 * Name - Item view Adapter
 * Purpose - Bind the item to Recycler view
 */
class ArticleViewAdapter(
    @param:LayoutRes private val layoutId: Int, private var dataSet: ArrayList<Article>,
    private val viewModel: ArticleViewModel
) : RecyclerView.Adapter<ArticleViewAdapter.GenericViewHolder>() {



    private fun getLayoutIdForPosition(position: Int): Int = layoutId

    override fun getItemCount() : Int = dataSet.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val binding =
            DataBindingUtil.inflate<ArticleViewBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun setArticle(data: ArrayList<Article>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun addArticle(data:ArrayList<Article>){
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    inner class GenericViewHolder(private val binding: ArticleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.setVariable(BR.article, dataSet[position])
            binding.setVariable(BR.articleVM, viewModel)
            binding.setVariable(BR.position,position)
            binding.executePendingBindings()
        }

    }

}
