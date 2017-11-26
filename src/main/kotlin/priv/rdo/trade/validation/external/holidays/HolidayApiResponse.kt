package priv.rdo.trade.validation.external.holidays

data class HolidayApiResponse(val status: Int,
                              val holidays: List<Holiday>,
                              val error: String?)
