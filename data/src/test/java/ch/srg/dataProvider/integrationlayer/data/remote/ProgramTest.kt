package ch.srg.dataProvider.integrationlayer.data.remote

import java.util.Calendar
import java.util.Calendar.AUGUST
import java.util.Calendar.OCTOBER
import java.util.Calendar.SEPTEMBER
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProgramTest {
    @Test
    fun `is date in program time`() {
        val startTime = createDate(2024, SEPTEMBER, 1)
        val endTime = createDate(2024, SEPTEMBER, 30)
        val program = Program(
            title = "",
            startTime = startTime,
            endTime = endTime,
        )

        assertFalse(program.isDateInProgramTime(createDate(2024, AUGUST, 15)))
        assertFalse(program.isDateInProgramTime(startTime))
        assertTrue(program.isDateInProgramTime(createDate(2024, SEPTEMBER, 15)))
        assertFalse(program.isDateInProgramTime(endTime))
        assertFalse(program.isDateInProgramTime(createDate(2024, OCTOBER, 15)))
    }

    private companion object {
        private fun createDate(year: Int, month: Int, day: Int): Date {
            return Calendar.getInstance().apply {
                set(year, month, day)
            }.time
        }
    }
}
