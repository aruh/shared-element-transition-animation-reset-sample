package com.example.sharedelementtransitionanimationresetsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        val gapSpacing = 96.dp
                        val textToExampleSpacing = 16.dp

                        Spacer(Modifier.height(24.dp))

                        Text("Tap the squares to trigger a transition.")

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "AnimatedContent",
                            usesMovableContent = true,
                            usesSharedTransitionLayout = true
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        MovableContentSharedTransition(Modifier.align(Alignment.CenterHorizontally))

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "AnimatedContent",
                            usesMovableContent = false,
                            usesSharedTransitionLayout = true
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        SharedTransition()

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "AnimatedContent",
                            usesMovableContent = true,
                            usesSharedTransitionLayout = false
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        MovableContentAnimatedContent()

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "No animation. Boxes being placed.",
                            usesMovableContent = true,
                            usesSharedTransitionLayout = false
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        MovableContentBox()

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "AnimatedVisibility",
                            usesMovableContent = true,
                            usesSharedTransitionLayout = false
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        MovableContentAnimatedVisibility()

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "AnimatedVisibility",
                            usesMovableContent = true,
                            usesSharedTransitionLayout = true
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        MovableContentAnimatedVisibilitySharedElement()

                        Spacer(Modifier.height(gapSpacing))

                        ExampleDescription(
                            animationKind = "callerManagedVisibility",
                            usesMovableContent = true,
                            usesSharedTransitionLayout = true
                        )
                        Spacer(Modifier.height(textToExampleSpacing))
                        MovableContentSelfControlledVisibilitySharedTransition()

                        Spacer(Modifier.height(gapSpacing))
                    }
                }
            }
        }
    }
}

@Composable
private fun MovableContentAnimatedContent(modifier: Modifier = Modifier) {
    val animatedBox =
        remember {
            movableContentOf {
                Box(
                    Modifier
                        .size(BoxSize)
                        .background(rememberInfiniteColorChangeTransition())
                )
            }
        }

    var inLeft by remember { mutableStateOf(true) }

    AnimatedContent(
        modifier = modifier.clickable { inLeft = !inLeft },
        targetState = inLeft
    ) { targetState ->
        if (targetState) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                animatedBox()
            }
        } else {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                animatedBox()
            }
        }
    }
}

@Composable
private fun MovableContentBox(modifier: Modifier = Modifier) {
    val animatedBox =
        remember {
            movableContentOf {
                Box(
                    Modifier
                        .size(BoxSize)
                        .background(rememberInfiniteColorChangeTransition())
                )
            }
        }

    var inLeft by remember { mutableStateOf(true) }

    Box(
        modifier = modifier.clickable { inLeft = !inLeft },
    ) {
        if (inLeft) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                animatedBox()
            }
        } else {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                animatedBox()
            }
        }
    }
}

@Composable
private fun MovableContentAnimatedVisibility(modifier: Modifier = Modifier) {
    val animatedBox =
        remember {
            movableContentOf {
                Box(
                    Modifier
                        .size(BoxSize)
                        .background(rememberInfiniteColorChangeTransition())
                )
            }
        }

    var inLeft by remember { mutableStateOf(true) }

    Box(
        modifier = modifier.clickable { inLeft = !inLeft },
    ) {
        if (inLeft) {
            AnimatedVisibility(visible = inLeft) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animatedBox()
                }
            }
        } else {
            AnimatedVisibility(visible = !inLeft) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animatedBox()
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MovableContentSelfControlledVisibilitySharedTransition(modifier: Modifier = Modifier) {
    val animatedBox =
        remember {
            movableContentWithReceiverOf<SharedTransitionScope, Boolean> { isVisible ->
                Box(
                    Modifier
                        .size(BoxSize)
                        .sharedElementWithCallerManagedVisibility(
                            rememberSharedContentState(key = "animated_box"),
                            isVisible,
                        )
                        .background(rememberInfiniteColorChangeTransition())
                )
            }
        }

    var inLeft by remember { mutableStateOf(true) }

    SharedTransitionLayout {
        Box(
            modifier = modifier.clickable { inLeft = !inLeft },
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                animatedBox(this@SharedTransitionLayout, inLeft)
            }

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                animatedBox(this@SharedTransitionLayout, !inLeft)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MovableContentAnimatedVisibilitySharedElement(modifier: Modifier = Modifier) {
    val animatedBox =
        remember {
            movableContentWithReceiverOf<SharedTransitionScope, AnimatedVisibilityScope> { animatedContentScope ->
                Box(
                    Modifier
                        .size(BoxSize)
                        .sharedElement(
                            rememberSharedContentState(key = "animated_box"),
                            animatedContentScope,
                        )
                        .background(rememberInfiniteColorChangeTransition())
                )
            }
        }

    var inLeft by remember { mutableStateOf(true) }

    SharedTransitionLayout {
        Box(
            modifier = modifier.clickable { inLeft = !inLeft },
        ) {
            AnimatedVisibility(visible = inLeft) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animatedBox(this@SharedTransitionLayout, this@AnimatedVisibility)
                }
            }
            AnimatedVisibility(visible = !inLeft) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animatedBox(this@SharedTransitionLayout, this@AnimatedVisibility)
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
                Box(
                    Modifier
                        .size(BoxSize)
                        .sharedElement(
                            rememberSharedContentState(key = "animated_box"),
                            animatedContentScope,
                        )
                        .background(rememberInfiniteColorChangeTransition())
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
                        animatedVisibilityScope = this@AnimatedContent,
                    )
                }
            } else {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AnimatedBox(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent,
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
    animatedVisibilityScope: AnimatedVisibilityScope,
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
                    animatedVisibilityScope,
                )
                .background(color)
        )
    }
}


@Composable
private fun ExampleDescription(
    animationKind: String,
    usesSharedTransitionLayout: Boolean,
    usesMovableContent: Boolean,
    other: String? = null,
) {
    fun booleanToYesNo(boolean: Boolean): String =
        if (boolean) "Yes" else "No"

    val boldStyle = SpanStyle(fontWeight = FontWeight.SemiBold)

    Text(
        buildAnnotatedString {
            withStyle(boldStyle) { append("Transition Animation: ") }
            append(animationKind)
            appendLine()

            withStyle(boldStyle) { append("Uses SharedTransitionLayout: ") }
            append(booleanToYesNo(usesSharedTransitionLayout))
            appendLine()

            withStyle(boldStyle) { append("Uses movableContentOf: ") }
            append(booleanToYesNo(usesMovableContent))

            other?.let {
                appendLine()
                append(other)
            }
        }
    )
}

@Composable
fun rememberInfiniteColorChangeTransition(
    initialColor: Color = Color.Green,
    targetColor: Color = Color.Black,
): Color =
    rememberInfiniteTransition(label = "Infinite color transition")
        .animateColor(
            initialValue = initialColor,
            targetValue = targetColor,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1500, easing = LinearEasing),
                RepeatMode.Reverse,
            ),
            label = "Infinite color animation",
        )
        .value

private val BoxSize = 48.dp
