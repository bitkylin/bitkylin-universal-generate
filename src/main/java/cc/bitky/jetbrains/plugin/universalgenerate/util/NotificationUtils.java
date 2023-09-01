package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * @author bitkylin
 */
public final class NotificationUtils {

    private NotificationUtils() {
    }

    public static void notifyError(Project project, String content) {
        notifyError(project, LocalizationConfigFactory.name(LocalizationEnum.NOTIFICATION_TITLE), content);
    }

    private static void notifyError(Project project, String title, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("bitkylinErrorNotification")
                .createNotification(title, content, NotificationType.ERROR)
                .notify(project);
    }

}
