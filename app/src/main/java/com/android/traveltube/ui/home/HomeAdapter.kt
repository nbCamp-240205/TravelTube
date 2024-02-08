package com.android.traveltube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.traveltube.databinding.RecyclerviewHomeSelectVideoBinding
import com.android.traveltube.model.VideoDetailModel
import com.bumptech.glide.Glide

class HomeAdapter(private val onItemClicked: (VideoDetailModel) -> Unit) :
    ListAdapter<VideoDetailModel, HomeAdapter.Holder>(DocumentDiffCallback()) {

    class DocumentDiffCallback : DiffUtil.ItemCallback<VideoDetailModel>() {
        override fun areItemsTheSame(
            oldItem: VideoDetailModel,
            newItem: VideoDetailModel
        ): Boolean {
            // 객체의 고유 식별자를 비교
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: VideoDetailModel,
            newItem: VideoDetailModel
        ): Boolean {
            // TODO : 그냥 ==은 왜 안되는지 확인하기
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewHomeSelectVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position) // ListAdapter에서는 getItem() 메서드 사용
        holder.bind(item)
    }

    inner class Holder(private val binding: RecyclerviewHomeSelectVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideoDetailModel) {
            Glide.with(itemView.context)
                .load(data.thumbNailUrl)
                .into(binding.ivThumbnail)
            binding.ivThumbnail.clipToOutline = true
            binding.tvTitle.text = data.title
            binding.tvChannelTitle.text = data.channelTitle
            binding.root.setOnClickListener {
                onItemClicked(data)
            }
            binding.tvDate.text = data.publishTime.toString()
        }
    }
}