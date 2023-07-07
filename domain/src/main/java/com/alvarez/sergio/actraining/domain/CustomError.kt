package com.alvarez.sergio.actraining.domain

sealed interface CustomError {
    class Server(val code: Int) : CustomError
    object Connectivity : CustomError
    class Unknown(val message: String) : CustomError
}
