package com.example.kursishi.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kursishi.R
import com.example.kursishi.app.App
import com.example.kursishi.ui.helper.SharedPreferences
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.layout_header.*

class HeaderDialog(context: Context) : AlertDialog(context) {
    private var listener: ((String, String) -> Unit)? = null
    private val pref = SharedPreferences(context)
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.item_header, null, false)

    init {
        setView(contentView)
        contentView.text_name.setText(pref.getHeaderUser())
        contentView.text_email.setText(pref.getHeaderEmail())
        contentView.btn_add_header.setOnClickListener {
            val txtName = text_name.text.toString()
            val txtEmail = text_email.text.toString()
            if(txtName.isEmpty()){
                text_name.error = "Iltimos ismingizni kiriting"
                return@setOnClickListener
            }
            if(txtEmail.isEmpty()){
                text_email.error = "Iltimos emailingizni kiriting"
                return@setOnClickListener
            }
            listener?.invoke(txtName, txtEmail)
            dismiss()
        }
        contentView.btn_calcel_header.setOnClickListener {
            dismiss()
        }
    }

    fun setOnClickListener(f: (String, String) -> Unit) {
        listener = f
    }

}