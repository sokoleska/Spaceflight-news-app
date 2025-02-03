package com.example.exam_screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.exam_screens.data.ArticleRepository
import com.example.exam_screens.ui.theme.ExamscreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArticleRepository.initialize(this)
        enableEdgeToEdge()
        setContent {
            ExamscreensTheme {
                SpaceApp()
            }
        }
    }
}