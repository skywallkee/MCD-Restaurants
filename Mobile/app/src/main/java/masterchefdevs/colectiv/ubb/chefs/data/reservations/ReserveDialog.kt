package masterchefdevs.colectiv.ubb.chefs.data.reservations

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.ReservationDTO


class ReserveDialog : AppCompatDialogFragment() {
    private lateinit var myContext: Context
    private var date: String = ""
    private var time: String = ""
    private var duration: String = ""
    private var table_id: Int = 0

/*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myContext = inflater.context
        arguments?.let {
            date = it.getString("date").toString()
            time = it.getString("time").toString()
            duration = it.getString("duration").toString()
        }
        return inflater.inflate(masterchefdevs.colectiv.ubb.chefs.R.layout.reservation_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_date).setText(date)
        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_time).setText(time)
        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_duration).setText(duration)

        view.findViewById<EditText>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_date).setText(date)

    }

 */


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        myContext = requireContext()
        arguments?.let {
            date = it.getString("date").toString()
            time = it.getString("time").toString()
            duration = it.getString("duration").toString()
            table_id = it.getInt("table_id")
        }
        val builder = AlertDialog.Builder(activity)
        val view: View = requireActivity()
            .layoutInflater
            .inflate(masterchefdevs.colectiv.ubb.chefs.R.layout.reservation_dialog, null)

        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_date).setText(date)
        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_time).setText(time)
        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_duration).setText(duration)

        view.findViewById<EditText>(masterchefdevs.colectiv.ubb.chefs.R.id.pers_name_text).hint = "Name"
        view.findViewById<EditText>(masterchefdevs.colectiv.ubb.chefs.R.id.telefon_text).hint = "Telephone"
        view.findViewById<EditText>(masterchefdevs.colectiv.ubb.chefs.R.id.email_text).hint = "Email"

        view.findViewById<Button>(masterchefdevs.colectiv.ubb.chefs.R.id.cancel_reservation_button)
            .setOnClickListener {
                this.dismiss()
            }

        builder.setView(view)
        return builder.create()
    }
/*
    suspend fun makeReservation(){
        val id_M: Int = 1;
        val id_U: Int = 1
        val data: String = ""
        val ora: String = ""
        val timp: String = ""
        val telefon: String = ""
        val nume_pers: String =""
        val email: String = ""

        var reservationDto = ReservationDTO(id_M, id_U, data, ora, timp, telefon, nume_pers, email)

        reservationModel.makeReservation(reservationDto)
        reservationModel.livedataReservationSucceded.observe(viewLifecycleOwner, {
            if (it == 1) {
                //succes
            } else
                if (it == 2) {
                    //error
                }
        })
    }
    
 */
}