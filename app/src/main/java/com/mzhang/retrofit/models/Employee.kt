package com.mzhang.retrofit.models

data class Employee(
    val id: Long? = null,
    val name: String,
    val role: String
) {
    override fun toString(): String {
        return "Employee: [id: $id, mame: $name, role: $role]"
    }
}