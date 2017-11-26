package priv.rdo.trade.validation.validators.common

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.CurrencyPair
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator

@Component
class CurrencyPairValidator : Validator<Trade> {
    companion object : KLogging(){
        private val CURRENCY_PAIR_FIELD_NAME = "ccyPair"
    }

    override fun shouldValidate(input: Trade): Boolean = true

    override fun validate(input: Trade): ValidationResult {
        LOG.entry(input)

        val currencyPair = input.ccyPair
        if (currencyPair == null){
            return LOG.exit(FieldMissingValidationResult(CURRENCY_PAIR_FIELD_NAME))
        }

        return when {
            isInvalidValue(currencyPair) -> LOG.exit(GenericFailureValidationResult("currency pair value is invalid", CURRENCY_PAIR_FIELD_NAME))
            else -> LOG.exit(SuccessValidationResult)
        }
    }

    private fun isInvalidValue(currencyPair: CurrencyPair) = currencyPair.leftCurrency == null || currencyPair.rightCurrency == null


}
