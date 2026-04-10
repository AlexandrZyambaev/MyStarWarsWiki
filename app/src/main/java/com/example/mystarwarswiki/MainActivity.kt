package com.example.mystarwarswiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mystarwarswiki.presentation.navigation.AppNavGraph
import com.example.mystarwarswiki.ui.theme.MyStarWarsWikiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyStarWarsWikiTheme {
                AppNavGraph()
            }
        }
    }
}