package xyz.kuteki.xoyfriend

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression

class StatsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stats)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val statsText = findViewById<TextView>(R.id.statsTextView)

        val function = intent.getStringExtra("function")
        val from = intent.getIntExtra("from", 0)
        val to = intent.getIntExtra("to", 100)
        val points = intent.getIntExtra("points", 1000)

        val stats = calculateStats(function!!, from, to, points)

        statsText.text = stats

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }


    private fun calculateStats(function: String, from: Int, to: Int, points: Int): String {
        val range_length = to - from
        val step = range_length.toDouble() / points

        var sum = 0.0
        var count = 0
        var min = Double.MAX_VALUE
        var max = Double.MIN_VALUE

        var i = from.toDouble()
        while(i <= to.toDouble()) {
            val e = Expression(function)
            e.addArguments(Argument("x= $i"))

            val y = e.calculate()
            sum += y
            count++
            min = min.coerceAtMost(y)
            max = max.coerceAtLeast(y)

            i += step
        }

        val mean = sum / count
        val variance = calculateVariance(function, from, to, points, mean)
        val std_dev = Math.sqrt(variance)

        val f_mean = String.format("%.3f", sum / count)
        val f_variance = String.format("%.3f", calculateVariance(function, from, to, points, mean.toDouble()))
        val f_std_dev = String.format("%.3f", Math.sqrt(variance.toDouble()))
        val f_max = String.format("%.3f", max)
        val f_min = String.format("%.3f", min)
        val f_sum = String.format("%.3f", sum)

        return """
            |Stats for f(x) = $function
            |Mean: $f_mean
            |Variance: $f_variance
            |Standard Deviation: $f_std_dev
            |Max: $f_max
            |Min: $f_min
            |Sum: $f_sum
            |Count: $count
        """.trimMargin()
    }

    private fun calculateVariance(function: String, from: Int, to: Int, points: Int, mean: Double): Double {
        val range_length = to - from
        val step = range_length.toDouble() / points

        var variance = 0.0
        var i = from.toDouble()
        while(i <= to.toDouble()) {
            val e = Expression(function)
            e.addArguments(Argument("x= $i"))

            val y = e.calculate()
            variance += Math.pow(y - mean, 2.0)

            i += step
        }

        return variance / points
    }

}
