package com.example.chatapp.di

import com.example.chatapp.data.repository.IRepository
import com.example.chatapp.data.repository.Repository
import com.example.chatapp.data.source.IUserData
import com.example.chatapp.data.source.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {
   @Binds
   fun provideUserData(userData: UserData):IUserData

    @Binds
    fun provideRepository(repository: Repository):IRepository




}