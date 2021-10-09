package com.rappi.android.utils

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScrollableColumn(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(0),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalGravity: Alignment.Horizontal = Alignment.Start,
    reverseScrollDirection: Boolean = false,
    isScrollEnabled: Boolean = true,
    //contentPadding: InnerPadding = InnerPadding(0.dp),
    children: @Composable ColumnScope.() -> Unit
) {
}

@Composable
fun ScrollableRow(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(0),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalGravity: Alignment.Vertical = Alignment.Top,
    reverseScrollDirection: Boolean = false,
    isScrollEnabled: Boolean = true,
    //contentPadding: InnerPadding = InnerPadding(0.dp),
    children: @Composable RowScope.() -> Unit
) {
}