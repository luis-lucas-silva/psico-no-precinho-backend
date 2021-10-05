package com.luislucassilva.psiconoprecinho.ports.database.theme

import com.luislucassilva.psiconoprecinho.domain.psychologist.Theme
import java.util.*

interface ThemeRepository {
    suspend fun addThemeToPsychologist(themeId: Int, psychologistId: UUID)
    suspend fun deleteAllThemesToPsychologist(psychologistId: UUID)
    suspend fun findByPsychologistId(psychologistId: UUID): List<Theme>
}