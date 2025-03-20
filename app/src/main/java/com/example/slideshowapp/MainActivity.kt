package com.example.slideshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.slideshowapp.ui.theme.SlideshowAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlideshowAppTheme {
                SlideshowLayout()
            }
        }
    }
}

@Composable
fun SlideshowLayout() {

    //states
    var currentIndex by remember { mutableStateOf(0) }
    var jumpInput by remember { mutableStateOf("") }
    //errorMessage state also included if the user types an image number that doesnt exist
    var errorMessage by remember { mutableStateOf("") }

    // Lists all the image ids added to resource manager
    val imageIds = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8
    )
    // Lists all the captions for pics in the strings.xml file
    val captionIds = listOf(
        R.string.caption1,
        R.string.caption2,
        R.string.caption3,
        R.string.caption4,
        R.string.caption5,
        R.string.caption6,
        R.string.caption7,
        R.string.caption8
    )



    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contains image and its caption
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Delicious Food",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                textAlign = TextAlign.Center,
                lineHeight = 50.sp
            )
            Image(
                painter = painterResource(id = imageIds[currentIndex]),
                contentDescription = stringResource(id = captionIds[currentIndex]),
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = captionIds[currentIndex]),
                style = MaterialTheme.typography.titleLarge
            )
        }

        // makes the row that holds the next and back buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //logic that ensures when next and back are hit we go to start of list or end of list if we are at end or start
            Button(onClick = {
                currentIndex = if (currentIndex - 1 < 0) imageIds.size - 1 else currentIndex - 1
            }) {
                Text(text = stringResource(id = R.string.back))
            }
            Button(onClick = {
                currentIndex = if (currentIndex + 1 >= imageIds.size) 0 else currentIndex + 1
            }) {
                Text(text = stringResource(id = R.string.next))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //row for the text box and search buttom
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // text field to search for specific image
            OutlinedTextField(
                value = jumpInput,
                onValueChange = {
                    jumpInput = it
                    errorMessage = ""
                },
                label = { Text(stringResource(id = R.string.enter_image_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorMessage.isNotEmpty(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            //button to click to search
            Button(onClick = {
                val number = jumpInput.toIntOrNull()
                //error handling so that you cant go to a number that doesnt exist (mainly I just found this on internet)
                if (number != null && number in 1..imageIds.size) {
                    currentIndex = number - 1  
                } else {
                    errorMessage = "Invalid number"
                }
            }) {
                Text(text = stringResource(id = R.string.go))
            }
        }
        //makes the box change on error
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SlideshowLayoutPreview() {
    SlideshowAppTheme {
        SlideshowLayout()
    }
}
