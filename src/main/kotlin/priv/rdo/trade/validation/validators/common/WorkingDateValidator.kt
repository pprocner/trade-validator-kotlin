package priv.rdo.trade.validation.validators.common

import mu.KLogging
import priv.rdo.trade.model.input.Trade
import priv.rdo.trade.model.output.*
import priv.rdo.trade.validation.external.holidays.HolidayCheckService
import priv.rdo.trade.validation.external.holidays.HolidayCheckServiceNotWorkingException
import priv.rdo.trade.validation.validators.Validator
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

abstract class WorkingDateValidator<T : Trade>(private val holidayCheckService: HolidayCheckService) : Validator<T> {
    companion object : KLogging(){
        private val WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
    }

    protected abstract val validatedDateName: String

    override fun validate(input: T): ValidationResult {
        LOG.entry(input)

        val validatedDate = getValidatedDate(input)
        if (validatedDate == null) {
            return LOG.exit(FieldMissingValidationResult(validatedDateName))
        }

        if (isWeekend(validatedDate)) {
            return LOG.exit(GenericFailureValidationResult("Date cannot occur during weekends", validatedDateName))
        }

        return try {
            when {
                isHoliday(validatedDate) -> LOG.exit(GenericFailureValidationResult("Date cannot occur during bank holidays", validatedDateName))
                else -> LOG.exit(SuccessValidationResult)
            }
        } catch (e: HolidayCheckServiceNotWorkingException) {
            LOG.exit(GenericFailureValidationResult("Working date validity could not be checked due to an external server error: " + e.message, validatedDateName))
        }

    }

    protected abstract fun getValidatedDate(input: T): LocalDate?

    protected fun isWeekend(date: LocalDate): Boolean = WEEKEND.contains(date.dayOfWeek)

    protected fun isHoliday(date: LocalDate): Boolean = holidayCheckService.isHoliday(date)
}
