package priv.rdo.trade.model.input

import java.math.BigDecimal
import java.time.LocalDate

data class Forward(
        override val customer: String? = null,
        override val ccyPair: CurrencyPair? = null,
        override val type: String? = null,
        override val direction: TradeDirection? = null,
        override val tradeDate: LocalDate? = null,
        override val amount1: BigDecimal? = null,
        override val amount2: BigDecimal? = null,
        override val rate: BigDecimal? = null,
        override val valueDate: LocalDate? = null,
        override val legalEntity: String? = null,
        override val trader: String? = null
        ) : Trade(customer, ccyPair, type, direction, tradeDate, amount1, amount2, rate, valueDate, legalEntity, trader)
