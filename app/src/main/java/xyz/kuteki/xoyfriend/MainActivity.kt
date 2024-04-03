package xyz.kuteki.xoyfriend

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        License.iConfirmNonCommercialUse("Edwin Kristof")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val lineChart = LineChart(this)
        val mainLayout = findViewById<ViewGroup>(R.id.chartContainer)
        mainLayout.addView(lineChart)

        val drawButton = findViewById<Button>(R.id.drawButton)
        val inputText = findViewById<EditText>(R.id.functionTextInput)
        val outputText = findViewById<TextView>(R.id.outputTextView)
        val fromText = findViewById<EditText>(R.id.fromTextInput)
        val toText = findViewById<EditText>(R.id.toTextInput)
        val pointsText = findViewById<EditText>(R.id.pointsTextInput)
        drawButton.setOnClickListener {
            val function = inputText.text.toString()
            outputText.text = "f(x)=$function"
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            lineChart.data = generateLineData(function,fromText.text.toString().toInt(),toText.text.toString().toInt(),pointsText.text.toString().toInt())
            lineChart.invalidate()

        }



    }

    private fun generateLineData(function:String,from:Int,to:Int,points:Int): LineData {
        val entries = ArrayList<Entry>()
        val range_length = to - from
        val step = range_length.toDouble() / points


        var i = from.toDouble()
        while(i <= to.toDouble()) {
            val e = Expression(function)
            e.addArguments(Argument("x= $i"))

            val y = e.calculate()
            entries.add(Entry(i.toFloat(), y.toFloat()))
            i += step
        }

        val dataSet = LineDataSet(entries, "f(x)")
        dataSet.color = Color.RED
        dataSet.valueTextColor = Color.BLACK

        return LineData(dataSet)
    }
}