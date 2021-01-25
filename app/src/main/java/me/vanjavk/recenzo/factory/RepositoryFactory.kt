package me.vanjavk.recenzo.factory

import android.content.Context
import me.vanjavk.recenzo.dao.RecenzoSqlHelper

fun getRecenzoRepository(context: Context?) = RecenzoSqlHelper(context)