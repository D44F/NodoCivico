package com.android.nodocivico.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Toast.makeText(context, "Nodo Cívico: Servicios reiniciados", Toast.LENGTH_LONG).show()
            // Aquí se reprogramarían las alarmas de recordatorios
        }
    }
}