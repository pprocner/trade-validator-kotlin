package priv.rdo.trade.validation.validators.common

import mu.KLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.*
import priv.rdo.trade.validation.validators.Validator
import java.util.*

//this could be implemented using a DB or whatnot, but in that case it would be an overkill
@Component
class LegalEntityValidator : Validator<Trade> {
    companion object : KLogging(){
        private val LEGAL_ENTITY_FIELD_NAME = "legalEntity"
        private val ALLOWED_LEGAL_ENTITIES = Arrays.asList("CS Zurich")
    }

    override fun shouldValidate(input: Trade): Boolean = true

    override fun validate(input: Trade): ValidationResult {
        LOG.entry(input)

        val legalEntity = input.legalEntity
        if (legalEntity == null || StringUtils.isBlank(legalEntity)) {
            return LOG.exit(FieldMissingValidationResult(LEGAL_ENTITY_FIELD_NAME))
        }

        return when {
            isSupportedEntity(legalEntity) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("We do not support a field named $legalEntity", LEGAL_ENTITY_FIELD_NAME))
        }
    }

    internal fun isSupportedEntity(legalEntity: String): Boolean = ALLOWED_LEGAL_ENTITIES.contains(legalEntity)
}
