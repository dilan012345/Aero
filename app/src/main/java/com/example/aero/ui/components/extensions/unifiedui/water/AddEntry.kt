package com.example.aero.ui.components.extensions.unifiedui.water

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WaterAdjustButtons(
    modifier: Modifier,
    onAdd: () -> Unit,
    onManual: () -> Unit,
    onSubtract: () -> Unit
) {
    ButtonGroup(
        overflowIndicator = { menuState ->
            ButtonGroupDefaults.OverflowIndicator(menuState)
        },
        expandedRatio = 1f,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier

    ) {

        customItem(
            menuContent = {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Add, contentDescription = null)
                    },
                    text = { Text("Add") },
                    onClick = onAdd
                )
            },
            buttonGroupContent = {
                ToggleButton(
                    checked = false,
                    onCheckedChange = { onAdd() },
                    shapes = ButtonGroupDefaults.connectedLeadingButtonShapes(),
                    colors = ToggleButtonDefaults.toggleButtonColors(
                        containerColor = Color(0xFF598FB9).copy(alpha = 0.15f),
                        contentColor = Color(0xFF598FB9),
                        checkedContainerColor = Color(0xFF598FB9),
                        checkedContentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        )

        customItem(
            menuContent = {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    },
                    text = { Text("Manual") },
                    onClick = onManual
                )
            },
            buttonGroupContent = {
                ToggleButton(
                    checked = false,
                    onCheckedChange = { onManual() },
                    shapes = ButtonGroupDefaults.connectedMiddleButtonShapes(),
                    colors = ToggleButtonDefaults.toggleButtonColors(
                        containerColor = Color(0xFF598FB9),
                        contentColor = Color(0xFF598FB9),
                        checkedContainerColor = Color(0xFF598FB9),
                        checkedContentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Manual",
                        tint = Color(0xFF202934)
                    )
                }
            }
        )

        customItem(
            menuContent = {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Remove, contentDescription = null)
                    },
                    text = { Text("Subtract") },
                    onClick = onSubtract
                )
            },
            buttonGroupContent = {
                ToggleButton(
                    checked = false,
                    onCheckedChange = { onSubtract() },
                    shapes = ButtonGroupDefaults.connectedTrailingButtonShapes(),
                    colors = ToggleButtonDefaults.toggleButtonColors(
                        containerColor = Color(0xFF598FB9).copy(alpha = 0.15f),
                        contentColor = Color(0xFF598FB9),
                        checkedContainerColor = Color(0xFF598FB9),
                        checkedContentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Subtract"
                    )
                }
            }
        )
    }
}