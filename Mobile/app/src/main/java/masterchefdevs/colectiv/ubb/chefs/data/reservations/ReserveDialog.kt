package masterchefdevs.colectiv.ubb.chefs.data.reservations

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import masterchefdevs.colectiv.ubb.chefs.core.Api
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantViewModel
import masterchefdevs.colectiv.ubb.chefs.data.model.ReservationDTO
import java.util.regex.Pattern

class ReserveDialog : AppCompatDialogFragment() {
    private lateinit var myContext: Context
    private lateinit var reservationModel: ReservationsViewModel
    private var date: String = ""
    private var res_id: String = ""
    private var time: String = ""
    private var duration: String = ""
    private var table_id: Int = 0
    private lateinit var error_text: TextView
    private lateinit var name_text: EditText
    private lateinit var tel_text: EditText
    private lateinit var em_text: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        myContext = requireContext()
        arguments?.let {
            res_id = it.getString("ITEM_ID").toString()
            date = it.getString("date").toString()
            time = it.getString("time").toString()
            duration = it.getString("duration").toString()
            table_id = it.getInt("table_id")
        }
        val builder = AlertDialog.Builder(activity)
        val view: View = requireActivity()
            .layoutInflater
            .inflate(masterchefdevs.colectiv.ubb.chefs.R.layout.reservation_dialog, null)

        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_date)
            .setText(date)
        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_time)
            .setText(time)
        view.findViewById<TextView>(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_duration)
            .setText(duration)

        name_text = view.findViewById(masterchefdevs.colectiv.ubb.chefs.R.id.pers_name_text)
        name_text.hint = "Name"
        tel_text = view.findViewById(masterchefdevs.colectiv.ubb.chefs.R.id.telefon_text)
        tel_text.hint = "Telephone"
        em_text = view.findViewById(masterchefdevs.colectiv.ubb.chefs.R.id.email_text)
        em_text.hint = "Email"
        error_text = view.findViewById(masterchefdevs.colectiv.ubb.chefs.R.id.reservation_error)

        name_text.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                error_text.setText("")
            }
        }
        tel_text.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                error_text.setText("")
            }
        }
        em_text.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                error_text.setText("")
            }
        }

        view.findViewById<Button>(masterchefdevs.colectiv.ubb.chefs.R.id.cancel_reservation_button)
            .setOnClickListener {
                this.dismiss()
            }

        view.findViewById<Button>(masterchefdevs.colectiv.ubb.chefs.R.id.save_reservation_button)
            .setOnClickListener {
                makeReservation()
            }

        builder.setView(view)
        reservationModel = ViewModelProvider(this).get(ReservationsViewModel::class.java);

        return builder.create()
    }

    // 0->ok
    // 1->telefon
    // 2->nume
    // 3->email
    fun validateData(telephone: String, name: String, email: String): Int {
        val REG = "^[1234567890]"
        var PATTERN: Pattern = Pattern.compile(REG)
        fun CharSequence.isPhoneNumber(): Boolean = PATTERN.matcher(this).find()
        if (name.length == 0)
            return 2
        if (!telephone.isPhoneNumber())
            return 1
        if (email.length == 0 || !email.contains("@"))
            return 3
        return 0
    }

    fun makeReservation() {
        val telefon: String = tel_text.text.toString()
        val nume_pers: String = name_text.text.toString()
        val email: String = em_text.text.toString()

        if (Api.tokenInterceptor.token == null || Api.tokenInterceptor.token!!.isEmpty()) {
            error_text.text = "Please sign in"
            return
        }

        val valid = validateData(telefon, nume_pers, email)
        if (valid == 0) {
            var reservationDto =
                ReservationDTO(table_id, 2, date, time, duration, telefon, nume_pers, email)

            reservationModel.makeReservation(reservationDto)
            reservationModel.livedataReservationSucceded.observe(this, {
                if (it == 1) {
                    //succes
                    val alertDialogBuilder = AlertDialog.Builder(myContext)
                    val alert = alertDialogBuilder
                        .setTitle("Reservation Completed")
                        .setPositiveButton("Ok") { dialog, it -> dis() }
                        .create()
                    alert.show()

                } else
                    if (it == 2) {
                        //error
                        error_text.text = "Cannot reserve. Please try again!"
                    }
            })
        } else {
            if (valid == 1)
                error_text.setText("invalid telephone number")
            if (valid == 2)
                error_text.setText("invalid name")
            if (valid == 3)
                error_text.setText("invalid email address")
        }
    }

    fun dis() {
        this.dismiss()
    }

}