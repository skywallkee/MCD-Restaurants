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
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Layout
import masterchefdevs.colectiv.ubb.chefs.data.model.Reservation
import masterchefdevs.colectiv.ubb.chefs.data.model.Table
import java.time.Month
import java.util.Calendar
import java.util.Date


class MainReservationFragment : Fragment(), NumberPicker.OnValueChangeListener {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var myContext: Context
    private var date = MutableLiveData<Calendar>().apply { value = Calendar.getInstance() }
    private var duration = MutableLiveData<Int>().apply { value = 1 }
    private var layouts: MutableList<Layout> = mutableListOf()

    private var flag = MutableLiveData<Int>().apply{value =0}

    companion object {
        var ITEM_ID: Number = -1;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myContext = inflater.context
        Log.d(TAG, "in on create view- arguments: " + arguments)
        arguments?.let {
            ITEM_ID = it.getString("ITEM_ID")?.toInt() ?: 0
        }
        return inflater.inflate(R.layout.fragment_rezerva_main, container, false)
    }

    fun convertToDp(px: Int): Int {
        val scale = requireContext().resources.displayMetrics.density
        return (px * scale + 0.5f).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAllElements();

        val spinner = view.findViewById<Spinner>(R.id.floors_spinner)
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                setLayout(layouts[i].floor)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
        val id = ITEM_ID;
        viewModel.getRestaurant(id)

        viewModel.getMeseRestaurant(id)
        viewModel.getPeretiRestaurant(id)

        viewModel.restaurant.observe(viewLifecycleOwner, { restaurant ->
            view.findViewById<TextView>(R.id.restaurant_name)?.setText(restaurant.nameR)
            view.findViewById<TextView>(R.id.restaurant_address).setText(restaurant.adresa)
        })
        viewModel.rating.observe(viewLifecycleOwner, { rating ->
            view.findViewById<RatingBar>(R.id.rating_stars).rating = rating
        })
        viewModel.tables.observe(viewLifecycleOwner, { tables ->
            tables.filter { table -> table.id_R == id }.forEach { table ->
                val l = layouts.find { l -> l.floor == (table.etaj) }
                if (l != null) {
                    l.tables.add(table)
                } else {
                    layouts.add(Layout(table.etaj, mutableListOf(table), mutableListOf()))
                }
            }
            val spinnerAdapter: SpinnerAdapter = ArrayAdapter(
                myContext, R.layout.support_simple_spinner_dropdown_item, layouts
            )
            spinner.adapter = spinnerAdapter
        })
        viewModel.walls.observe(viewLifecycleOwner, { walls ->
            walls.filter { wall -> wall.id_R == id }.forEach { wall ->
                val l = layouts.find { l -> l.floor == (wall.etaj) }
                if (l != null) {
                    l.walls.add(wall)
                } else {
                    layouts.add(Layout(wall.etaj, mutableListOf(), mutableListOf(wall)))
                }
            }
        })
        viewModel.reservations.observe(viewLifecycleOwner, { rez ->
            setAllTablesGreen()
            rez.forEach { reservation ->
                val table = getTableFromLayouts(reservation.id_M)
                if (checkIfReserved(reservation)) {
                    Log.d(TAG, "red")
                    table?.reserved = true
                    table?.button?.setBackgroundColor(Color.RED)
                }
            }
        })
    }

    fun setAllTablesGreen(){
        layouts.forEach { layout ->
            layout.tables.forEach{table ->
                table.reserved = false
                table.button?.setBackgroundColor(Color.GREEN)
            }
        }
    }

    fun getMilisec(cal: Calendar): Long {
        return cal.timeInMillis
    }

    fun getTableFromLayouts(id: Int): Table? {
        layouts.forEach { layout ->
            layout.tables.forEach { table ->
                if (table.id == id) {
                    return table
                }
            }
        }
        return null
    }

