package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.BitkylinException;
import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.LocalizationConfigFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * @author bitkylin
 */
public final class NotificationUtils {

    private NotificationUtils() {
    }

    private static void notifyError(Project project, String title, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("bitkylinErrorNotification")
                .createNotification(title, content, NotificationType.ERROR)
                .notify(project);
    }

    private static void notifyError(Project project, String content) {
        notifyError(project, "", content);
    }

    /**
     * checks都为true，不抛异常
     */
    public static void checks(Project project, ExceptionMsgEnum exceptionMsgEnum, Boolean... checks) {
        if (ArrayUtils.isEmpty(checks)) {
            String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
            notifyError(project,
                    LocalizationConfigFactory.name(LocalizationEnum.NOTIFICATION_TITLE),
                    localizationDesc
            );
            throw new BitkylinException(exceptionMsgEnum, localizationDesc);
        }
        Arrays.stream(checks).forEach(check -> {
            if (BooleanUtils.isNotTrue(check)) {
                String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
                notifyError(project,
                        LocalizationConfigFactory.name(LocalizationEnum.NOTIFICATION_TITLE),
                        localizationDesc
                );
                throw new BitkylinException(exceptionMsgEnum, localizationDesc);
            }
        });
    }

    /**
     * checks都为true，不抛异常
     */
    public static BitkylinException notifyAndNewException(Project project, ExceptionMsgEnum exceptionMsgEnum) {
        String localizationDesc = LocalizationConfigFactory.name(exceptionMsgEnum.getLocalizationEnum());
        notifyError(project,
                LocalizationConfigFactory.name(LocalizationEnum.NOTIFICATION_TITLE),
                localizationDesc
        );
        return new BitkylinException(exceptionMsgEnum, localizationDesc);
    }

}
