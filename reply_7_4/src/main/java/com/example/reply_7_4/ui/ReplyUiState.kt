package com.example.reply_7_4.ui

import com.example.reply_7_4.data.Email
import com.example.reply_7_4.data.MailboxType
import com.example.reply_7_4.data.local.LocalEmailsDataProvider

data class ReplyUiState(
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val currentMailbox: MailboxType = MailboxType.Inbox,
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isShowingHomepage: Boolean = true
) {
    val currentMailboxEmails: List<Email> by lazy { mailboxes[currentMailbox]!! }
}
