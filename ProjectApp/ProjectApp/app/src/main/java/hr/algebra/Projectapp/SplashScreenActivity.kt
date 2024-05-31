package hr.algebra.Projectapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.Projectapp.api.ProjektWorker
import hr.algebra.Projectapp.databinding.ActivitySplashScreenBinding
import hr.algebra.Projectapp.framework.applyAnimation
import hr.algebra.Projectapp.framework.callDelayed
import hr.algebra.Projectapp.framework.getBooleanPreference
import hr.algebra.Projectapp.framework.isOnline
import hr.algebra.Projectapp.framework.startActivity

private const val DELAY = 3000L

const val DATA_IMPORTED = "hr.algebra.Projectapp.data8"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.tvSplash.applyAnimation(R.anim.fadein_txt)
        binding.ivSplash.applyAnimation(R.anim.fadein)

    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) {
                startActivity<HostActivity>()
            }
        } else {
            if (isOnline()) {

                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(ProjektWorker::class.java)
                    )
                }


            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) {
                    finish()
                }
            }
        }
    }

}