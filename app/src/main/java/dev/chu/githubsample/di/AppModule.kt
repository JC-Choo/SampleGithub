package dev.chu.githubsample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.chu.chulibrary.api.BaseApiResponseAdapterFactory
import dev.chu.chulibrary.core.AppPref
import dev.chu.chulibrary.di.ForApplication
import dev.chu.chulibrary.di.ViewModelModule
import dev.chu.githubsample.MainApplication
import dev.chu.githubsample.data.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @ForApplication
    @Provides
    fun provideContext(app: MainApplication): Context = app

    @Singleton
    @Provides
    fun provideAppPref(app: MainApplication): AppPref = app.appPref


    @Singleton
    @Provides
    fun provideGithubService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(BaseApiResponseAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideDb(app: Application): GithubDb {
//        return Room
//            .databaseBuilder(app, GithubDb::class.java, "github.db")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideUserDao(db: GithubDb): UserDao {
//        return db.userDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRepoDao(db: GithubDb): RepoDao {
//        return db.repoDao()
//    }
}