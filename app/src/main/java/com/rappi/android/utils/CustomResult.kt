package com.rappi.android.utils

sealed class CustomResult<out T> {
    data class OnSuccess<out T>(val data: T) : CustomResult<T>()
    data class OnError<out T>(val error: CustomError) : CustomResult<T>()
}


open class CustomError (
    val code: Int? = 0,
    val message: String? = null,
    val cause: Throwable? = null
)


open class ErrorThrowable(
    private val code: Int? = 0,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message, cause) {
    fun toError(): CustomError = CustomError(code, message, cause)
}


class NetworkError(code: Int? = 0, message: String? = null, cause: Throwable? = null):
    CustomError(code, message ?: "Usted no tiene conexión a internet, active los datos", cause)

class HttpError(code: Int? = 0, message: String? = null, cause: Throwable? = null):
    CustomError(code, message, cause)

class UnknownError(code: Int? = 0, message: String? = null, cause: Throwable? = null):
    CustomError(code, message ?: "Unknown error", cause)

class CustomNotFoundError(code: Int? = 0, message: String? = null, cause: Throwable? = null):
    CustomError(code, message ?: "Data not found", cause)
