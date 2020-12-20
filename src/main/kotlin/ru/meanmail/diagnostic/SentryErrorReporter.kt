package ru.meanmail

import com.intellij.diagnostic.IdeaReportingEvent
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.diagnostic.IdeaLoggingEvent
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.ui.Messages
import com.intellij.util.Consumer
import io.sentry.Sentry
import io.sentry.SentryEvent
import io.sentry.SentryLevel
import io.sentry.protocol.Message
import java.awt.Component


object SentryClient {
    init {
        Sentry.init {
            it.dsn = "https://8d59d6d038bd4908bcdd0d169c6b5946@o428712.ingest.sentry.io/5554321";
        }
    }

    fun captureEvent(event: SentryEvent) {
        Sentry.captureEvent(event)
    }
}

class SentryErrorReporter : ErrorReportSubmitter() {

    override fun getReportActionText(): String {
        return "Submit to meanmail.dev"
    }

    override fun getPrivacyNoticeText(): String {
        return "By clicking on the '${reportActionText}' button you agree that " +
                "the following will be sent along with the error message: " +
                "IDE version, " +
                "IDE name, " +
                "plugin version, " +
                "date when the event occurred, " +
                "error log and other data that the IDE passes to the error handler.<br>" +
                "This data will only be used for debugging and bug fixing.<br>" +
                "<br>" +
                "<b>Attention!</b> Carefully study the transmitted data. Do not " +
                "click the '${reportActionText}' button if the error log or other data contains " +
                "personal information or other information that you do not want " +
                "to share."
    }

    override fun submit(
        events: Array<out IdeaLoggingEvent>,
        additionalInfo: String?,
        parentComponent: Component,
        consumer: Consumer<in SubmittedReportInfo>
    ): Boolean {
        val dataManager = DataManager.getInstance();
        val context = dataManager.getDataContext(parentComponent);
        val project = CommonDataKeys.PROJECT.getData(context);

        object : Task.Backgroundable(project, "Sending Error Report") {
            override fun run(indicator: ProgressIndicator) {
                for (ideaEvent in events) {
                    if (ideaEvent is IdeaReportingEvent) {
                        val event = SentryEvent(ideaEvent.data.throwable)
                        val message = Message()
                        message.message = ideaEvent.message
                        event.message = message
                        event.release = ideaEvent.plugin?.version
                        event.level = SentryLevel.ERROR
                        event.environment = ideaEvent.plugin?.name

                        val applicationInfo = ApplicationInfo.getInstance()
                        event.setTags(
                            mapOf(
                                "IDE version" to applicationInfo.fullVersion,
                                "IDE name" to applicationInfo.fullApplicationName,
                                "IDE company name" to applicationInfo.companyName,
                                "IDE company url" to applicationInfo.companyURL,
                                "IDE api version" to applicationInfo.apiVersion
                            )
                        )
                        event.setExtras(
                            mapOf(
                                "Additional info" to additionalInfo,
                                "Event date" to ideaEvent.data.date
                            )
                        )
                        SentryClient.captureEvent(event)
                    }
                }

                ApplicationManager.getApplication().invokeLater {
                    Messages.showInfoMessage(
                        parentComponent,
                        "Thank you for submitting your report!", "Error Report"
                    )
                    consumer.consume(
                        SubmittedReportInfo(
                            SubmittedReportInfo.SubmissionStatus.NEW_ISSUE
                        )
                    )
                }
            }
        }.queue()
        return true;
    }

}
