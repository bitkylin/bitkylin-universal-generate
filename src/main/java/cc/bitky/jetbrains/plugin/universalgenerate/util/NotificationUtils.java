package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.BitkylinException;
import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
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
            notifyError(project, "执行发生异常", exceptionMsgEnum.getShowMsg());
            throw new BitkylinException(exceptionMsgEnum);
        }
        Arrays.stream(checks).forEach(check -> {
            if (BooleanUtils.isNotTrue(check)) {
                notifyError(project, "执行发生异常", exceptionMsgEnum.getShowMsg());
                throw new BitkylinException(exceptionMsgEnum);
            }
        });
    }

    /**
     * checks都为true，不抛异常
     */
    public static BitkylinException notifyAndNewException(Project project, ExceptionMsgEnum exceptionMsgEnum) {
        notifyError(project, "执行发生异常", exceptionMsgEnum.getShowMsg());
        return new BitkylinException(exceptionMsgEnum);
    }

}
