package xyz.kuteki.xoyfriend

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HelpActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_help)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val helpTextView = findViewById<TextView>(R.id.helpTextView)
        """
     Welcome to the XOYFriend App! This application is designed to help you visualize and analyze mathematical functions through an intuitive and interactive interface. Whether you're a student, teacher, or math enthusiast, this guide will walk you through the essential features and functionalities of the app, ensuring you get the most out of your experience.
    
    Getting Started
    When you first launch the XOYFriend App, you'll be greeted with the main screen, which serves as your workspace for entering and plotting mathematical functions. The main screen is equipped with input fields for defining your function and specifying the range and number of points for the graph. The function input field is where you enter the mathematical expression you wish to plot, using standard notation such as sin(x), cos(x), or more complex functions like x^2 + 3*x + 5.
    
    Input Fields and Plotting
    In addition to the function input, you will find three more fields labeled "From," "To," and "Points." These fields allow you to define the range over which the function will be plotted and the number of points that will be used to draw the graph. For example, entering 0 in the "From" field and 100 in the "To" field will plot the function from x = 0 to x = 100. The "Points" field determines the resolution of the graph; more points will result in a smoother curve. After filling in these details, simply press the "Draw" button to generate the graph. If any of these fields are left empty, the app will use default values (From = 0, To = 100, Points = 100) to plot the function.
    
    Interactive Graph and Error Handling
    Once the function is plotted, the graph provides interactive features such as double-tap to zoom and pinch-to-zoom, allowing you to explore different parts of the graph in detail. You can also drag the graph to navigate through different sections. If there is an error in your function input or if the function is empty, an error message will be displayed in the output text view, helping you quickly identify and correct the issue.""".also { helpTextView.text = it }
    }
}