    //true if reserved
    fun checkIfReserved(res: Reservation): Boolean {
        //crurrent reservation
        val currResDate = getMilisec(date.value!!)
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.YEAR, date.value!!.get(Calendar.YEAR))
        cal.set(Calendar.MONTH, date.value!!.get(Calendar.MONTH))
        cal.set(Calendar.DAY_OF_MONTH, date.value!!.get(Calendar.DAY_OF_MONTH))
        cal.set(Calendar.HOUR_OF_DAY, date.value!!.get(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MINUTE, date.value!!.get(Calendar.MINUTE))
        cal.add(Calendar.HOUR_OF_DAY, duration.value!!)
        val currResDatePlusDuration = getMilisec(cal)
        //old reservation
        val calRes: Calendar = Calendar.getInstance()
        calRes.set(Calendar.YEAR, res.data_conv.get(Calendar.YEAR))
        calRes.set(Calendar.MONTH, res.data_conv.get(Calendar.MONTH))
        calRes.set(Calendar.DAY_OF_MONTH, res.data_conv.get(Calendar.DAY_OF_MONTH))
        calRes.set(Calendar.HOUR_OF_DAY, res.data_conv.get(Calendar.HOUR_OF_DAY))
        calRes.set(Calendar.MINUTE, res.data_conv.get(Calendar.MINUTE))
        val oldRes = getMilisec(calRes)
        val timeh = res.timp_conv.hours
        val minh = res.timp_conv.minutes
        calRes.add(Calendar.HOUR_OF_DAY, timeh)
        calRes.add(Calendar.MINUTE,minh)
        val oldDatePlusDuration = getMilisec(calRes)

        if ((currResDate>oldRes) and (currResDate<oldDatePlusDuration))
            return true
        if ((currResDatePlusDuration>oldRes) and (currResDatePlusDuration<oldDatePlusDuration))
            return true
        if ((currResDate<oldRes) and (currResDatePlusDuration>oldDatePlusDuration))
            return true
        return false
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setLayout(etaj: String) {
        val constraintLayout = view?.findViewById<ConstraintLayout>(R.id.layout)
        constraintLayout?.removeAllViewsInLayout()
        layouts.findLast { layout -> layout.floor == etaj }?.tables?.forEach { table ->
            val butt = Button(myContext)
            butt.setText(table.nr_locuri.toString())
            butt.id = View.generateViewId()
            table.id_button = butt.id
            table.button = butt
            if(table.reserved)
                butt.setBackgroundColor(Color.RED)
            else
                butt.setBackgroundColor(Color.GREEN)

            // w = 370  h = 370
            val h = convertToDp((table.Dy - table.Ay) * 370 / 100)
            val w = convertToDp((table.Bx - table.Ax) * 370 / 100)
            val left = convertToDp(table.Ax * 370 / 100)
            val top = convertToDp(table.Ay * 370 / 100)

            val lp = RelativeLayout.LayoutParams(w, h)
            constraintLayout?.addView(butt, lp)
            val set = ConstraintSet()
            set.clone(constraintLayout)
            set.connect(butt.id,ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,left)
            set.connect(butt.id,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP, top )
            set.constrainMinHeight(butt.id, h)
            set.constrainMaxHeight(butt.id, h)
            set.constrainMinWidth(butt.id, w)
            set.constrainMaxWidth(butt.id, w)
            set.applyTo(constraintLayout)
        }

        //pereti
        layouts.findLast { layout -> layout.floor == etaj }?.walls?.forEach { wall ->
            val butt = Button(myContext)
            butt.id = View.generateViewId()
            butt.setBackgroundColor(Color.GRAY)
            butt.setOnClickListener {  }
            // w = 370  h = 380
            val h = convertToDp((wall.Dy - wall.Ay) * 380 / 100)
            val w = convertToDp((wall.Bx - wall.Ax) * 370 / 100)
            val left = convertToDp(wall.Ax * 370 / 100)
            val top = convertToDp(wall.Ay * 380 / 100)

            val lp = RelativeLayout.LayoutParams(w, h)
            constraintLayout?.addView(butt, lp)

            val set = ConstraintSet()
            set.clone(constraintLayout)
            set.connect(
                butt.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                left
            )
            set.connect(butt.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, top)
            set.constrainMinHeight(butt.id, h)
            set.constrainMaxHeight(butt.id, h)
            set.constrainMinWidth(butt.id, w)
            set.constrainMaxWidth(butt.id, w)
            set.applyTo(constraintLayout)
        }
    }

    fun setDuration(){
        view?.findViewById<NumberPicker>(R.id.duration_numer_picker)?.setOnValueChangedListener(this)
    }

    fun makeReservation(){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAllElements(){
        Log.d(TAG, "inside setallElements")
        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        setMyDatePicker()
        setMyTimePicker()
        setDuration()
        val dateView = view?.findViewById<TextView>(R.id.date_view)
        val timeView = view?.findViewById<TextView>(R.id.time_view)
        dateView?.setText(
            date.value?.get(Calendar.YEAR)?.toString() + "-" +
                    (date.value?.get(Calendar.MONTH))?.plus(1).toString() + "-" +
                    date.value?.get(Calendar.DAY_OF_MONTH)?.toString()
        )

        val min = date.value?.get(Calendar.MINUTE)
        if (min != null) {
            if (min < 10) {
                val smin = "0" + min.toString()
                timeView?.setText(
                    date.value?.get(Calendar.HOUR_OF_DAY).toString() + " : " +
                            smin)
            } else
                timeView?.setText(
                    date.value?.get(Calendar.HOUR_OF_DAY).toString() + " : " +
                            date.value?.get(Calendar.MINUTE).toString() )
        }
        view?.findViewById<Button>(R.id.back_to_map)?.setOnClickListener {
            findNavController().navigate(R.id.action_layout_to_map, Bundle().apply {
                putString("ITEM_ID", ITEM_ID.toString())
            })
        }
        view?.findViewById<NumberPicker>(R.id.duration_numer_picker)?.minValue=1;
        view?.findViewById<NumberPicker>(R.id.duration_numer_picker)?.maxValue=10;
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
                                Log.d(TAG, date.value.toString())
                                flag.value=1
                                dateView?.setText(nyear.toString() + "-" + (nmonthOfYear + 1).toString() + "-" + ndayOfMonth.toString())
                                Log.d(TAG, "new DAte: " + date.value!!.get(Calendar.YEAR).toString()+" " + date.value!!.get(Calendar.MONTH)+ " " + date.value!!.get(Calendar.DAY_OF_MONTH))
                            }, it1, it2, it3
                        )
                    }
                }
            }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }
        flag.observe(viewLifecycleOwner, {
            if (flag.value==1) {
               val stringDate: String = date.value!!.get(Calendar.YEAR).toString() + "-" +
                       (date.value!!.get(Calendar.MONTH)+1).toString() + "-" +
                       date.value!!.get(Calendar.DAY_OF_MONTH).toString();
                viewModel.getReservations(ITEM_ID, stringDate)
                flag.value = 0
            }
        })
        duration.observe(viewLifecycleOwner, {
            val stringDate: String = date.value!!.get(Calendar.YEAR).toString() + "-" +
                    (date.value!!.get(Calendar.MONTH)+1).toString() + "-" +
                    date.value!!.get(Calendar.DAY_OF_MONTH).toString();
            viewModel.getReservations(ITEM_ID, stringDate)
        })
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
                            Log.d(TAG, "h" + hours.toString())
                            Log.d(TAG, timeView.toString())
                            var smin = ""
                            if (min < 10)
                                smin += "0" + min.toString()
                            else
                                smin += min.toString()
                            timeView.setText(hours.toString() + " : " + smin)
                            flag.value=1
                        }, it1, it2, true
                    )
                }
            }
            if (timePickerDialog != null) {
                timePickerDialog.show()
            }
        }
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        duration.value = newVal
    }
}