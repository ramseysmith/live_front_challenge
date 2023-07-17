package com.example.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.presentation.R
import com.example.presentation.contract.CharacterUiModel

@Composable
fun CharacterListContentView(
    characters: List<CharacterUiModel>,
    characterClickedAction: (name: String) -> Unit
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "The Wire Characters",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        items(characters) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable {
                        characterClickedAction(it.name)
                    },
                backgroundColor = Color.Green.copy(alpha = 0.2f),
            ) {
                Row(
                    modifier = Modifier.fillParentMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 16.dp)
                            .fillMaxWidth(),
                        model = it.imageUrl,
                        contentScale = ContentScale.Fit,
                        contentDescription = "image of ${it.name}",
                        error = painterResource(R.drawable.baseline_person_24)
                    )
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            text = it.name,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
}