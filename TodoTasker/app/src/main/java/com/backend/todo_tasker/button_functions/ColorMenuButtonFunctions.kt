package com.backend.todo_tasker.button_functions

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.backend.todo_tasker.*
import com.backend.todo_tasker.database.Category
import com.backend.todo_tasker.popup_window.PopUpWindowInflater
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import petrov.kristiyan.colorpicker.ColorPicker

class ColorMenuButtonFunctions {

    fun saveProjectCreation(view: View)
    {
        var name = view.rootView.findViewById<EditText>(R.id.edittext_name).text.toString()
        var color = view.rootView.findViewById<ImageButton>(R.id.image_btn_color).imageTintList?.defaultColor

        GlobalScope.launch {
            sharedCategoryDbLock.acquire()
            var id = dbCategoryClass.getLastEntry(categoryDb).uid + 1
            dbCategoryClass.addToDb(categoryDb, Category(id, name, color, null))
            sharedCategoryDbLock.release()
        }

        PopUpWindowInflater().getInstance().dismissProjectWindow()
    }

    fun cancelProjectCreation()
    {
        PopUpWindowInflater().getInstance().dismissProjectWindow()
    }

    fun pickColorFunction(act: Activity, view: View)
    {
        val color_button = view.findViewById<ImageButton>(R.id.image_btn_color)

        color_button.setOnClickListener {
            val colorPicker = ColorPicker(act)
            colorPicker.setRoundColorButton(true)
            colorPicker.show()
            colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
                override fun onChooseColor(position: Int, color: Int)
                {
                    color_button.setColorFilter(color)
                }

                override fun onCancel() {

                }
            })
        }
    }


}