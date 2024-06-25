package com.goally.task.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goally.task.R
import com.goally.task.databinding.RowViewFilmsBinding
import com.goally.task.domain.models.FilmDataModelResult
import com.goally.task.domain.repository.constant.Constants


class FilmAdapter(var context: Context) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    private var filmList: ArrayList<FilmDataModelResult> = ArrayList()
    private lateinit var binding: RowViewFilmsBinding

    fun addData(filmList: ArrayList<FilmDataModelResult>) {
        this.filmList = filmList
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowViewFilmsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        this.context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if (filmList.isEmpty()) {
            binding.noData.visibility = View.VISIBLE
            binding.designList.visibility = View.GONE
        } else {
            val list = filmList[position]

            binding.noData.visibility = View.GONE
            binding.designList.visibility = View.VISIBLE
            val imageUrl = Constants.filmPosterBaseUrl + list.poster_path

            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_android_black_24dp)
                .error(R.drawable.ic_android_black_24dp)
                .into(holder.bind.filmImg)

            holder.bind.filmTitle.text = list.name.ifEmpty { "Film Title" }
            holder.bind.filmDesc.text = list.overview.ifEmpty { "Description" }
        }
    }

    override fun getItemCount(): Int = if (filmList.size == 0) {
        1
    } else filmList.size

    class ViewHolder(val bind: RowViewFilmsBinding) : RecyclerView.ViewHolder(bind.root)
}