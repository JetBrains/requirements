package ru.meanmail.notification

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project


object Notifier {
    private const val GROUP_ID = "Requirements"

    private fun notify(
        project: Project?, title: String,
        content: String, type: NotificationType
    ) {
        Notification(
            GROUP_ID, title, content, type
        ).notify(project)
    }

    fun notifyError(project: Project?, title: String, content: String) {
        notify(project, title, content, NotificationType.ERROR)
    }

    fun notifyInformation(project: Project?, title: String, content: String) {
        notify(project, title, content, NotificationType.INFORMATION)
    }
}
