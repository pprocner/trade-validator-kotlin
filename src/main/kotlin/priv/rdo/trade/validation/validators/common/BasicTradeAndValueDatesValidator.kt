package priv.rdo.trade.validation.validators.common

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Forward
import priv.rdo.trade.model.input.Spot
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator
import java.time.LocalDate

/*
 * because value date does not exist in the input data for Options I moved this validator out from common validation chain (which does not fit requirements)
 * This may be a bad idea and is something that I'd ask about, but I'm writing this code during the weekend so there is no one to ask :)
 */
@Component
class BasicTradeAndValueDatesValidator : Validator<Trade> {
    companion object : KLogging() {
        private val TRADE_DATE_FIELD_NAME = "tradeDate"
        private val VALUE_DATE_FIELD_NAME = "valueDate"
    }

    override fun shouldValidate(input: Trade): Boolean = input is Spot || input is Forward

    override fun validate(input: Trade): ValidationResult {
        LOG.entry(input)

        val tradeDate = input.tradeDate
        val valueDate = input.valueDate

        if (tradeDate == null || valueDate == null) {
            return LOG.exit(FieldMissingValidationResult(VALUE_DATE_FIELD_NAME, TRADE_DATE_FIELD_NAME))
        }

        return when {
            valueDateIsNotBeforeTradeDate(valueDate, tradeDate) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("Value date cannot be before trade date", VALUE_DATE_FIELD_NAME, TRADE_DATE_FIELD_NAME))
        }
    }

    private fun valueDateIsNotBeforeTradeDate(valueDate: LocalDate, tradeDate: LocalDate): Boolean {
        return valueDate.isAfter(tradeDate) || valueDate.isEqual(tradeDate)
    }


}
