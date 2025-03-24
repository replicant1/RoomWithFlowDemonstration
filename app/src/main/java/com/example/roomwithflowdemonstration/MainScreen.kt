package com.example.roomwithflowdemonstration


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    modifier: Modifier,
    onClickInsertButton: () -> Unit,
    onClickGetAllRegionsAsStreamButton: () -> Unit,
    onClickGetAllRegionsAsStreamWithCancelButton: () -> Unit,
    onClickGetAllRegionsAsListButton: () -> Unit,
    onClickDeleteAllButton: () -> Unit,
    onClickListAllRegionsButton: () -> Unit,
) {
    Column(modifier = modifier) {
        Button(onClick = { onClickInsertButton.invoke() }) {
            Text("Insert new region")
        }

        Button(onClick = { onClickGetAllRegionsAsStreamButton.invoke() }) {
            Text("a) getAllRegionsAsStream()")
        }

        Button(onClick = { onClickGetAllRegionsAsStreamWithCancelButton.invoke() }) {
            Text("b) getAllRegionsAsStream() with CANCEL")
        }

        Button(onClick = { onClickGetAllRegionsAsListButton.invoke() }) {
            Text("getAllRegionsAsList()")
        }

        Button(onClick = { onClickDeleteAllButton.invoke() }) {
            Text("Delete all regions")
        }

        Button(onClick = { onClickListAllRegionsButton.invoke() }) {
            Text("List all regions")
        }

    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        Modifier
            .padding(8.dp)
            .width(500.dp)
            .height(500.dp),
        {},
        {},
        {},
        {},
        {},
        {},
    )
}