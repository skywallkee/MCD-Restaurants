package masterchefdevs.colectiv.ubb.chefs.data.reservations

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.findNavController
import masterchefdevs.colectiv.ubb.chefs.R

class LogInDialog : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        val builder = AlertDialog.Builder(activity)
        val view: View = requireActivity()
            .layoutInflater
            .inflate(R.layout.login_dialog, null)

        view.findViewById<Button>(R.id.cancel_please).setOnClickListener { this.dismiss() }
        view.findViewById<Button>(R.id.login_please).setOnClickListener {
            this.dismiss()
            findNavController().navigate(R.id.action_go_to_loggin)

        }
        builder.setView(view)
        return builder.create()
    }

}