package com.example.chatapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapplication.entity.groupDataSample
import com.example.chatapplication.ui.theme.ChatApplicationTheme

@Composable
fun GroupScreen(
    onGroupClick: () -> Unit = {},
    viewModel: SharedViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.fetchGroupList()
    }

    val groupDataList = viewModel.groupList.collectAsState()
    GroupList(
        groupDataList = groupDataList.value,
        onGroupClick = { groupId ->
            viewModel.setGroupId(groupId)
            onGroupClick()
        }
    )
}

@Preview
@Composable
fun PreviewGroupScreen() {
    ChatApplicationTheme {
//        GroupScreen()
    }
}
