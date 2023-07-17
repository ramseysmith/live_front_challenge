package com.example.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.presentation.R
import com.example.presentation.contract.CharacterUiModel

@Composable
fun CharacterDetailContentView(
    characterUiModel: CharacterUiModel,
    backClickedAction: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (backButton, tittle) = createRefs()
                Image(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .constrainAs(backButton) {
                            start.linkTo(parent.start, 8.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            backClickedAction()
                        },
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back button"
                )
                Text(
                    text = characterUiModel.name,
                    modifier = Modifier.constrainAs(tittle) {
                        start.linkTo(backButton.end, 8.dp)
                        end.linkTo(parent.end, 8.dp)
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(250.dp)
                    .padding(16.dp),
                contentScale = ContentScale.FillWidth,
                model = characterUiModel.imageUrl,
                contentDescription = "image of ${characterUiModel.name}",
                error = painterResource(R.drawable.baseline_person_24)
            )
            Text(
                text = characterUiModel.shortDescription,
                fontSize = 20.sp,
            )
        }
    }
}