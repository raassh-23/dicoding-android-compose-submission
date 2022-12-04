package com.raassh.dicodingcomposefinal.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.raassh.dicodingcomposefinal.R
import com.raassh.dicodingcomposefinal.assertCurrentRouteName
import com.raassh.dicodingcomposefinal.data.model.fakeAnimeList
import com.raassh.dicodingcomposefinal.di.RepositoryInjection
import com.raassh.dicodingcomposefinal.onNodeWithContentDescriptionWithStringId
import com.raassh.dicodingcomposefinal.onNodeWithStringId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotMyAnimeListAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        RepositoryInjection.provideAnimeRepository().resetAnimeList()

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NotMyAnimeListApp(navController = navController)
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToDetailWithData() {
        composeTestRule.onNodeWithTag("anime_list").performScrollToIndex(15)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title)
            .assertIsDisplayed()
    }

    @Test
    fun navHost_clickItem_navigatesBackFromDetail() {
        composeTestRule.onNodeWithTag("anime_list").performScrollToIndex(15)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToAbout() {
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.about_content_description)
            .performClick()
        navController.assertCurrentRouteName(Screen.About.route)
    }

    @Test
    fun navHost_clickItem_navigatesBackFromAbout() {
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.about_content_description)
            .performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToDetailWithWrongData() {
        composeTestRule.onNodeWithTag("anime_list").performScrollToIndex(15)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(fakeAnimeList[16].title)
            .assertDoesNotExist()
    }

    @Test
    fun animeList_searchAnimeExist() {
        composeTestRule.onNodeWithTag("search_bar").performTextInput("Naruto")
        composeTestRule.onNodeWithTag("anime_list").onChildAt(0)
            .assert(hasText("Naruto"))
    }

    @Test
    fun animeList_searchAnimeNotExist() {
        composeTestRule.onNodeWithTag("search_bar").performTextInput("asdfghjkl")
        composeTestRule.onNodeWithStringId(R.string.empty_anime_list)
            .assertIsDisplayed()
    }

    @Test
    fun animeDetail_changeWatchStatus() {
        composeTestRule.onNodeWithTag("anime_list").performScrollToIndex(15)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.expand).assert(hasText(
            composeTestRule.activity.getString(R.string.untracked)))
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.expand).performClick()
        composeTestRule.onNodeWithTag("dropdown_menu").assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.watching).performClick()
        composeTestRule.onNodeWithTag("dropdown_menu").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.expand).assert(hasText(
            composeTestRule.activity.getString(R.string.watching)))
    }

    @Test
    fun animeList_filteredEmpty() {
        composeTestRule.onNodeWithStringId(R.string.watching).performClick()
        composeTestRule.onNodeWithStringId(R.string.empty_anime_list)
            .assertIsDisplayed()
    }

    @Test
    fun animeList_filteredNotEmpty() {
        composeTestRule.onNodeWithTag("anime_list").performScrollToIndex(15)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(fakeAnimeList[15].title)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.expand).performClick()
        composeTestRule.onNodeWithStringId(R.string.watching).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.watching).performClick()
        composeTestRule.onNodeWithTag("anime_list").onChildAt(0)
            .assert(hasText(fakeAnimeList[15].title))
    }
}