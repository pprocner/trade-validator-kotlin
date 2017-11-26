package priv.rdo.trade.validation.validators.common

import priv.rdo.trade.model.input.CurrencyPair
import priv.rdo.trade.model.input.Spot
import priv.rdo.trade.model.input.Trade
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 08.07.2017
 */
class CurrencyPairValidatorTest extends Specification {
    @Shared
    CurrencyPairValidator sut = new CurrencyPairValidator()

    @Unroll
    def "should return #resType validation result for '#inputCurrencyString' currencyPair"() {
        given:
            Trade trade = testTrade(new CurrencyPair(inputCurrencyString))

        expect:
            sut.validate(trade).isFailure() == !expectedSuccess

        where:
            inputCurrencyString || expectedSuccess
            "PLNUSD"            || true
            "PLNUSD1"           || false
            "PLN1USD"           || false
            "PLZUSD"            || false
            "PLNUSZ"            || false
            ""                  || false
//            null                || false

            resType = expectedSuccess ? "positive" : "negative"
    }

    def "should return negative validation result for null currencyPair"() {
        given:
            Trade trade = testTrade(null)

        expect:
            sut.validate(trade).isFailure()
    }

    Trade testTrade(CurrencyPair inputCurrencyPair) {
        return new Spot(null,
                inputCurrencyPair,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
    }
}
