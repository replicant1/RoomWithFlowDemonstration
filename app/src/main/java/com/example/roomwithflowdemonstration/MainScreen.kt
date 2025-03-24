package com.example.roomwithflowdemonstration


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(
    onClickInsertButton: () -> Unit,
    onClickGetAllRegionsAsStreamButton: () -> Unit,
    onClickGetAllRegionsAsStreamWithCancelButton: () -> Unit,
    onClickGetAllRegionsAsListButton: () -> Unit,
    onClickDeleteAllButton: () -> Unit,
    onClickListAllRegionsButton: () -> Unit
) {
    Column() {
        Button(onClick = { onClickInsertButton.invoke() }) {
            Text("Insert new region")
        }

        Button(onClick = { onClickGetAllRegionsAsStreamButton.invoke() }) {
            Text("getAllRegionsAsStream()")
        }

        Button(onClick = { onClickGetAllRegionsAsStreamWithCancelButton.invoke()}) {
            Text("getAllRegsionsAsStream() with CANCEL")
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