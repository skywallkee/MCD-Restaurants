package masterchefdevs.colectiv.ubb.chefs.data

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Layout
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Table
import java.util.*


class MainReservationFragment : Fragment() {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var myContext: Context
    private var date = MutableLiveData<Calendar>().apply { value = Calendar.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myContext = inflater.context
        return inflater.inflate(R.layout.fragment_rezerva_main, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val dateView = view.findViewById<TextView>(R.id.date_view)
        val timeView = view.findViewById<TextView>(R.id.time_view)

        dateView.setText(date.value?.get(Calendar.YEAR)?.toString() + "-"+
                (date.value?.get(Calendar.MONTH))?.plus(1).toString() +"-"+
                    date.value?.get(Calendar.DAY_OF_MONTH)?.toString())

        val min = date.value?.get(Calendar.MINUTE)
        if (min != null) {
            if (min < 10) {
                val smin = "0" + min.toString()
                timeView.setText(
                    date.value?.get(Calendar.HOUR_OF_DAY).toString() + " : " +
                            smin
                )
            }
            else
                timeView.setText(
                    date.value?.get(Calendar.HOUR_OF_DAY).toString() + " : " +
                            date.value?.get(Calendar.MINUTE).toString()
                )
        }

        setMyDatePicker()
        setMyTimePicker()

        //view.findViewById<TextView>(R.id.restaurant_name).setText(viewModel.restaurant.value?.name)
        //view.findViewById<TextView>(R.id.restaurant_address).setText(viewModel.restaurant.value?.address)
        //view.findViewById<TextView>(R.id.rating_stars).setRawInputType(3)

        //
        val table1 = Table(1, "masa1", 4, 100, 100, 100, 150, 150, 150, 150, 100)
        val tables : MutableList<Table> = mutableListOf(table1)
        val layout = Layout("parter", 350, 450, tables)
        val layouts : MutableList<Layout> = mutableListOf(layout)
        val restaurant = Restaurant("112", "MecDonalds", "Oradea, str. Unirii nr 14", 3.0f, layouts)

        view.findViewById<TextView>(R.id.restaurant_name).setText(restaurant.name)
        view.findViewById<TextView>(R.id.restaurant_address).setText(restaurant.address)
        view.findViewById<RatingBar>(R.id.rating_stars).rating = restaurant.stars
        val spinner = view.findViewById<Spinner>(R.id.floors_spinner)
        val spinnerAdapter: SpinnerAdapter = ArrayAdapter(
            myContext,
            R.layout.support_simple_spinner_dropdown_item,
            layouts
        )
        spinner.adapter = spinnerAdapter

        val butt = Button(myContext)

        butt.setText("5")
        butt.id = View.generateViewId()
        butt.setBackgroundColor(Color.GREEN)

        val layout1 =view.findViewById<ConstraintLayout>(R.id.layout)
        val lp = RelativeLayout.LayoutParams(50, 50)
        layout1.addView(butt, lp)

        val set = ConstraintSet()
        set.clone(layout1)
        set.connect(butt.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 150)
        set.connect(
            butt.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            50
        )
        set.constrainMinHeight(butt.id, 100)
        set.constrainMaxHeight(butt.id, 100)
        set.constrainMinWidth(butt.id, 100)
        set.constrainMaxWidth(butt.id, 100)
        set.applyTo(layout1)
         //

        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setMyDatePicker(){
        val dateView = view?.findViewById<TextView>(R.id.date_view)
        view?.findViewById<Button>(R.id.date_button)?.setOnClickListener{
            val datePickerDialog = date.value?.get(Calendar.YEAR)?.let { it1 ->
                date.value?.get(Calendar.MONTH)?.let { it2 ->
                    date.value?.get(Calendar.DAY_OF_MONTH)?.let { it3 ->
                        DatePickerDialog(
                            myContext,
                            { view, nyear, nmonthOfYear, ndayOfMonth ->
                                date.value?.set(Calendar.YEAR, nyear)
                                date.value?.set(Calendar.MONTH, nmonthOfYear)
                                date.value?.set(Calendar.DAY_OF_MONTH, ndayOfMonth)
                                Log.d(TAG,date.value.toString())
                                dateView?.setText(nyear.toString() + "-"+ (nmonthOfYear+1).toString() +"-"+ndayOfMonth.toString())
                            }, it1, it2, it3)
                    }
                }
            }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setMyTimePicker(){
        val timeView = requireView().findViewById<TextView>(R.id.time_view)
        view?.findViewById<Button>(R.id.time_button)?.setOnClickListener{
            val timePickerDialog = date.value?.get(Calendar.HOUR_OF_DAY)?.let { it1 ->
                date.value?.get(Calendar.MINUTE)?.let { it2 ->
                    TimePickerDialog(
                        myContext,
                        { view, hours, min ->
                            date.value?.set(Calendar.HOUR_OF_DAY, hours)
                            date.value?.set(Calendar.MINUTE, min)
                            Log.d(TAG,"h"+hours.toString())
                            Log.d(TAG, timeView.toString())
                            var smin = ""
                            if (min < 10 )
                                smin+="0"+min.toString()
                            else
                                smin += min.toString()
                            timeView.setText(hours.toString() + " : " + smin)
                        }, it1, it2, true
                    )

                }
            }
            if (timePickerDialog != null) {
                timePickerDialog.show()
            }
        }
    }
}