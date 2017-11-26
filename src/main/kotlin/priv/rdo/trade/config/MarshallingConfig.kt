package priv.rdo.trade.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
class MarshallingConfig {

    class MoneySerializer : JsonSerializer<BigDecimal>() {
        override fun serialize(value: BigDecimal, jsonGenerator: JsonGenerator, provider: SerializerProvider) {
            jsonGenerator.writeNumber(value.setScale(2, BigDecimal.ROUND_HALF_UP))
        }
    }
}
