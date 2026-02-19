package com.android.nesinecasestudy.ui.listscreen.xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.android.nesinecasestudy.R
import com.android.nesinecasestudy.databinding.ItemPostBinding
import com.android.nesinecasestudy.domain.utils.ImageConstants
import com.android.nesinecasestudy.ui.listscreen.model.PostItemUiModel

class PostAdapter(
    private val onClick: (String, String) -> Unit
) : ListAdapter<PostItemUiModel, PostAdapter.PostViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<PostItemUiModel>() {
            override fun areItemsTheSame(
                oldItem: PostItemUiModel,
                newItem: PostItemUiModel
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PostItemUiModel,
                newItem: PostItemUiModel
            ) = oldItem == newItem
        }
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostItemUiModel) {

            binding.titleText.text = item.title
            binding.bodyText.text = item.body

            val imageUrl = ImageConstants.buildGrayscaleImage(item.id)

            binding.postImage.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error_placeholder)
                transformations(CircleCropTransformation())
            }

            binding.root.setOnClickListener {
                onClick(item.title, item.body)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}