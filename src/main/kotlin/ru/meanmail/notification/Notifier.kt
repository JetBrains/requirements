package ru.meanmail.notification

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project


object Notifier {
    private const val GROUP_ID = "Requirements"

    private fun notify(
        project: Project?, title: String,
        content: String, type: NotificationType
    ) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(GROUP_ID)
            .createNotification(title, content, type)
            .notify(project)
    }

    fun notifyError(project: Project?, title: String, content: String) {
        notify(project, title, content, NotificationType.ERROR)
    }

    fun notifyInformation(project: Project?, title: String, content: String) {
        notify(project, title, content, NotificationType.INFORMATION)
    }
}
