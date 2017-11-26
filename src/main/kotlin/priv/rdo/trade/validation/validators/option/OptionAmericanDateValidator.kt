package priv.rdo.trade.validation.validators.option

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Option
import priv.rdo.trade.model.input.OptionStyle.AMERICAN
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator
import java.time.LocalDate

@Component
class OptionAmericanDateValidator : Validator<Option> {
    companion object : KLogging() {
        private val EXERCISE_DATE_FIELD_NAME = "excerciseStartDate"
        private val TRADE_DATE_FIELD_NAME = "tradeDate"
        private val EXPIRY_DATE_FIELD_NAME = "expiryDate"
    }

    override fun shouldValidate(input: Trade): Boolean {
        if (input !is Option) {
            return false
        }

        return AMERICAN.name == input.style
    }

    override fun validate(input: Option): ValidationResult {
        LOG.entry(input)

        if (input.excerciseStartDate == null || input.tradeDate == null || input.expiryDate == null) {
            return LOG.exit(FieldMissingValidationResult(EXERCISE_DATE_FIELD_NAME, TRADE_DATE_FIELD_NAME, EXPIRY_DATE_FIELD_NAME))
        }

        return when {
            isValidDate(input.excerciseStartDate, input) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("the exerciseStartDate has to be after the tradeDate and before the expiryDate",
                    EXERCISE_DATE_FIELD_NAME, TRADE_DATE_FIELD_NAME, EXPIRY_DATE_FIELD_NAME))
        }
    }

    private fun isValidDate(excerciseStartDate: LocalDate, input: Option) =
            excerciseStartDate.isAfter(input.tradeDate) && excerciseStartDate.isBefore(input.expiryDate)

}
