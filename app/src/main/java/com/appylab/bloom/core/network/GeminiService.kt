package com.appylab.bloom.core.network

import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyAbFQolb7J_8J_Aj8gUL2ncexUCNaBBq0M"
    )

    suspend fun generateWorkoutSummary(sessionData: String): String? {
        return try {
            val prompt = """
                Analyze the following workout session data and provide a summary containing:
                1. Estimated Calories Burnt
                2. Muscles Targeted
                3. Overall Intensity (Low, Medium, High)
                4. Recovery Tip
                5. Next Session Suggestion
                
                Data:
                $sessionData
                
                Return ONLY a JSON object with keys: calories, muscles, intensity, recovery_tip, next_session
            """.trimIndent()
            
            val response = generativeModel.generateContent(prompt)
            response.text?.trim()?.removePrefix("```json")?.removeSuffix("```")?.trim()
        } catch (e: Exception) {
            null
        }
    }
}
