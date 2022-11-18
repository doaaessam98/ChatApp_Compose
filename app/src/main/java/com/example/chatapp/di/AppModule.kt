package com.example.chatapp.di

import com.example.chatapp.data.repository.userRepository.IRepository
import com.example.chatapp.data.repository.userRepository.Repository
import com.example.chatapp.data.source.remote.userData.IUserData
import com.example.chatapp.data.source.remote.userData.UserData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {
   @Binds
   fun provideUserData(userData: UserData): IUserData

    @Binds
    fun provideRepository(repository: Repository): IRepository




}