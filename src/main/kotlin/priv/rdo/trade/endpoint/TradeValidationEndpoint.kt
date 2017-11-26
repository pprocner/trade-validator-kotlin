package priv.rdo.trade.endpoint

import com.codahale.metrics.annotation.Timed
import mu.KLogging
import org.apache.commons.collections4.CollectionUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.BulkTradeValidationResult
import priv.rdo.trade.model.output.TradeValidationResult
import priv.rdo.trade.validation.TradeValidationService

@RestController
class TradeValidationEndpoint(private val tradeValidationService: TradeValidationService) {
    companion object : KLogging()

    @Timed
    @PostMapping(value = "trades")
    fun trades(@RequestBody trade: Trade): ResponseEntity<TradeValidationResult> {
        LOG.info("Received a trade to validate $trade")

        val validationErrors = tradeValidationService.validate(trade)

        return when {
            validationErrors.isEmpty() -> {
                LOG.info("Trade validation success $trade")
                ResponseEntity.ok(TradeValidationResult(trade))
            }
            else -> {
                LOG.info("Trade validation failed $trade: $validationErrors")
                ResponseEntity.badRequest().body(TradeValidationResult(trade, validationErrors))
            }
        }
    }

    @Timed
    @PostMapping(value = "bulkTrades")
    fun trades(@RequestBody trades: List<Trade>): ResponseEntity<BulkTradeValidationResult> {
        LOG.info("Received trades to validate $trades")

        val result = BulkTradeValidationResult()

        trades.forEach { trade -> result.add(partialResultForBulk(trade)) }

        return when {
            result.containsErrors() -> {
                LOG.info("Trade validation failure $result")
                LOG.exit(ResponseEntity.badRequest().body(result))
            }
            else -> {
                LOG.info("Trade validation success $result")
                LOG.exit(ResponseEntity.ok(result))
            }
        }
    }

    private fun partialResultForBulk(trade: Trade): TradeValidationResult {
        val result = tradeValidationService.validate(trade)
        return when {
            CollectionUtils.isEmpty(result) -> TradeValidationResult(trade)
            else -> TradeValidationResult(trade, result)
        }
    }

}
