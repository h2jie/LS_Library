package com.example.hh.salle_library

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_item.view.*
import java.io.File

/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
class BookAdapter(var myBookList : ArrayList<Book>, var mContext : Context) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    class MyViewHolder (v : View) : RecyclerView.ViewHolder(v){
        var title = v.item_title
        var author = v.item_author
        var desc = v.item_desc
        var date = v.item_date
        var bookImg = v.item_image

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder{
        var itemVeiw = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.book_item, parent, false)
        return MyViewHolder(itemVeiw)
    }

    override fun getItemCount(): Int {
        return myBookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var book = myBookList[position]
        holder.title.text = book.bookTitle
        holder.author.text = book.bookAuthor
        holder.date.text = book.bookDate
        holder.desc.text = book.bookDesc
        Glide.with(mContext).load(File(book.imageUrl.path)).into(holder.bookImg)

    }

}