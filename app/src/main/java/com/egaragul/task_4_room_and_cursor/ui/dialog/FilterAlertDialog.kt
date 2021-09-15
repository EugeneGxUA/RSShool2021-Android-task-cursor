package com.egaragul.task_4_room_and_cursor.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import com.egaragul.task_4_room_and_cursor.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object FilterAlertDialog {

    fun provideSupportAlertDialog(viewGroupContainer: ViewGroup, selectedItem: Int, callback: (Int) -> Unit): AlertDialog {
        val context = viewGroupContainer.context

        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_dialog_filters, viewGroupContainer, false)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_group)

        val dialog = MaterialAlertDialogBuilder(context)
            .setView(view)
            .create()

        if (selectedItem != -1) {
            radioGroup.check(radioGroup.children.elementAt(selectedItem).id)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            callback(group.indexOfChild(group.findViewById(checkedId)))
            dialog.dismiss()
        }

        return dialog
    }
}