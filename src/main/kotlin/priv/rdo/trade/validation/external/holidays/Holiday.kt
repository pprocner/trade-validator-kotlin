package priv.rdo.trade.validation.external.holidays

import java.time.LocalDate

data class Holiday(private val name: String,
                   private val date: LocalDate)