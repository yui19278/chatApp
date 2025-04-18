package com.example.chatapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapplication.R
import com.example.chatapplication.entity.ChatApiEntity
import com.example.chatapplication.entity.ChatData
import com.example.chatapplication.entity.sampleUserData
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import java.time.LocalDateTime

@Composable
fun ChatScreen(
    viewModel: SharedViewModel,
    onBackClick: () -> Unit = {},
) {
    val groupId = viewModel.groupId.collectAsState()
    val chatDataList = viewModel.chatList.collectAsState()

//    val userOptions = sampleUserData.map { it.name }


    LaunchedEffect(Unit) {
        viewModel.fetchChatList()
    }

    Scaffold(
        topBar = {
            Row {
                Button(
                    onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                }
                Button(onClick = {
                    viewModel.fetchChatList()
                }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "")
                }
            }
        },
        bottomBar = {
            SendingTextField(
                viewModel,
                chatDataList.value
            )
        },
    ) { innerPadding ->
        ChatList(
            chatDataList = chatDataList.value.filter { x -> x.groupId == groupId.value }, // viewModelの場合groupId.valueに
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun SendingTextField(
    viewModel: SharedViewModel,
    chatDataList: ArrayList<ChatData>
) {
    var text by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    TextField(
        value = text,
        onValueChange = { newValue ->
            text = newValue.filter { x -> x != '\n' }
        },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    if (text.isNotEmpty()){
                        viewModel.postChat(
                            chat = ChatApiEntity(
                                id = chatDataList.last().id + 1,
                                groupId = 1,
                                senderId = 1,
                                senderName = "Alice",
                                dateTime = LocalDateTime.now().toString(),
                                message = text
                            )
                        )
                        focusManager.clearFocus()
                        text = ""
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sendicon256x),
                    modifier = Modifier.size(40.dp),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(0.5f.dp, Color.Black))
    )
}



@Preview
@Composable
fun PreviewChatScreen() {
    ChatApplicationTheme {
//        ChatScreen()
    }
}
