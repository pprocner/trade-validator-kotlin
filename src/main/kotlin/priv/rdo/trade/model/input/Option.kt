package priv.rdo.trade.model.input

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class Option(
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
        override val trader: String? = null,
        val style: String? = null,
        val strategy: String? = null,
        @get: ApiModelProperty(required = true, example = "2016-01-01") @get: JsonFormat(pattern = "yyyy-MM-dd") val deliveryDate: LocalDate? = null,
        @get: ApiModelProperty(required = true, example = "2016-01-01") @get: JsonFormat(pattern = "yyyy-MM-dd") val expiryDate: LocalDate? = null,
        @get: ApiModelProperty(required = true, example = "2016-01-01") @get: JsonFormat(pattern = "yyyy-MM-dd") val excerciseStartDate: LocalDate? = null,
        val payCcy: Currency? = null,
        val premium: BigDecimal? = null,
        val premiumCcy: Currency? = null,
        val premiumType: String? = null,
        @get: ApiModelProperty(required = true, example = "2016-01-01") @get: JsonFormat(pattern = "yyyy-MM-dd") val premiumDate: LocalDate? = null
) : Trade(customer, ccyPair, type, direction, tradeDate, amount1, amount2, rate, valueDate, legalEntity, trader)
