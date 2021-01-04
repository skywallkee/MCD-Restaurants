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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Layout
import masterchefdevs.colectiv.ubb.chefs.data.model.Reservation
import java.time.Month
import java.util.Calendar
import java.util.Date


class MainReservationFragment : Fragment(), NumberPicker.OnValueChangeListener {
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var myContext: Context
    private var date = MutableLiveData<Calendar>().apply { value = Calendar.getInstance() }
    private var duration: Int = 0
    private var layouts: MutableList<Layout> = mutableListOf()
    private var reservations: MutableList<Reservation> = mutableListOf();

    companion object {
        var ITEM_ID: Number = -1;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myContext = inflater.context
        Log.d(TAG,"in on create view- arguments: "+arguments)
        arguments?.let {
            val a = it.getString("ITEM_ID")
            Log.d(TAG,"in on create view: "+a)
            ITEM_ID = it.getString("ITEM_ID")?.toInt() ?: 0
        }
        return inflater.inflate(R.layout.fragment_rezerva_main, container, false)
    }

    fun convertToDp(px: Int): Int{
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
        Log.d(TAG, id.toString())
        viewModel.getRestaurant(id)

        viewModel.getMeseRestaurant(id, date.value!!.time)
        viewModel.getPeretiRestaurant(id)
        viewModel.getReservations(ITEM_ID, Date())

        viewModel.restaurant.observe(viewLifecycleOwner, { restaurant ->
            view.findViewById<TextView>(R.id.restaurant_name)?.setText(restaurant.nameR)
            view.findViewById<TextView>(R.id.restaurant_address).setText(restaurant.adresa)
        })

        viewModel.rating.observe(viewLifecycleOwner, {rating ->
            view.findViewById<RatingBar>(R.id.rating_stars).rating = rating
        })

        viewModel.tables.observe(viewLifecycleOwner, { tables ->
            tables.filter { table -> table.id_R == id }.forEach { table ->
                val l = layouts.find { l -> (l.floor == (table.etaj)) and (table.id_R.equals(id)) }
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
                val l = layouts.find { l -> (l.floor == (wall.etaj)) and (wall.id_R.equals(id)) }
                if (l != null) {
                    l.walls.add(wall)
                } else {
                    layouts.add(Layout(wall.etaj, mutableListOf(), mutableListOf(wall)))
                }
            }
        })
        viewModel.reservations.observe(viewLifecycleOwner,{

            it.forEach {
                Log.d(TAG,it.data_conv.toString())
                var tableButton = view.findViewById<Button>(it.id_M)    // nu intra aici !!!!11
                if (tableButton != null)
                    if (viewModel.isReserved(it, Date(date.value!!.get(Calendar.YEAR), date.value!!.get(Calendar.MONTH), date.value!!.get(Calendar.DAY_OF_MONTH)),0))
                    {
                        tableButton.setBackgroundColor(Color.RED)
                        Log.d(TAG, tableButton.id.toString())
                    };

            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setLayout(etaj: String) {
        val constraintLayout = view?.findViewById<ConstraintLayout>(R.id.layout)
        constraintLayout?.removeAllViewsInLayout()
        layouts.findLast { layout -> layout.floor == etaj }?.tables?.forEach { table ->
            val butt = Button(myContext)
            butt.setText(table.nr_locuri.toString())
            butt.id = table.id
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
        date.observe(viewLifecycleOwner, {
            val date = Date(it.get(Calendar.YEAR), it.get(Calendar.MONTH)+1, it.get(Calendar.DAY_OF_MONTH))
            viewModel.getReservations(ITEM_ID, date)
            //viewModel.setReservedToAllTables(date)
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
        duration = newVal
    }
}