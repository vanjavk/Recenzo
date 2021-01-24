package me.vanjavk.recenzo.factory

import android.content.Context

fun getRecenzoRepository(context: Context?) = RecenzoSqlHelper(context)