package com.uzpsb.nearbyrestaurantsapplication.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.uzpsb.nearbyrestaurantsapplication.databinding.WarningDialogBinding

class WarningDialog(context: Context) : Dialog(context) {
    private lateinit var binding: WarningDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WarningDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnOk.setOnClickListener{

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
            this.dismiss()
        }
    }
}