package com.alleluid.principium.common.misc

abstract class MachineFields


data class SmelterFields(val potential: Int, val capacity: Int, val progress: Int) : MachineFields() {
    constructor() : this(0, 0, 0)
}