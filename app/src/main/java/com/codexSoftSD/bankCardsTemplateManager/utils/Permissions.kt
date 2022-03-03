package com.codexSoftSD.bankCardsTemplateManager.utils

import android.Manifest

enum class Permissions(val permission: String , val relational:String) {
    SMS(Manifest.permission.READ_SMS,"SMS permission \"required for reading and analysing SMS messages\"")

}