package com.yudiz.instagram

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudiz.composeui.data_provider.InstagramPostData
import com.yudiz.composeui.data_provider.instaPostList
//import com.yudiz.composeui.data_provider.InstagramPostData
import com.yudiz.instagram.BottomNavItem.Items.items

@Composable
fun InstaHomePage(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { InstaTopBar() },
        bottomBar = {
            InstaBottomBar()
        },
        backgroundColor = Color.Black
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        RoundedProfilePic(
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    items(items = (1..10).toList()) {
                        InstaStory(
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }

            items(items = instaPostList) {
                InstaPosts(data = it)
            }
        }
    }
}


@Preview
@Composable
fun InstaHomePagePreview() {
    InstaHomePage()
}

@Composable
fun InstaPosts(
    modifier: Modifier = Modifier,
    data: InstagramPostData
) {
    var isLiked by remember(0) {
        mutableStateOf(false)
    }
    var isBookmarked by remember(0) {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedProfilePic(
                    profilePic = R.drawable.sundar_pichai_profile,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = data.author,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 0.dp),
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "Menu",
                tint = Color.White,
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            painter = painterResource(id = R.drawable.sundar_pichai_profile),
            contentDescription = "Post",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 6.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { isLiked = !isLiked },
            ) {
                Icon(
                    imageVector = if (isLiked) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_insta_comment),
                    contentDescription = "Comment",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            IconButton(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_insta_send),
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = { isBookmarked = !isBookmarked },
                ) {
                    Icon(
                        painter = if (isBookmarked) {
                            painterResource(id = R.drawable.ic_insta_bookmarked_fillded)
                        } else {
                            painterResource(id = R.drawable.ic_insta_bookmarked_outlined)
                        },
                        contentDescription = "Like",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedProfilePic(
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append("Liked by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Mark Zukerberg, Elon Musk")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("12,12,123 others")
                    }
                },
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        // if caption is there
        if (data.caption.isNotEmpty()) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(data.author)
                    }
                    append(" ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(data.caption)
                    }
                },
                color = Color.White,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = "View all 140000 Comments",
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = data.time,
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 0.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun InstaPostsPreview() {
    InstaPosts(
        data = InstagramPostData(
            id = 1,
            painter = R.drawable.sundar_pichai_profile,
            author = "Sundar Pichai",
            authorHasStory = false,
            authorImage = R.drawable.sundar_pichai_profile,
            caption = "Wear your failures as a badge of honor.",
            time = "15 Minutes Ago"
        )
    )
}

@Composable
fun InstaStory(
    modifier: Modifier = Modifier,
    @DrawableRes profilePic: Int = R.drawable.sundar_pichai_profile
) {
    Box(
        modifier = modifier
            .sizeIn(60.dp, 60.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFF9AA1B),
                        Color(0xFFF76E12),
                        Color(0xFFE95D3A),
                        Color(0xFFC20998),
                        Color(0xFFC6078B),
                        Color(0xFFF9AA1B),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .padding(1.5.dp)
                .clip(CircleShape),
            painter = painterResource(id = profilePic),
            contentDescription = "Story",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun InstaStoryPreview() {
    InstaStory(
        modifier = Modifier.size(60.dp),
        profilePic = R.drawable.sundar_pichai_profile
    )
}


@Composable
fun RoundedProfilePic(
    modifier: Modifier = Modifier,
    @DrawableRes profilePic: Int = R.drawable.mark_zuckerberg_profile
) {
    Image(
        modifier = modifier
            .clip(CircleShape),
        painter = painterResource(id = profilePic),
        contentDescription = "Story",
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun RoundedCornerProfilePicPreview() {
    RoundedProfilePic(modifier = Modifier.size(48.dp))
}


@Composable
fun InstaTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        elevation = 8.dp,
        backgroundColor = Color.Black
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_insta_text),
                    contentDescription = "Instagram",
                    colorFilter = ColorFilter.tint(color = Color.White),
                    modifier = Modifier.padding(6.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_insta_add_post),
                        contentDescription = "Add Post",
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_insta_messenger),
                        contentDescription = "Messages",
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun InstaTopBarPreview() {
    InstaTopBar()
}

@Composable
fun InstaBottomBar(
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        backgroundColor = Color.Black,
        elevation = 8.dp,
        modifier = modifier,
    ) {
        items.forEach {
            BottomNavigationItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = it.label
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun InstaBottomBarPreview() {
    InstaBottomBar()
}
