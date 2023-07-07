package com.alvarez.sergio.actraining.data.repositories

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}
