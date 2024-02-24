package xyz.teamgravity.receivecontentdemo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.hasMediaType
import androidx.compose.foundation.content.receiveContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField2
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import xyz.teamgravity.receivecontentdemo.ui.theme.ReceiveContentDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceiveContentDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        val text = rememberTextFieldState()
                        var image by remember { mutableStateOf(Uri.EMPTY) }

                        AsyncImage(
                            model = image,
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(150.dp)
                        )
                        BasicTextField2(
                            state = text,
                            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                                .padding(16.dp)
                                .receiveContent(
                                    hintMediaTypes = setOf(MediaType.All),
                                    onReceive = { content ->
                                        if (content.hasMediaType(MediaType.Image)) {
                                            val clip = content.clipEntry.clipData
                                            for (index in 0 until clip.itemCount) {
                                                image = clip.getItemAt(index)?.uri ?: continue
                                                break
                                            }
                                        }
                                        return@receiveContent content
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}