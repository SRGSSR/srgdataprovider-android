package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.Calendar.AUGUST
import java.util.Calendar.OCTOBER
import java.util.Calendar.SEPTEMBER
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProgramTest {
    @Test
    fun `is date in program time`() {
        val startTime = createInstant(2024, SEPTEMBER, 1)
        val endTime = createInstant(2024, SEPTEMBER, 30)
        val program = Program(
            title = "",
            startTime = startTime,
            endTime = endTime,
        )

        assertFalse(program.isDateInProgramTime(createInstant(2024, AUGUST, 15)))
        assertFalse(program.isDateInProgramTime(startTime))
        assertTrue(program.isDateInProgramTime(createInstant(2024, SEPTEMBER, 15)))
        assertFalse(program.isDateInProgramTime(endTime))
        assertFalse(program.isDateInProgramTime(createInstant(2024, OCTOBER, 15)))
    }

    private companion object {
        private fun createInstant(year: Int, month: Int, day: Int): Instant {
            val timestamp = LocalDate(year, month, day)
                .atStartOfDayIn(TimeZone.currentSystemDefault())
                .toEpochMilliseconds()

            return Instant.fromEpochMilliseconds(timestamp)
        }
    }
}
