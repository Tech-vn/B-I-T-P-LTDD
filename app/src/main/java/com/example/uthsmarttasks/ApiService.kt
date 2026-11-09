package com.example.uthsmarttasks

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // Firebase Realtime DB yêu cầu đuôi .json khi fetch dữ liệu qua REST API
    // Giả sử data của bạn được lưu ở node con là "tasks" -> Endpoint sẽ là "tasks.json"
    // Nếu bạn lưu ở node gốc thì đổi thành ".json"
    @GET("tasks.json")
    suspend fun getTasks(): Response<Map<String, Task>> // Firebase thường trả về dạng Map (Key-Value) thay vì List
}