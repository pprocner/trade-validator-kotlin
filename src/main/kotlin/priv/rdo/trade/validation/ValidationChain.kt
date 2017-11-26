package priv.rdo.trade.validation

import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator
import java.util.*

class ValidationChain {
    private val validators: MutableList<Validator<Trade>> = ArrayList()

    internal fun addValidator(validator: Validator<Trade>): ValidationChain {
        validators.add(validator)
        return this
    }

    fun validate(trade: Trade): Collection<ValidationResult> = validators
            .filter { it.shouldValidate(trade) }
            .map { it.validate(trade) }
}
