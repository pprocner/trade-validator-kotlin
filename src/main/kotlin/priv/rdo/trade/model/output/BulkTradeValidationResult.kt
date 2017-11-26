package priv.rdo.trade.model.output

class BulkTradeValidationResult {
    val results: MutableList<TradeValidationResult> = mutableListOf()

    fun add(result: TradeValidationResult) = results.add(result)

    fun containsErrors(): Boolean = results
            .map{ it.containsErrors() }
            .fold(false, {identity, nextVal -> identity || nextVal})
}
