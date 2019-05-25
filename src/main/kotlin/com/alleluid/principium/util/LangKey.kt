package com.alleluid.principium.util

import com.alleluid.principium.MOD_ID

fun langKey(type: LangType, key: String): String {
    return "$type.$MOD_ID.$key"
}

enum class LangType{
    INFO,
    STATUS,
    CHAT,
    GUI;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}
