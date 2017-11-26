package priv.rdo.trade.validation.validators.option

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Option
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator

@Component
class OptionDatesValidator : Validator<Option> {
    companion object : KLogging() {
        private val PREMIUM_DATE_FIELD_NAME = "premiumDate"
        private val DELIVERY_DATE_FIELD_NAME = "deliveryDate"
        private val EXPIRY_DATE_FIELD_NAME = "expiryDate"
    }

    override fun shouldValidate(input: Trade): Boolean = input is Option

    override fun validate(input: Option): ValidationResult {
        LOG.entry(input)

        if (input.premiumDate == null || input.deliveryDate == null || input.expiryDate == null) {
            return LOG.exit(FieldMissingValidationResult(PREMIUM_DATE_FIELD_NAME, DELIVERY_DATE_FIELD_NAME, EXPIRY_DATE_FIELD_NAME))
        }

        return if (input.premiumDate.isBefore(input.deliveryDate) && input.expiryDate.isBefore(input.deliveryDate)) {
            LOG.exit(SuccessValidationResult)
        } else {
            LOG.exit(GenericFailureValidationResult("the expiry date and the premium date shall be before the delivery date",
                    PREMIUM_DATE_FIELD_NAME, DELIVERY_DATE_FIELD_NAME, EXPIRY_DATE_FIELD_NAME))
        }
    }


}
