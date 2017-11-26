package priv.rdo.trade.model.input

import java.util.*

/*
 * was enum but one can't pass enums to annotations, so I decided to go with String
 */
object TradeType {
    const val SPOT = "Spot"
    const val FORWARD = "Forward"
    const val VANILLA_OPTION = "VanillaOption"

    fun values(): List<String> = Arrays.asList(SPOT, FORWARD, VANILLA_OPTION)
}
