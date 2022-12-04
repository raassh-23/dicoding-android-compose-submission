package com.raassh.dicodingcomposefinal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.ui.screen.HomeScreen
import com.raassh.dicodingcomposefinal.ui.screen.about.AboutScreen
import com.raassh.dicodingcomposefinal.ui.theme.DicodingComposeFinalTheme

@Composable
fun NotMyAnimeListApp(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = Screen.titleFromRoute(currentRoute ?: "")))
        }, navigationIcon = if (currentRoute != Screen.Home.route) ({
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }) else null, actions = {
            if (currentRoute == Screen.Home.route) {
                IconButton(onClick = {
                    navController.navigate(Screen.About.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = stringResource(
                            R.string.about_content_description
                        )
                    )
                }
            }
        })
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NotMyAnimeListAppPreview() {
    DicodingComposeFinalTheme {
        NotMyAnimeListApp()
    }
}