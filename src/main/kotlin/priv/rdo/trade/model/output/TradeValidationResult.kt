package priv.rdo.trade.model.output

import org.apache.commons.collections4.CollectionUtils
import priv.rdo.trade.model.input.Trade

class TradeValidationResult {
    val validationStatus: ValidationStatus
    val errors: Collection<ValidationResult>?
    val validatedTrade: Trade

    constructor(validatedTrade: Trade) {
        this.validationStatus = ValidationStatus.SUCCESS
        this.validatedTrade = validatedTrade
        this.errors = null
    }

    constructor(validatedTrade: Trade, errors: Collection<ValidationResult>) {
        this.validationStatus = ValidationStatus.FAILURE
        this.validatedTrade = validatedTrade
        this.errors = errors
    }

    fun containsErrors(): Boolean = CollectionUtils.isNotEmpty(errors)
}
