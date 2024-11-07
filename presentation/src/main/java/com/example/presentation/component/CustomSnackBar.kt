package com.example.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.theme.Error
import com.example.presentation.ui.theme.Primary_Blue_300
import com.example.presentation.ui.theme.Primary_Red_100
import com.example.presentation.ui.theme.Success
import com.example.presentation.ui.theme.Text_0
import com.example.presentation.ui.theme.Text_600
import org.json.JSONException
import org.json.JSONObject


@Composable
fun DefaultSnackBar(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState
) {
//	snackBarHostState.currentSnackbarData?.dismiss()

    val state = remember { snackBarHostState.currentSnackbarData }
    if (state != null) state.dismiss()

    SnackbarHost(
        hostState = snackBarHostState,
        snackbar = { data ->
            val jsonObject = try {
                JSONObject(data.visuals.message)
            } catch (e: JSONException) {
                null
            }
            val isError = try {
                jsonObject?.getBoolean("isError") ?: false
            } catch (e: JSONException) {
                false
            }
            val content = try {
                jsonObject?.getString("content") ?: ""
            } catch (e: JSONException) {
                ""
            }

            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .border(
                        width = 1.dp,
                        color = if (isError) Error else Text_600,
                        shape = RoundedCornerShape(6.dp)
                    )
            ) {
                Snackbar(
                    shape = RoundedCornerShape(6.dp),
                    content = {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)) {
                            VectorIcon(
                                modifier = Modifier
                                    .width(16.dp)
                                    .padding(top = 2.dp),
                                vector = if (isError) Icons.Default.Cancel else Icons.Default.CheckCircle,
                                tint = if (isError) Error else Success,
                                contentDescription = "Snackbar Icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = content,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = if (isError) Error else Text_0
                            )
                        }
                    },
                    containerColor = if (isError) Primary_Red_100 else Text_600,
                    action = {
                        data.visuals.actionLabel?.let { actionLabel ->
                            TextButton(onClick = {
                                data.performAction()
                            }) {
                                Text(
                                    text = actionLabel,
                                    color = if (isError) Error else Primary_Blue_300,
                                )
                            }
                        }
                    }
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}