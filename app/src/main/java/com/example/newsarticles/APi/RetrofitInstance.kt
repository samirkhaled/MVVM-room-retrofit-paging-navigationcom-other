package com.example.newsarticles.APi
import com.example.newsarticles.Util.Constant.Companion.Base_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitInstance {
        companion object {
            private val retrofit by lazy {
                //for logcat response result
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                //more efficiency
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            val api by lazy {
                retrofit.create(NewApi::class.java)
            }
        }
    }