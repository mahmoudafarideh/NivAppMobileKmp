package ir.niv.app.ui.support.submit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.theme.button.NivButton
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SubmitTicketDialog(
    state: SubmitTicketUiModel,
    onCategoryClick: (Long) -> Unit,
    onSubjectChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    messageError: String?,
    subjectError: String?
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.categories.forEach {
                FilterChip(
                    selected = it.selected,
                    onClick = {
                        onCategoryClick(it.id)
                    },
                    label = {
                        Text(text = it.name)
                    }
                )
            }
        }
        OutlinedTextField(
            value = state.subject,
            onValueChange = onSubjectChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            placeholder = {
                Text(text = "عنوان پیام")
            },
            shape = NivTheme.shapes.small,
            isError = subjectError != null,
            supportingText = {
                AnimatedVisibility(subjectError != null) {
                    Text(text = subjectError.orEmpty())
                }
            },
        )
        OutlinedTextField(
            value = state.message,
            onValueChange = onMessageChange,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(16.dp),
            placeholder = {
                Text(text = "متن پیام")
            },
            shape = NivTheme.shapes.small,
            isError = messageError != null,
            supportingText = {
                AnimatedVisibility(messageError != null) {
                    Text(text = messageError.orEmpty())
                }
            },
        )

        NivButton(
            label = "ثبت پیام پشتیبانی",
            onClick = onSubmitClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(bottom = 12.dp),
            state = state.buttonState,
        )
    }

}

@Preview
@Composable
private fun SubmitTicketDialogPreview() {
    NivThemePreview {
        SubmitTicketDialog(
            state = SubmitTicketUiModel(
                categories = persistentListOf(
                    SubmitTicketUiModel.CategoryUiModel(1, "دسته اول", false),
                    SubmitTicketUiModel.CategoryUiModel(2, "دسته دوم", true),
                )
            ),
            onCategoryClick = {},
            onSubjectChange = {},
            onMessageChange = {},
            onSubmitClick = {},
            messageError = null,
            subjectError = null
        )
    }
}