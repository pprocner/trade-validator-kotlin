package priv.rdo.trade.validation

import mu.KLogging
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator

@Component
class ValidationManager(ctx: ApplicationContext) {
    companion object : KLogging()

    private final val validationChain: ValidationChain = ValidationChain()

    init {
        addValidators(ctx)
    }

    private final fun addValidators(ctx: ApplicationContext) {
        LOG.debug("Initializing validation manager...")

        val validators = findValidators(ctx)
        LOG.debug("Found {} validators: {}", validators.size, validators)

        validators.forEach { validationChain.addValidator(it) }
        LOG.debug("Added validators to the validation chain")
    }

    private fun findValidators(ctx: ApplicationContext): Collection<Validator<Trade>> {
        return ctx.getBeansOfType(Validator::class.java).values as Collection<Validator<Trade>>
    }

    fun validate(trade: Trade): Collection<ValidationResult> {
        LOG.entry(trade)
        return LOG.exit(validationChain.validate(trade))
    }


}
