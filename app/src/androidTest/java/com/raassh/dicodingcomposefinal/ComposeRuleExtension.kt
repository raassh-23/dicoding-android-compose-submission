package com.raassh.dicodingcomposefinal

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
) = onNodeWithText(activity.getString(id))

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithContentDescriptionWithStringId(
    @StringRes id: Int
) = onNodeWithContentDescription(activity.getString(id))