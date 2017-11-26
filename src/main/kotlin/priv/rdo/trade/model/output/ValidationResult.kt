package priv.rdo.trade.model.output

import com.fasterxml.jackson.annotation.JsonIgnore

sealed class ValidationResult {
    var message: String = ""

    @JsonIgnore
    abstract fun isFailure(): Boolean

    @JsonIgnore
    fun isSuccess(): Boolean = !isFailure()
}

object SuccessValidationResult : ValidationResult() {
    override fun isFailure(): Boolean = false

    init {
        message = "success!"
    }

    override fun toString(): String {
        return "SuccessValidationResult(message='$message')"
    }
}

sealed class FailureValidationResult : ValidationResult {
    val fieldName: String

    constructor(message: String, vararg fieldNames: String) {
        this.message = message
        this.fieldName = fieldNames.joinToString()
    }

    override fun isFailure(): Boolean = true

    override fun toString(): String {
        return "FailureValidationResult(message='$message', fieldName='$fieldName')"
    }
}

class FieldMissingValidationResult(vararg fieldNames: String) : FailureValidationResult("the following fields are mandatory", *fieldNames)

class GenericFailureValidationResult(message: String, vararg fieldNames: String) : FailureValidationResult(message, *fieldNames)
