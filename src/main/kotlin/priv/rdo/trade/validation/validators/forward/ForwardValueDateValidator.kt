package priv.rdo.trade.validation.validators.forward

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Forward
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.TemporalAdjusters
import java.util.*

@Component
class ForwardValueDateValidator : Validator<Forward> {
    companion object : KLogging() {
        private val VALUE_DATE_FIELD_NAME = "valueDate"

        private val QUARTERS = EnumSet.of(Month.MARCH, Month.JUNE, Month.SEPTEMBER, Month.DECEMBER)
        private val THIRD_FRIDAY_OF_A_MONTH = TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.FRIDAY)
    }

    override fun shouldValidate(input: Trade): Boolean = input is Forward

    /**
     * with forward trade the value date should be one of four possible dates
     * a possible date is every 3rd Friday of each quarter (more info in readme.md)
     */
    override fun validate(input: Forward): ValidationResult {
        LOG.entry(input)

        if (input.valueDate == null) {
            return LOG.exit(FieldMissingValidationResult(VALUE_DATE_FIELD_NAME))
        }

        return when {
            isA3rdFridayOfAQuarter(input.valueDate) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("the value date should be a 3rd Friday of a quarter", VALUE_DATE_FIELD_NAME))
        }
    }

    protected fun isA3rdFridayOfAQuarter(valueDate: LocalDate): Boolean {
        if (!QUARTERS.contains(valueDate.month)) {
            return false
        }

        return valueDate.with(THIRD_FRIDAY_OF_A_MONTH) == valueDate
    }


}