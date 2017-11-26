package priv.rdo.trade.validation.external.holidays

import com.codahale.metrics.annotation.Timed
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDate

@Service
class HolidayCheckService {
    // default = US, because our request does NOT contain information about location. one could argue that we could use currency for that, but this method
    // would fail for Europe (EURO). Maybe it could be obtained from the customer or the legal entity, but I don't have such information
    // then again... I suppose this was just to check if I am able to make some rest request so... I did a very simple one :)
    companion object : KLogging(){
        private val URL = "https://holidayapi.com/v1/holidays?key={key}&country={country}&year={year}&month={month}&day={day}"
        private val DEFAULT_COUNTRY = "US"
    }

    private final val restTemplate: RestTemplate

    constructor(restTemplate: RestTemplate, errorHandler: HolidayCheckServiceErrorHandler) {
        this.restTemplate = restTemplate
        this.restTemplate.errorHandler = errorHandler
    }

    @Value("\${holidays.key}")
    private val key: String? = null

    @Timed
    fun isHoliday(date: LocalDate): Boolean {
        LOG.info("Calling external system to check if {} is a holiday", date)

        val response: ResponseEntity<HolidayApiResponse> = restTemplate.getForEntity(URL, HolidayApiResponse::class.java,
                key,
                DEFAULT_COUNTRY,
                date.year,
                date.monthValue,
                date.dayOfMonth
        )

        return LOG.exit(containsHolidays(response.body))
    }

    fun containsHolidays(response: HolidayApiResponse): Boolean {
        return response.holidays.isNotEmpty()
    }


}
