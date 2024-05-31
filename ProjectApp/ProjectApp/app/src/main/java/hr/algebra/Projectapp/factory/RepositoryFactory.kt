package hr.algebra.Projectapp.factory

import android.content.Context
import hr.algebra.Projectapp.dao.ProjektSqlHelper

fun getProjectRepository(context: Context?) = ProjektSqlHelper(context)