package com.lmptech.recipefinder.data.models.bulk

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)