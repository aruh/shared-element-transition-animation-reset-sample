package com.example.sharedelementtransitionanimationresetsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sharedelementtransitionanimationresetsample.ui.theme.SharedElementTransitionAnimationResetSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedElementTransitionAnimationResetSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Using movableContentOf")
                        Spacer(Modifier.height(8.dp))
                        MovableContentSharedTransition()

                        Spacer(Modifier.height(96.dp))

                        Text("No movableContentOf")
                        Spacer(Modifier.height(8.dp))
                        SharedTransition()

                        Spacer(Modifier.height(96.dp))

                        Text("Not animated")
                        Spacer(Modifier.height(8.dp))
                        SharedTransitionNotAnimated()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MovableContentSharedTransition(modifier: Modifier = Modifier) {
    val animatedBox =
        remember {
            movableContentWithReceiverOf<SharedTransitionScope, AnimatedContentScope> { animatedContentScope ->
                val transition = rememberInfiniteTransition()
                val color by transition.animateColor(
                    initialValue = Color.Magenta,
                    targetValue = Color.Black,
                    animationSpec = infiniteRepeatable(
                        tween(durationMillis = 1500, easing = LinearEasing),
                        RepeatMode.Reverse,
                    ),
                )

                Box(
                    Modifier
                        .size(48.dp)
                        .sharedElement(
                            rememberSharedContentState(key = "animated_box"),
                            animatedContentScope,
                        )
                        .background(color)
                )
            }
        }

    var inLeft by remember { mutableStateOf(true) }

    SharedTransitionLayout {
        AnimatedContent(
            modifier = modifier.clickable { inLeft = !inLeft },
            targetState = inLeft
        ) { targetState ->
            if (targetState) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animatedBox(this@SharedTransitionLayout, this@AnimatedContent)
                }
            } else {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animatedBox(this@SharedTransitionLayout, this@AnimatedContent)
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransition(modifier: Modifier = Modifier) {
    var inLeft by remember { mutableStateOf(true) }

    SharedTransitionLayout(modifier) {
        AnimatedContent(
            modifier = Modifier.clickable { inLeft = !inLeft },
            targetState = inLeft
        ) { targetState ->
            if (targetState) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AnimatedBox(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@AnimatedContent,
                    )
                }
            } else {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AnimatedBox(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@AnimatedContent,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionNotAnimated(modifier: Modifier = Modifier) {
    var inLeft by remember { mutableStateOf(true) }

    SharedTransitionLayout(modifier) {
        AnimatedContent(
            modifier = Modifier.clickable { inLeft = !inLeft },
            targetState = inLeft
        ) { targetState ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (targetState) {
                    Box(
                        Modifier
                            .align(Alignment.CenterStart)
                            .size(BoxSize)
                            .sharedElement(
                                rememberSharedContentState("something"),
                                this@AnimatedContent
                            )
                            .background(Color.Yellow)
                    )
                } else {
                    Box(
                        Modifier
                            .align(Alignment.CenterEnd)
                            .size(BoxSize)
                            .sharedElement(
                                rememberSharedContentState("something"),
                                this@AnimatedContent
                            )
                            .background(Color.Yellow)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun AnimatedBox(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        val transition = rememberInfiniteTransition()
        val color by transition.animateColor(
            initialValue = Color.Green,
            targetValue = Color.Black,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1500, easing = LinearEasing),
                RepeatMode.Reverse,
            ),
        )

        Box(
            modifier
                .size(BoxSize)
                .sharedElement(
                    rememberSharedContentState(key = "animated_box"),
                    animatedContentScope,
                )
                .background(color)
        )
    }
}

private val BoxSize = 48.dp
