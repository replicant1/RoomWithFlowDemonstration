package com.example.roomwithflowdemonstration.db

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    fun build(ctx: Context) : AppDatabase {
        return Room.databaseBuilder(
            ctx.applicationContext,
            AppDatabase::class.java,
            "demo").build()
    }
}