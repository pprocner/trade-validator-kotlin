package priv.rdo.trade.model.input

import com.fasterxml.jackson.annotation.JsonValue
import org.apache.commons.lang3.StringUtils
import java.util.*

class CurrencyPair(currencyPair: String?) {
    val leftCurrency: Currency?
    val rightCurrency: Currency?

    init {
        if (StringUtils.isNotBlank(currencyPair)) {
            leftCurrency = findCurrency(currencyPair?.substring(0, 3))
            rightCurrency = findCurrency(currencyPair?.substring(3))
        } else {
            leftCurrency = null
            rightCurrency = null
        }
    }

    private fun findCurrency(currencyString: String?): Currency? {
        return try {
            Currency.getInstance(currencyString)
        } catch (e: IllegalArgumentException) {
            null
        }

    }

    @JsonValue
    override fun toString(): String = currencyCode(leftCurrency) + currencyCode(rightCurrency)

    private fun currencyCode(currency: Currency?): String = currency?.currencyCode ?: "N/A"
}
