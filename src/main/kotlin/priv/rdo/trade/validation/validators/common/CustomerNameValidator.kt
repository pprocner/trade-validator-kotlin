package priv.rdo.trade.validation.validators.common

import mu.KLogging
import org.springframework.stereotype.Component
import priv.rdo.trade.model.input.Customer
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.GenericFailureValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import priv.rdo.trade.validation.validators.Validator

//this could be implemented using a DB or whatnot, but in that case it would be an overkill
@Component
class CustomerNameValidator : Validator<Trade> {
    companion object : KLogging(){
        private val CUSTOMER_FIELD_NAME = "customer"
    }

    override fun shouldValidate(input: Trade): Boolean = true

    override fun validate(input: Trade): ValidationResult {
        LOG.entry(input)

        val customer = input.customer
        if (customer == null) {
            return LOG.exit(FieldMissingValidationResult(CUSTOMER_FIELD_NAME))
        }

        return when {
            isSupportedCustomer(customer) -> LOG.exit(SuccessValidationResult)
            else -> LOG.exit(GenericFailureValidationResult("We do not support a customer named " + customer, CUSTOMER_FIELD_NAME))
        }
    }

    fun isSupportedCustomer(customer: String): Boolean = Customer.values().map { it.name }.contains(customer)

}
