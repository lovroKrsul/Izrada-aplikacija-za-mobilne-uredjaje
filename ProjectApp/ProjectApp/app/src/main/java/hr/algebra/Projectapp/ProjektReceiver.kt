package hr.algebra.Projectapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.Projectapp.framework.setBooleanPreference
import hr.algebra.Projectapp.framework.startActivity

class ProjektReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //FOREGROUND
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()

    }
}