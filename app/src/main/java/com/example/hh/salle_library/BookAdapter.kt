package com.example.hh.salle_library

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.book_item.view.*
import java.io.File
import java.io.IOException
import java.net.URL

/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
class BookAdapter(var myBookList : ArrayList<Book>, var mContext : Context, var from : String, var mCallBack : taskCompletedListenner?) : RecyclerView.Adapter<BookAdapter.MyViewHolder>(){

    class MyViewHolder (v : View) : RecyclerView.ViewHolder(v){
        var title = v.item_title
        var author = v.item_author
        var desc = v.item_desc
        var date = v.item_date
        var bookImg = v.item_image
        var addbtn = v.item_add_btn
        var delBtn = v.item_delete_btn
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
        Glide.with(mContext).load(book.imageUrl).into(holder.bookImg)


        if (from.equals("main")){
            holder.addbtn.setOnClickListener(View.OnClickListener { v->
                val mUser = FirebaseAuth.getInstance().currentUser
                val sharedPref = mContext.getSharedPreferences(mUser!!.uid,Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                var mBook : Book? = null
                mBook = Book(book.imageUrl, book.bookTitle, book.bookAuthor, book.bookDate, book.bookDesc)


                var mBooks = ArrayList<Book>()
                try {
                    mBooks = ObjectSerializer.deserialize(sharedPref.getString("books",ObjectSerializer.serialize(ArrayList<Book>()))) as ArrayList<Book>
                    if (!mBooks.contains(mBook)){
                        mBooks.add(mBook)
                        Toast.makeText(v!!.context,"${mBook.bookTitle} added to favorite list",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(v!!.context,"${mBook.bookTitle} already in favorite list",Toast.LENGTH_SHORT).show()
                    }

                    editor.putString("books",ObjectSerializer.serialize(mBooks))
                    editor.commit()

                }catch (e: IOException){
                    print(e.message)
                }
            })

        }else{
            holder.delBtn.visibility= View.VISIBLE
            holder.addbtn.visibility = View.GONE
            holder.delBtn.setOnClickListener(View.OnClickListener { v->
                val mUser = FirebaseAuth.getInstance().currentUser
                val sharedPref = mContext.getSharedPreferences(mUser!!.uid,Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                var mBookToRemove : Book? = null
                mBookToRemove = Book(book.imageUrl, book.bookTitle, book.bookAuthor, book.bookDate, book.bookDesc)

                var mBooks = ArrayList<Book>()
                try {
                    mBooks = ObjectSerializer.deserialize(sharedPref.getString("books",ObjectSerializer.serialize(ArrayList<Book>()))) as ArrayList<Book>
                    mBooks.remove(mBookToRemove)
                    editor.putString("books",ObjectSerializer.serialize(mBooks))
                    editor.commit()
                    Toast.makeText(v!!.context,"Removed ${mBookToRemove.bookTitle}",Toast.LENGTH_SHORT).show()
                    mCallBack!!.taskCompleted(mBooks)

                }catch (e: IOException){
                    print(e.message)
                }
            })
        }



        /*imgUrl = book.imageUrl
        itemAutor = book.bookAuthor
        itemDesc  = book.bookDesc
        itemDate  = book.bookDate
        itemTitle = book.bookTitle*/

    }
}