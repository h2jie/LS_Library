package com.example.viewaddfav;

import android.content.Context;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Type;

/**
 * Created by Bhupinder Kumar on 10/03/2018.
 */

public class ListAddFavView extends RelativeLayout implements View.OnClickListener {
    private TextView itemTitle, itemAuthor, itemDate, itemDesc;
    private ImageView itemImg;
    private ImageButton btnAdd, btnDelete;

    private OnButtonClickedListener mCallback = null;

    public interface OnButtonClickedListener{
        public void onButtonClicked(ListAddFavView source, String text);
    }

    public ListAddFavView(Context context) {
        super(context);
        init(null,0);
    }

    public ListAddFavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(null,0);
    }

    public ListAddFavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    public ListAddFavView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(AttributeSet attrs, int defStyle){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.list_add_fav_view, this);

        itemTitle = findViewById(R.id.item_title);
        itemAuthor = findViewById(R.id.item_author);
        itemDate = findViewById(R.id.item_date);
        itemDesc = findViewById(R.id.item_desc);
        itemImg = findViewById(R.id.item_image);
        btnAdd = findViewById(R.id.item_add_btn);
        btnDelete = findViewById(R.id.item_delete_btn);


        TypedArray ae = getContext().obtainStyledAttributes(attrs,R.styleable.ListAddFavViewNewOne,defStyle,0);

        //TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.List,defStyle,0);

        int textSize = ae.getDimensionPixelOffset(R.styleable.ListAddFavViewNewOne_textSizeAll,16);
        if (textSize>0){setTextSize(textSize);}

        int titleTextSize = ae.getDimensionPixelOffset(R.styleable.ListAddFavViewNewOne_itemTitleSize,16);
        if (titleTextSize>0){setTitleTextSize(titleTextSize);}

        int addVisibilty = ae.getInt(R.styleable.ListAddFavViewNewOne_itemVisibilityAddBtn,1);
        setVisibilityAddBtn(addVisibilty);

        int deleteVisibility = ae.getInt(R.styleable.ListAddFavViewNewOne_itemVisibilityDeleteBtn,0);
        setVisibilityDeleteBtn(deleteVisibility);

        CharSequence authorText = ae.getString(R.styleable.ListAddFavViewNewOne_authorText);
        //System.out.println(authorText.toString());
        if (authorText != null) {setTextAuthor(authorText.toString());}


        CharSequence titleText = ae.getString(R.styleable.ListAddFavViewNewOne_titleText);
        if (titleText != null) {setTitleText(titleText.toString()); }

        CharSequence dateText = ae.getText(R.styleable.ListAddFavViewNewOne_dateText);
        if (dateText != null) {
            setDateText(dateText.toString());
        }
        CharSequence descText = ae.getText(R.styleable.ListAddFavViewNewOne_descText);
        if (descText != null) {
            setDescText(descText.toString());
        }

        ae.recycle();

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    private void setDescText(String s) {
        itemDesc.setText(s);
        invalidate();
        requestLayout();
    }

    private void setDateText(String s) {
        itemDate.setText(s);
        invalidate();
        requestLayout();
    }

    private void setTitleText(String s) {
        itemTitle.setText(s);
        invalidate();
        requestLayout();
    }

    private void setTextAuthor(String s) {
        itemAuthor.setText(s);
        invalidate();
        requestLayout();
    }

    private void setVisibilityAddBtn(int addVisibilty) {
        if (addVisibilty==1){
            btnAdd.setVisibility(VISIBLE);
        }else
        {
            btnAdd.setVisibility(INVISIBLE);
        }
        invalidate();
        requestLayout();
    }

    private void setVisibilityDeleteBtn(int deleteVisibility) {
        if (deleteVisibility==1){
            btnDelete.setVisibility(VISIBLE);
        }else
        {
            btnDelete.setVisibility(INVISIBLE);
        }
        invalidate();
        requestLayout();
    }

    private void setTextSize(int textSize) {
        itemAuthor.setTextSize(textSize);
        itemDate.setTextSize(textSize);
        itemDesc.setTextSize(textSize);
        invalidate();
        requestLayout();
    }

    private void setTitleTextSize(int titleTextSize) {
        itemTitle.setTextSize(titleTextSize);
        invalidate();
        requestLayout();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_add_btn){
            if (mCallback!=null){
                mCallback.onButtonClicked(this, itemTitle.getText().toString());
            }
        }else if (view.getId() == R.id.item_delete_btn){
            if (mCallback!=null){
                mCallback.onButtonClicked(this, itemTitle.getText().toString());
            }
        }
    }
}
