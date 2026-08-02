package com.example.aero.ui.components.major

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.example.aero.data.AppDataStoreKeys
import com.example.aero.data.appDataStore
import kotlinx.coroutines.launch

@Composable
fun Toolbar(modifier: Modifier,showTargets: Boolean,onTargetsClick: () -> Unit,onConnectClick: () -> Unit ){

    var advanced by remember { mutableStateOf(false) }
    var target by remember { mutableStateOf(false) }
    var debug by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.appDataStore.data.collect { preferences ->
            debug = preferences[AppDataStoreKeys.DEBUG] ?: false
            advanced = preferences[AppDataStoreKeys.ADVANCED] ?: false
        }
    }
    LazyRow(
        modifier = Modifier
            .then(other = modifier),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        item {

            val scaledebug = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
                    .scale(scaledebug.value)
                    .clip(
                        if(!debug){
                            CircleShape
                        } else{
                            RoundedCornerShape(15.dp)
                        }
                    )
                    .background(
                            if(debug){
                                MaterialTheme.colorScheme.tertiaryFixed
                            } else{
                                MaterialTheme.colorScheme.surface
                            }
                            )
                    .clickable {
                        debug = !debug
                        scope.launch {
                            scaledebug.animateTo(1.05f)
                            scaledebug.animateTo(1f)
                            context.appDataStore.edit { preferences ->
                                preferences[AppDataStoreKeys.DEBUG] = debug
                            }
                        }

                    }



            ) {
                Text(
                    text = "Debug",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = (
                            if(!debug){
                                MaterialTheme.colorScheme.tertiaryFixed
                            } else{
                                MaterialTheme.colorScheme.surface
                            }
                            )
                )
            }
        }
        item {


            val scaleadvanced = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
                    .scale(scaleadvanced.value)
                    .clip(
                        if(!advanced){
                            CircleShape
                        } else{
                            RoundedCornerShape(15.dp)
                        }
                    )
                    .background(
                        if(advanced){
                            MaterialTheme.colorScheme.tertiaryFixed
                        } else{
                            MaterialTheme.colorScheme.surface
                        }
                    )
                    .clickable {
                        advanced = !advanced
                        scope.launch {
                            scaleadvanced.animateTo(1.05f)
                            scaleadvanced.animateTo(1f)
                            context.appDataStore.edit { preferences ->
                                preferences[AppDataStoreKeys.ADVANCED] = advanced
                            }
                        }
                    }


            ) {
                Text(
                    text = "Advanced",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = (
                            if(!advanced){
                                MaterialTheme.colorScheme.tertiaryFixed
                            } else{
                                MaterialTheme.colorScheme.surface
                            }
                            )
                )
            }
        }
        item {


            val scaleconnect = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
                    .scale(scaleconnect.value)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        scope.launch {
                            scaleconnect.animateTo(1.05f)
                            scaleconnect.animateTo(1f)
                        }
                        onConnectClick()
                    }


            ) {
                Text(
                    text = "Connect",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.tertiaryFixed
                )
            }
        }
        item {


            val scaleconnect = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp)
                    .scale(scaleconnect.value)
                    .clip(
                        if(!target){
                            CircleShape
                        } else{
                            RoundedCornerShape(15.dp)
                        }
                    )
                    .background(
                        if(target){
                            MaterialTheme.colorScheme.tertiaryFixed
                        } else{
                            MaterialTheme.colorScheme.surface
                        }
                    )
                    .clickable {
                        scope.launch {
                            target = !target
                            scaleconnect.animateTo(1.05f)
                            scaleconnect.animateTo(1f)
                            onTargetsClick()
                        }
                    }


            ) {
                Text(
                    text = "Show Targets",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = (
                            if(!target){
                                MaterialTheme.colorScheme.tertiaryFixed
                            } else{
                                MaterialTheme.colorScheme.surface
                            }
                            )
                )

            }
        }


    }

}