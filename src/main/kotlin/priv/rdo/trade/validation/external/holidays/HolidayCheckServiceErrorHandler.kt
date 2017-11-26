package priv.rdo.trade.validation.external.holidays

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.slf4j.ext.XLogger.Level.WARN
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.DefaultResponseErrorHandler
import java.io.IOException

@Component
class HolidayCheckServiceErrorHandler(private val mapper: ObjectMapper) : DefaultResponseErrorHandler() {
    companion object: KLogging()

    override fun handleError(response: ClientHttpResponse) {
        LOG.entry(response)

        if (HttpStatus.NOT_FOUND == getHttpStatusCode(response)) {
            throw LOG.throwing(WARN, HolidayCheckServiceNotWorkingException("Service was not found, please contact admins"))
        }
        try {
            val apiResponse = mapper.readValue(getResponseBody(response), HolidayApiResponse::class.java)
            throw LOG.throwing(WARN, HolidayCheckServiceNotWorkingException("Service did not respond correctly. status = "
                    + apiResponse.status + ", error = " + apiResponse.error))
        } catch (e: IOException) {
            LOG.warn("Exception not handled internally", e)
            super.handleError(response)
        }

    }



}
