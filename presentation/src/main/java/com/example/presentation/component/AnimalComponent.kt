package com.example.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.window.core.layout.WindowHeightSizeClass
//import androidx.window.core.layout.WindowWidthSizeClass
import com.example.domain.entity.Animal
import com.example.domain.entity.Result
import com.example.domain.entity.Status
import com.example.presentation.ui.theme.Diary_Green_400
import com.example.presentation.ui.theme.Primary_Red_500
import com.orhanobut.logger.Logger
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


@Composable
fun AnimalList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    itemListState: StateFlow<List<Animal>>,
    onLoadMore: (Boolean) -> Unit,
    itemClicked: (Int, Animal) -> Unit
) {
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val list by itemListState.collectAsStateWithLifecycle()

    LaunchedEffect(listState) {

        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index ->
                val totalCount = listState.layoutInfo.totalItemsCount
                val temp = totalCount - 20

                Logger.d("index: $index, totalCount: ${totalCount}")
                totalCount != 0 && index > temp
            }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                onLoadMore(false)
            }
    }
//    LaunchedEffect(listState) {
//        snapshotFlow {
//            listState.firstVisibleItemIndex
//        }.collect { visibleIndex ->
//
//            val totalCount =listState.layoutInfo.totalItemsCount
//            val temp = totalCount - 20
//            Logger.d("visibleIndex: $visibleIndex, totalCount: $totalCount")
//            if (visibleIndex in temp..temp + 2 && listState.lastScrolledForward && state.status != Status.LOADING) {
//                onLoadMore(false)
//            }
//
//        }
//    }

//        LazyColumn(
//            modifier = modifier
//                .fillMaxWidth(),
//            state = listState,
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//        ) {
//            item {
//                Spacer(modifier = Modifier.height(10.dp))
//            }
//            itemsIndexed(list) { index, item ->
//
//                AnimalItemCompact(
//                    index = index,
//                    item = item,
//                    onClicked = { i, animal -> itemClicked(i, animal) })
//            }
//            item {
//                Spacer(modifier = Modifier.height(10.dp))
//            }
//        }
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(vertical = 10.dp),
        columns =
//        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
            GridCells.Fixed(
            1
        )
//        else GridCells.Adaptive(200.dp)
    ,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(list) { index, item ->
//            if ((windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT))
                AnimalItemCompact(
                    index = index,
                    item = item,
                    onClicked = { i, animal -> itemClicked(i, animal) })
//            else
//                AnimalItemExpanded(
//                    index = index,
//                    item = item,
//                    onClicked = { i, animal -> itemClicked(i, animal) })
        }
    }
}


@Composable
fun AnimalItemCompact(index: Int, item: Animal, onClicked: (Int, Animal) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
    ) {
        Logger.d("${item.favorite}")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            GlideImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .layout { measurable, constraints ->
                        if (constraints.maxHeight == Constraints.Infinity) {
                            layout(0, 0) {}
                        } else {
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height) {
                                placeable.place(0, 0)
                            }
                        }
                    },
                imageModel = { item.filename }, // loading a network image using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                loading = { LoadingIcon() },
                failure = { PlaceHolderIcon() }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    text = "# ${item.desertionNo}"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    text = "Name : ${item.kindCd}"
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        text = "Age : ${item.age}"
                    )
                    VectorIcon(
                        modifier = Modifier.clickable {
                            onClicked(index, item)
                        },
                        vector = if (item.favorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        tint = Primary_Red_500
                    )
                }
            }
        }
    }
}


@Composable
fun AnimalItemMedium(index: Int, item: Animal, onClicked: (Int, Animal) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClicked(index, item)
            }
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            GlideImage(
                imageModel = { item.popfile }, // loading a network image using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                loading = { LoadingIcon() },
                failure = {
                    PlaceHolderIcon()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "# ${item.desertionNo}"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                text = "Name : ${item.kindCd}"
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                text = "Age : ${item.age}"
            )
        }
    }
}

@Composable
fun AnimalItemExpanded(index: Int, item: Animal, onClicked: (Int, Animal) -> Unit) {

    Box(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(10.dp))
            .clickable {
                onClicked(index, item)
            }
            .padding(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GlideImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                imageModel = { item.filename }, // loading a network image using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                loading = { LoadingIcon() },
                failure = { PlaceHolderIcon() }
            )
            Column(
                modifier = Modifier
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    text = "# ${item.desertionNo}"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    text = "Name : ${item.kindCd}"
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    text = "Age : ${item.age}"
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            VectorIcon(
                modifier = Modifier.clickable {
                    onClicked(index, item)
                },
                vector = if (item.favorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                tint = Primary_Red_500
            )
        }
    }
}