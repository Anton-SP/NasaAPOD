package com.example.nasaapod.ui.notepad.TouchHelper

interface TouchHelperAdapter {

    fun onItemMoved(from: Int, to : Int)

    fun onItemDismissed(position : Int)
}