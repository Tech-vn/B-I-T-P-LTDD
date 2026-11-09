package com.example.uthsmarttasks


import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("remind") val remind: Boolean,
    @SerializedName("done") val done: Boolean,
    @SerializedName("subtasks") val subtasks: List<String>? = null
)