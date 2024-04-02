package xyz.kuteki.xoyfriend

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        License.iConfirmNonCommercialUse("Edwin Kristof")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lineChart = LineChart(this)
        val mainLayout = findViewById<ViewGroup>(R.id.chartContainer)
        mainLayout.addView(lineChart)

        val button = findViewById<Button>(R.id.button)
        val text_box = findViewById<EditText>(R.id.editTextText)
        button.setOnClickListener {
            val function = text_box.text.toString()

            lineChart.data = generateLineData(function)
            lineChart.invalidate()
        }



    }

    private fun generateLineData(function:String): LineData {
        val entries = ArrayList<Entry>()
        for (i in 0..9) {
            val e = Expression(function)
            e.addArguments(Argument("x= $i"))

            val y = e.calculate()
            entries.add(Entry(i.toFloat(), y.toFloat()))
        }

        val dataSet = LineDataSet(entries, "f(x)")
        dataSet.color = Color.RED
        dataSet.valueTextColor = Color.BLACK

        return LineData(dataSet)
    }
}