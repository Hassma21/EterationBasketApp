package eteration.muhammed.basketapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eteration.muhammed.basketapp.data.local.BasketDatabase
import eteration.muhammed.basketapp.data.local.ProductDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBasketDatabase(@ApplicationContext context: Context): BasketDatabase {
        return BasketDatabase.invoke(context)
    }

    @Provides
    @Singleton
    fun provideProductDao(basketDatabase: BasketDatabase): ProductDao {
        return basketDatabase.productDao
    }
}