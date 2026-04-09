package com.example.sebwave.features.map.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sebwave.features.dashboard.domain.entities.SemaphoreStatus
import com.example.sebwave.features.map.presentation.components.SemaphoreInfoCard
import com.example.sebwave.features.map.presentation.components.TappedPointCard
import com.example.sebwave.features.map.presentation.viewmodel.MapViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

private const val DEFAULT_LAT  = 19.4326
private const val DEFAULT_LON  = -99.1332
private const val DEFAULT_ZOOM = 12.0

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    var tappedPoint    by remember { mutableStateOf<GeoPoint?>(null) }
    var tappedMarkerRef by remember { mutableStateOf<Marker?>(null) }

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(
            context,
            context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = context.packageName
    }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(DEFAULT_ZOOM)
                    controller.setCenter(GeoPoint(DEFAULT_LAT, DEFAULT_LON))
                }
            },
            update = { mapView ->
                mapView.overlays.clear()

                val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                        tappedMarkerRef?.let { mapView.overlays.remove(it) }

                        val newMarker = Marker(mapView).apply {
                            position = p
                            title    = "Punto marcado"
                            snippet  = "${"%.5f".format(p.latitude)}, ${"%.5f".format(p.longitude)}"
                            icon     = tappedPointIcon(context)
                            setOnMarkerClickListener { _, _ -> true }
                        }
                        mapView.overlays.add(newMarker)
                        tappedMarkerRef = newMarker
                        tappedPoint     = p
                        mapView.invalidate()
                        return true
                    }

                    override fun longPressHelper(p: GeoPoint): Boolean = false
                })
                mapView.overlays.add(0, eventsOverlay)

                uiState.markers.forEach { semaphore ->
                    val marker = Marker(mapView).apply {
                        position = GeoPoint(semaphore.latitude, semaphore.longitude)
                        title    = semaphore.name
                        snippet  = semaphore.street
                        icon     = markerIconForStatus(context, semaphore.status)  // ← context
                        setOnMarkerClickListener { _, _ ->
                            viewModel.onMarkerSelected(semaphore)
                            true
                        }
                    }
                    mapView.overlays.add(marker)
                }

                mapView.invalidate()
            }
        )

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF4CAF50)
            )
        }

        uiState.errorMessage?.let { error ->
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp),
                action = { TextButton(onClick = { viewModel.dismissError() }) { Text("Cerrar") } }
            ) { Text(error) }
        }

        if (uiState.selectedMarker != null) {
            SemaphoreInfoCard(
                marker    = uiState.selectedMarker!!,
                onDismiss = { viewModel.onMarkerSelected(null) },
                modifier  = Modifier.align(Alignment.BottomCenter)
            )
        } else {
            tappedPoint?.let { point ->
                TappedPointCard(
                    point     = point,
                    onDismiss = { tappedPoint = null },
                    modifier  = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

private fun tappedPointIcon(context: Context): android.graphics.drawable.Drawable {
    val drawable = androidx.core.content.ContextCompat.getDrawable(
        context, org.osmdroid.library.R.drawable.marker_default
    )!!.mutate()
    androidx.core.graphics.drawable.DrawableCompat.setTint(
        drawable, android.graphics.Color.parseColor("#2196F3")
    )
    return drawable
}

private fun markerIconForStatus(
    context: Context,
    status: SemaphoreStatus
): android.graphics.drawable.Drawable {
    val color = when (status) {
        SemaphoreStatus.CONECTADO    -> android.graphics.Color.parseColor("#4CAF50")
        SemaphoreStatus.DESCONECTADO -> android.graphics.Color.parseColor("#9E9E9E")
        SemaphoreStatus.FALLIDO      -> android.graphics.Color.parseColor("#F44336")
    }
    val drawable = androidx.core.content.ContextCompat.getDrawable(
        context, org.osmdroid.library.R.drawable.marker_default
    )!!.mutate()
    androidx.core.graphics.drawable.DrawableCompat.setTint(drawable, color)
    return drawable
}