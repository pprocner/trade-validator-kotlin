package priv.rdo.trade.validation

import org.springframework.stereotype.Service
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.ValidationResult

@Service
class TradeValidationService(private val validationManager: ValidationManager) {

    fun validate(trade: Trade): Collection<ValidationResult> {
        return findValidationErrors(validationManager.validate(trade))
    }

    fun findValidationErrors(results: Collection<ValidationResult>) = results.filter { it.isFailure() }
}
