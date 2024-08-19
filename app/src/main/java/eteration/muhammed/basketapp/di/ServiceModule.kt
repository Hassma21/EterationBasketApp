package eteration.muhammed.basketapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eteration.muhammed.basketapp.data.remote.EterationMockAPI
import eteration.muhammed.basketapp.data.repository.ProductRepositoryImpl
import eteration.muhammed.basketapp.domain.repository.ProductRepository
import eteration.muhammed.basketapp.util.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideEterationMockApi(): EterationMockAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EterationMockAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api : EterationMockAPI): ProductRepository {
        return ProductRepositoryImpl(api = api)
    }
}