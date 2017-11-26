package priv.rdo.trade.validation.validators

import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.ValidationResult

interface Validator<T : Trade> {
    fun shouldValidate(input: Trade): Boolean

    fun validate(input: T): ValidationResult
}
