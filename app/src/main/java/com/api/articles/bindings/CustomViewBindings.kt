package com.api.articles.bindings

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class CustomViewBindings {

    companion object {
        @JvmStatic
        @BindingAdapter("image_url")
        fun loadImage(view: ImageView, imageUrl: String) {
            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(view.context)
                    .load(imageUrl).apply(RequestOptions())
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("profile_image")
        fun loadImage(view: CircleImageView, imageUrl: String) {
            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(view.context)
                    .load(imageUrl).apply(RequestOptions().circleCrop())
                    .into(view)
            }
        }
    }


}