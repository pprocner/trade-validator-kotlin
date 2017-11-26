package priv.rdo.trade.validation.validators.option

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Option
import priv.rdo.trade.model.input.OptionStyle
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.*
import priv.rdo.trade.validation.validators.Validator
import java.util.*

//this could be implemented using a DB or whatnot, but in that case it would be an overkill
@Component
class OptionStyleValidator : Validator<Option> {
    companion object : KLogging() {
        private val STYLE_FIELD_NAME = "style"
    }

    override fun shouldValidate(input: Trade): Boolean = input is Option

    override fun validate(input: Option): ValidationResult {
        LOG.entry(input)

        if (input.style == null) {
            return LOG.exit(FieldMissingValidationResult(STYLE_FIELD_NAME))
        }

        return when {
            validStyle(input.style) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("This field should be one of: " + Arrays.toString(OptionStyle.values()), STYLE_FIELD_NAME))
        }
    }

    private fun validStyle(input: String): Boolean = OptionStyle.values().map { it.name }.contains(input)

}