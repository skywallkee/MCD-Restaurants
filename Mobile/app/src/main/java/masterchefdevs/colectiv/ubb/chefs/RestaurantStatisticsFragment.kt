package masterchefdevs.colectiv.ubb.chefs

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantViewModel


class RestaurantStatisticsFragment :  Fragment() {
    private var chart: BarChart? = null
    private var barData: BarData? = BarData()
    private var chartH: BarChart? = null
    private var barDataH: BarData? = BarData()
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var myContext: Context
    private val DAYS = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    private val HOURS = arrayOf(
        "0-2",
        "2-4",
        "4-6",
        "6-8",
        "8-10",
        "10-12",
        "12-14",
        "14-16",
        "16-18",
        "18-20",
        "20-22",
        "22-24"
    )
    private val daysOfWeek = arrayOf(
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday", "Sunday"
    )

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
            val a = it.getString("ITEM_ID")
            Log.d(TAG, "in on create view: " + a)
            ITEM_ID = it.getString("ITEM_ID")?.toInt() ?: 0
        }
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpElements()
        chart = requireView().findViewById(R.id.chart_by_day)
        chartH = requireView().findViewById(R.id.chart_by_hour)
        createChartData()

        view.findViewById<Button>(R.id.back2map).setOnClickListener {
            findNavController().navigate(
                R.id.action_statistics_to_map,
                Bundle().apply {
                    putString("ITEM_ID", ITEM_ID.toString())
                })
        }

        var daySpinner = view.findViewById<Spinner>(R.id.daySpinner);
        val spinnerAdapter: SpinnerAdapter = ArrayAdapter(
            myContext, R.layout.support_simple_spinner_dropdown_item, daysOfWeek
        )
        daySpinner.adapter = spinnerAdapter

        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                changeDay(i)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }

    fun changeDay(day: Int) {
        viewModel.getHourStat(ITEM_ID.toInt(), day)
    }

    private fun setUpElements() {
        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        viewModel.getRestaurant(ITEM_ID.toInt())
        viewModel.getDayStat(ITEM_ID.toInt())
        viewModel.getHourStat(ITEM_ID.toInt(),0)
        viewModel.restaurant.observe(viewLifecycleOwner, { restaurant ->
            view?.findViewById<TextView>(R.id.restaurant_name_stat)?.setText(restaurant.nameR)
            view?.findViewById<TextView>(R.id.restaurant_address_stat)?.setText(restaurant.adresa)
            view?.findViewById<RatingBar>(R.id.rating_stars_stat)?.rating = 3.0f
        })
    }

    private fun prepareChartData() {
        barData?.setValueTextSize(12f)
        chart!!.data = barData
        chart!!.invalidate()
    }

    private fun prepareChartDataH() {
        barDataH?.setValueTextSize(12f)
        chartH!!.data = barDataH
        chartH!!.invalidate()
    }

    private fun createChartData() {
        viewModel.dayStat.observe(viewLifecycleOwner, { dayStatDto ->
            Log.d(TAG, "frieday: " + dayStatDto.Friday.toString())
            val values: ArrayList<BarEntry> = ArrayList()
            val list = listOf(
                dayStatDto.Sunday, dayStatDto.Monday, dayStatDto.Tuesday,
                dayStatDto.Wednesday, dayStatDto.Thursday, dayStatDto.Friday, dayStatDto.Saturday
            )

            for (i in 0 until 7) {
                val x = i.toFloat()
                val y = list[i].toFloat()

                values.add(BarEntry(x, y))
            }
            val set1 = BarDataSet(values, "Statistics by day of the week")
            set1.setColors(
                Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.MAGENTA,
                Color.CYAN,
                Color.GRAY,
                Color.RED
            )
            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            barData = BarData(dataSets)

            configureChartAppearance()
            prepareChartData()
        })
        viewModel.hourStat.observe(viewLifecycleOwner, { list ->
            val values: ArrayList<BarEntry> = ArrayList()
            for (i in 0 until HOURS.size) {
                val x = i.toFloat()
                val y: Float = list[i].toFloat()
                values.add(BarEntry(x, y))
            }
            val set1 = BarDataSet(values, "Statistics by hours")
            set1.setColors(
                Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.MAGENTA,
                Color.CYAN,
                Color.GRAY,
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.MAGENTA,
                Color.CYAN,
            )
            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            barDataH = BarData(dataSets)

            configureChartAppearanceH()
            prepareChartDataH()
        })
    }

    fun configureChartAppearance() {
        chart!!.description.isEnabled = false
        chart!!.animateY(2000)
        val xAxis = chart!!.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return DAYS.get(value.toInt())
            }
        }
    }

    fun configureChartAppearanceH() {
        chartH!!.description.isEnabled = false
        chartH!!.animateY(2000)
        val xAxisH = chartH!!.xAxis
        xAxisH.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return HOURS.get(value.toInt())
            }
        }
    }
}