package com.example.zivotot_e_krug.ui.users.adult.adapters

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.zivotot_e_krug.R

class TaskBinding {

    companion object{

        @BindingAdapter("setActiveColor")
        @JvmStatic
        fun setActiveColor(imageView: ImageView, active : String){
            if(active == "ACTIVE"){
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.Yellow
                    )
                )

            }
            else if(active == "PENDING"){
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.Red
                    )
                )
            }
            else {
                imageView.setColorFilter(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.chip_text_color
                    )
                )
            }
        }
    }
}