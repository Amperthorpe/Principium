package com.alleluid.principium.util

import com.alleluid.principium.MOD_ID

fun langKey(type: LangType, key: String): String {
    return "$type.$MOD_ID.$key"
}

enum class LangType(typeStr: String){
    INFO("info"),
    STATUS("status"),
    CHAT("chat"),
    GUI("gui")
}
