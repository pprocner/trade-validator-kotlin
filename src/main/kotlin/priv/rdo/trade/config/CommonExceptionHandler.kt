package priv.rdo.trade.config

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.google.common.collect.ImmutableMap
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class CommonExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handle(e: HttpMessageNotReadableException): Map<String, Any> {
        if (e.rootCause == null) {
            return ImmutableMap.of<String, Any>("message", "body cannot be empty!")
        }

        return when {
            e.rootCause is InvalidFormatException -> ImmutableMap.of<String, Any>("message", buildMessageForInvalidFormat(e))
            else -> ImmutableMap.of<String, Any>("message", StringUtils.split(e.rootCause.message, ":")[0])
        }

    }

    private fun buildMessageForInvalidFormat(e: HttpMessageNotReadableException): String {
        val ife = e.rootCause as InvalidFormatException

        val msg = StringBuilder("Invalid value: " + ife.value)

        val targetType = ife.targetType
        if (targetType.isEnum) {
            val joiner = StringJoiner(", ", ". Possible values: [", "]")

            val constants = ife.targetType.enumConstants as Array<Enum<*>>
            constants.forEach { constant -> joiner.add(constant.name) }

            msg.append(joiner.toString())
        }
        return msg.toString()
    }

    //TODO: add ife.value somehow
    private fun buildMessage(e: HttpMessageNotReadableException): String {
        return Optional.of(e)
                .map { e.rootCause as InvalidFormatException }
                .filter { it.targetType.isEnum }
                .map { it.targetType.enumConstants as Array<Enum<*>>}
                .orElseGet { emptyArray() }
                .fold( StringJoiner(", ", "Invalid value. Possible values: [", "]"), {joiner, enum -> joiner.add(enum.name)})
                .toString()
    }
}