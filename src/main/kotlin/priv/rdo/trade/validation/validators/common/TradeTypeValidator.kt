package priv.rdo.trade.validation.validators.common

import mu.KLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.input.TradeType
import priv.rdo.trade.model.output.*
import priv.rdo.trade.validation.validators.Validator

@Component
class TradeTypeValidator : Validator<Trade> {
    companion object : KLogging() {
        private val TYPE_FIELD_NAME = "type"
    }

    override fun shouldValidate(input: Trade): Boolean = true

    override fun validate(input: Trade): ValidationResult {
        LOG.entry(input)

        if (StringUtils.isBlank(input.type)) {
            return LOG.exit(FieldMissingValidationResult(TYPE_FIELD_NAME))
        }

        return when {
            TradeType.values().contains(input.type) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("Type has to be one of: " + TradeType.values(), TYPE_FIELD_NAME))
        }
    }


}