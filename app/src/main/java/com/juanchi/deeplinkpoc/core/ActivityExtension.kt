package com.juanchi.deeplinkpoc.core

import com.juanchi.deeplinkpoc.ui.home.HomeActivity

fun AuthActivity.isLastActivityInStack(): Boolean {
    return isTaskRoot &&  this !is HomeActivity
}