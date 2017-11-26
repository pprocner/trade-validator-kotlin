package priv.rdo.trade.helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import priv.rdo.trade.model.input.*
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

/**
 * @author WrRaThY
 * @since 08.07.2017
 */
class MarshallingTest extends Specification {
    public static final String TEST_JSON = '{"customer":"PLUTO1","ccyPair":"EURUSD","type":"Spot","direction":"BUY","tradeDate":"2016-08-11",' +
            '"amount1":1000000.00,"amount2":1120000.00,"rate":1.12,"valueDate":"2016-08-15","legalEntity":"CS Zurich","trader":"Johann Baumfiddler"}'

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).registerModule(new KotlinModule())

    def "should unmarshal JSON to an object"() {

        when:
            def result = mapper.readValue(TEST_JSON, Trade)

        then:
            result != null
            result.ccyPair.leftCurrency == Currency.getInstance(Locale.GERMANY)
            result.direction == TradeDirection.BUY
    }

    def "should marshal an object to JSON"() {
        given:
            Spot input = testSpot()


        when:
            String result = mapper.writeValueAsString(input)

        then:
            result == TEST_JSON
    }

    def testSpot() {
        return new Spot(
                Customer.PLUTO1.name(),
                new CurrencyPair("EURUSD"),
                "Spot",
                TradeDirection.BUY,
                LocalDate.of(2016, Month.AUGUST, 11),
                BigDecimal.valueOf(1000000),
                BigDecimal.valueOf(1120000),
                BigDecimal.valueOf(1.12),
                LocalDate.of(2016, Month.AUGUST, 15),
                "CS Zurich",
                "Johann Baumfiddler"
        )
    }
}
