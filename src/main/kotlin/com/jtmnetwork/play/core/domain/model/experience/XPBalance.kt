package com.jtmnetwork.play.core.domain.model.experience

data class XPBalance(var level: Int = 1, var experience: Int = 0) {

    fun addExperience(amount: Int) {
        experience += amount
    }

    fun removeExperience(amount: Int) {
        experience -= amount
    }
}