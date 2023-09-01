package cc.bitky.jetbrains.plugin.universalgenerate.action.action.base;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author bitkylin
 */
public abstract class AbstractUniversalGenerateAction extends AnAction {

    protected AbstractUniversalGenerateAction(String text) {
        super(text);
    }

    protected AbstractUniversalGenerateAction() {
        super();
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    protected abstract ActionEnum fetchActionEnum();
}
