package com.example.nasaroverapp.adapter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaroverapp.R
import com.example.nasaroverapp.databinding.PhotoItemRowBinding
import com.example.nasaroverapp.databinding.PopupBinding
import com.example.nasaroverapp.model.PhotoByRover

class PhotoAdapter(var photos: ArrayList<PhotoByRover>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    lateinit var dataBinding: PopupBinding
    lateinit var dialog: Dialog

    class PhotoViewHolder(var view: PhotoItemRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<PhotoItemRowBinding>(
            inflater,
            R.layout.photo_item_row,
            parent,
            false
        )

        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.popup,
            parent,
            false
        )
        dialog = Dialog(parent.context)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataBinding.root)

        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.view.photo = photos[position]
        holder.view.item = this

    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun onItemClick(view: View, photo: PhotoByRover) {
        dataBinding.photo = photo
        dialog.show()

    }

}