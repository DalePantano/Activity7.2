package com.example.activity72

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.activity72.ui.theme.Activity72Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.VideogameAsset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Activity72Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppOverview()
                }
            }
        }
    }
}

@Composable
fun AppOverview() {
    var expanded by remember { mutableStateOf(false) }
    var showHobbies by remember { mutableStateOf(false) }
    var animationTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationTrigger = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = "App Icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "My Hobbies App",
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(Color.Blue)
                .padding(4.dp)
        ) {
            DogItemButton(
                expanded = expanded,
                onClick = { expanded = !expanded }
            )
        }

        if (animationTrigger) {
            LaunchedEffect(expanded) {
                expanded = true
            }
        }

        if (expanded) {
            Text(
                text = "Here is some more information about the app it's all about my hobbies in life",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showHobbies = !showHobbies },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(if (showHobbies) "Hide Hobbies" else "Show Hobbies")
            }

            if (showHobbies) {
                HobbyList(hobbies = listOf("Reading", "Traveling", "Cooking", "Gaming"))
            }
        }
    }
}

@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.Blue)
        ) {
            Icon(
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    "Collapse"
                } else {
                    "Expand"
                },
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationAngle)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun HobbyList(hobbies: List<String>) {
    val expandedHobbies = remember { mutableStateMapOf<String, Boolean>() }

    Column {
        Text(
            text = "Hobbies:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        hobbies.forEach { hobby ->
            val isExpanded = expandedHobbies[hobby] ?: false
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { expandedHobbies[hobby] = !isExpanded }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = when (hobby) {
                            "Reading" -> Icons.Default.MenuBook
                            "Traveling" -> Icons.Default.Flight
                            "Cooking" -> Icons.Default.Restaurant
                            "Gaming" -> Icons.Default.VideogameAsset
                            else -> Icons.Default.Help
                        },
                        contentDescription = "Hobby Icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(if (isExpanded) Color.LightGray else Color.Transparent)
                            .padding(9.dp)
                    ) {
                        Text(
                            text = hobby,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                if (isExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Detailed information about this $hobby when i'm doing it its so much fun and having a great time learning and adventuring ",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppOverviewPreview() {
    Activity72Theme {
        AppOverview()
    }
}
