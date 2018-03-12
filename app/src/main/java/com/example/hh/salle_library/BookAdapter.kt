package com.example.hh.salle_library

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_item.view.*
import java.io.File
import java.net.URL

/**
 * Created by Bhupinder Kumar on 11/03/2018.
 */
class BookAdapter(var myBookList : ArrayList<Book>, var mContext : Context) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    companion object {
        var imgUrl : URL? = null
        var itemAutor : String? = null
        var itemDesc : String? = null
        var itemDate : String? = null
        var itemTitle : String? = null

    }


    class MyViewHolder (v : View) : RecyclerView.ViewHolder(v), View.OnClickListener{
        var title = v.item_title
        var author = v.item_author
        var desc = v.item_desc
        var date = v.item_date
        var bookImg = v.item_image
        var addbtn = v.item_add_btn
        var mUser = FirebaseAuth.getInstance().currentUser
        var sharedPref = v.context.getSharedPreferences(mUser!!.uid,Context.MODE_PRIVATE)
        var editor = sharedPref.edit()

        init {
            addbtn.setOnClickListener(this)
        }

        override fun onClick(p: View?) {
            var mBook : Book? = null
            mBook = Book(imgUrl!!, itemTitle!!, itemAutor!!, itemDate!!, itemDesc!!)
            if (mBook != null) {

                var mBooks : ArrayList<Book>
                //mBooks = getSavedObjectFromPreference(p!!.context,"mPrefData",FirebaseAuth.getInstance().currentUser!!.uid, ArrayList<>)


                saveObjectToSharedPreference(p!!.context, "mPrefData",mUser!!.uid,mBook)


                /*var gson = Gson()
                var serializedObject = gson.toJson(mBook)

                var mBookJsonStr = Gson().toJson(mBook)


                //saveObjectToSharedPreference(p!!.context)




                editor.putString(mUser!!.uid,mBookJsonStr)
                editor.commit()*/
                Toast.makeText(p!!.context,"Funciona?",Toast.LENGTH_LONG).show()
            }
        }
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

        imgUrl = book.imageUrl
        itemAutor = book.bookAuthor
        itemDesc  = book.bookDesc
        itemDate  = book.bookDate
        itemTitle = book.bookTitle

    }

}