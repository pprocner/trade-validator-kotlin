package priv.rdo.trade.validation.validators.common

import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Forward
import priv.rdo.trade.model.input.Spot
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.validation.external.holidays.HolidayCheckService

import java.time.LocalDate

/*
 * for info about why this got created (its ugly, I'll admit it) please refer to OptionWorkingDateValidator
 */
@Component
class SpotAndForwardWorkingDateValidator(holidayCheckService: HolidayCheckService) : WorkingDateValidator<Trade>(holidayCheckService) {

    override val validatedDateName: String
        get() = VALUE_DATE_FIELD_NAME

    override fun getValidatedDate(input: Trade): LocalDate? {
        return input.valueDate
    }

    override fun shouldValidate(input: Trade): Boolean {
        return input is Spot || input is Forward
    }

    companion object {
        private val VALUE_DATE_FIELD_NAME = "valueDate"
    }
}
