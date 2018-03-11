package com.example.hh.salle_library

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_item.view.*

/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
class RecyclerAdapter(private val books : ArrayList<Book>) : RecyclerView.Adapter<RecyclerAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.BookHolder{
        val inflatedView = parent.inflate(R.layout.book_item, false)
        return BookHolder(inflatedView)    }

    override fun getItemCount() = books.size


    override fun onBindViewHolder(holder: RecyclerAdapter.BookHolder, position: Int) {
        val itemBook = books[position]
        holder.bindBook(itemBook)

    }

    class BookHolder(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view : View = v
        private var book : Book? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.d("RecylerView","CLICK")

        }

        companion object {
            private val PHOTO_KEY = "PHOTO"
        }

        fun bindBook(book : Book){
            this.book = book
            Picasso.get().load(book.imageUrl).into(view.item_image)
            view.item_author.text = book.bookAuthor
            view.item_title.text = book.bookTitle
            view.item_date.text = book.bookDate
        }


    }
}