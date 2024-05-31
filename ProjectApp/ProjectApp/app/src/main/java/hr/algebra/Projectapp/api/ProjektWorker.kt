package hr.algebra.Projectapp.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ProjektWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        ProjektFetcher(context).fetchItems()

        return Result.success()
    }


}