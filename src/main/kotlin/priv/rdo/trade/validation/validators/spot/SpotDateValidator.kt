package priv.rdo.trade.validation.validators.spot

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Spot
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator

@Component
class SpotDateValidator : Validator<Spot> {
    companion object : KLogging(){
        private val TRADE_DATE_FIELD_NAME = "tradeDate"
        private val VALUE_DATE_FIELD_NAME = "valueDate"
    }

    override fun shouldValidate(input: Trade): Boolean = input is Spot

    /**
     * with spot trade the value date should be exactly two days after trade date (more info in readme.md)
     */
    override fun validate(input: Spot): ValidationResult {
        LOG.entry(input)

        if (input.tradeDate == null || input.valueDate == null) {
            return LOG.exit(FieldMissingValidationResult(VALUE_DATE_FIELD_NAME, TRADE_DATE_FIELD_NAME))
        }

        return when {
            input.tradeDate.plusDays(2) == input.valueDate -> LOG.exit<ValidationResult>(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("the value date should be exactly two days after trade date", VALUE_DATE_FIELD_NAME, TRADE_DATE_FIELD_NAME))
        }
    }
}
