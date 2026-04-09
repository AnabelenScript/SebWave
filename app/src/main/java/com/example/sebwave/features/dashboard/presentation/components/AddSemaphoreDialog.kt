package com.example.sebwave.features.dashboard.presentation.components

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.sebwave.core.ui.theme.*
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

private const val DEFAULT_LAT = 19.4326
private const val DEFAULT_LON = -99.1332
private const val DEFAULT_ZOOM = 13.0

@Composable
fun AddSemaphoreDialog(
    onDismiss: () -> Unit,
    onAdd: (
        name: String,
        serial: String,
        density: String,
        greenTime: String,
        latitude: Double?,
        longitude: Double?
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var serial by remember { mutableStateOf("") }
    var density by remember { mutableStateOf("") }
    var greenTime by remember { mutableStateOf("") }
    var selectedPoint by remember { mutableStateOf<GeoPoint?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(
            context,
            context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = context.packageName
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header del dialog
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "NUEVO SEMÁFORO",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                fontStyle = FontStyle.Italic,
                                letterSpacing = (-1).sp,
                                color = primaryLight
                            )
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido scrollable
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Campo: Nombre de la intersección
                    SemaphoreTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nombre de la intersección",
                        placeholder = "Ej. Concha y Coral"
                    )

                    // Campo: Número de serie
                    SemaphoreTextField(
                        value = serial,
                        onValueChange = { serial = it },
                        label = "Número de serie",
                        placeholder = "Ej. 00000000"
                    )

                    // Campo: Descripción / estado inicial
                    Text(
                        text = "El dispositivo aún no ha sido conectado.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    // Fila: Densidad + Tiempo verde
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SemaphoreTextField(
                            value = density,
                            onValueChange = { density = it },
                            label = "Densidad (%)",
                            placeholder = "9999",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.weight(1f)
                        )
                        SemaphoreTextField(
                            value = greenTime,
                            onValueChange = { greenTime = it },
                            label = "Tiempo verde (s)",
                            placeholder = "9999",
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Etiqueta del mapa
                    Text(
                        text = "Selecciona ubicación en el mapa",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    // Mapa OSM embebido para seleccionar ubicación
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                primaryLight.copy(alpha = 0.3f),
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        var markerRef by remember { mutableStateOf<Marker?>(null) }

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
                                mapView.overlays.removeAll { it is MapEventsOverlay }

                                val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
                                    override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                                        markerRef?.let { mapView.overlays.remove(it) }

                                        val newMarker = Marker(mapView).apply {
                                            position = p
                                            title = "Ubicación seleccionada"
                                            snippet = "${"%.5f".format(p.latitude)}, ${"%.5f".format(p.longitude)}"
                                            icon = tappedPointIcon(context)
                                            setOnMarkerClickListener { _, _ -> true }
                                        }
                                        mapView.overlays.add(newMarker)
                                        markerRef = newMarker
                                        selectedPoint = p
                                        mapView.invalidate()
                                        return true
                                    }

                                    override fun longPressHelper(p: GeoPoint): Boolean = false
                                })
                                mapView.overlays.add(0, eventsOverlay)
                                mapView.invalidate()
                            }
                        )
                    }

                    // Info del punto seleccionado
                    selectedPoint?.let { point ->
                        Surface(
                            color = primaryContainerLight,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "📍 ${"%.5f".format(point.latitude)}, ${"%.5f".format(point.longitude)}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = primaryLight
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón "Agregar semáforo"
                Button(
                    onClick = {
                        onAdd(
                            name,
                            serial,
                            density,
                            greenTime,
                            selectedPoint?.latitude,
                            selectedPoint?.longitude
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                    enabled = name.isNotBlank() && serial.isNotBlank()
                ) {
                    Text(
                        text = "Agregar semáforo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SemaphoreTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = Color.LightGray)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryLight,
                unfocusedBorderColor = Color.LightGray,
                cursorColor = primaryLight
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

private fun tappedPointIcon(context: Context): android.graphics.drawable.Drawable {
    val drawable = androidx.core.content.ContextCompat.getDrawable(
        context, org.osmdroid.library.R.drawable.marker_default
    )!!.mutate()
    androidx.core.graphics.drawable.DrawableCompat.setTint(
        drawable, android.graphics.Color.parseColor("#1A7A2E")
    )
    return drawable
}
