package priv.rdo.trade.validation.validators.option

import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Option
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.validation.external.holidays.HolidayCheckService
import priv.rdo.trade.validation.validators.common.WorkingDateValidator

import java.time.LocalDate

/*
 * because value date does not exist in the input data for Options I moved this validator out from common validation chain (which does not fit requirements) and split it in two
 * I just assumed that some other date has to be checked for being working date in case of option
 * This may be a bad idea and is something that I'd ask about, but I'm writing this code during the weekend so there is no one to ask :)
 */
@Component
class OptionWorkingDateValidator(holidayCheckService: HolidayCheckService) : WorkingDateValidator<Option>(holidayCheckService) {
    companion object {
        private val DELIVERY_DATE_FIELD_NAME = "deliveryDate"
    }

    override val validatedDateName = DELIVERY_DATE_FIELD_NAME

    override fun getValidatedDate(input: Option): LocalDate? = input.deliveryDate

    override fun shouldValidate(input: Trade): Boolean = input is Option


}
