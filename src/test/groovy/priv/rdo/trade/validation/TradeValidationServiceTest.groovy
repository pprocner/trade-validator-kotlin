package priv.rdo.trade.validation

import priv.rdo.trade.model.output.FieldMissingValidationResult
import priv.rdo.trade.model.output.SuccessValidationResult
import priv.rdo.trade.model.output.ValidationResult
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 08.07.2017
 */
class TradeValidationServiceTest extends Specification {
    TradeValidationService sut = new TradeValidationService(Mock(ValidationManager))

    @Unroll
    def "should return #expectedFailuresCount fails for #validationResults"() {
        when: ""
            List<ValidationResult> errors = sut.findValidationErrors(validationResults as List<ValidationResult>)

        then:
            errors.size() == expectedFailuresCount

        where:
            validationResults                 || expectedFailuresCount
            [success(), failure()]            || 1
            [failure()]                       || 1
            [success(), success(), success()] || 0
            [failure(), failure()]            || 2
            []                                || 0
    }

    def failure() {
        new FieldMissingValidationResult("aaa", "bbb")
    }

    def success() {
        SuccessValidationResult.newInstance()
    }
}
