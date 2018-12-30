package com.alleluid.principium.common.items

object Debugger : BaseItem("debugger"){
    init {

    }

    override fun getUnlocalizedName(): String {
        val name = super.getUnlocalizedName()
        return "[$name]"
    }
}