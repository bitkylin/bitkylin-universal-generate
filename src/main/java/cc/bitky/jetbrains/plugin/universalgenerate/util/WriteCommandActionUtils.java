package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.BitkylinException;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import lombok.extern.slf4j.Slf4j;

/**
 * @author limingliang
 */
@Slf4j
public class WriteCommandActionUtils {

    private WriteCommandActionUtils() {
    }

    public static void runWriteCommandAction(Project project, Runnable runnable) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                runnable.run();
            } catch (BitkylinException e) {
                log.warn("WriteCommandActionUtils.runWriteCommandAction BitkylinException", e);
                NotificationUtils.notifyError(project, e.getMessage());
            } catch (Exception e) {
                log.warn("WriteCommandActionUtils.runWriteCommandAction Exception", e);
                NotificationUtils.notifyError(project, "Exception: " + e.getMessage());
            }
        });
    }
}
