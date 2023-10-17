package cc.bitky.jetbrains.plugin.universalgenerate.action.intention.base;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.BitkylinException;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.ActionLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.IntentionFamilyEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;
import com.intellij.codeInsight.intention.BaseElementAtCaretIntentionAction;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author limingliang
 */
@Slf4j
public abstract class AbstractUniversalGenerateIntentionAction extends BaseElementAtCaretIntentionAction implements IntentionAction {

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        try {
            if (!GlobalSettingsStateHelper.getInstance().isIntentionActionEnabled()) {
                return false;
            }
            return calcIsAvailable(project, editor, element);
        } catch (ProcessCanceledException e) {
            log.info("IntentionAction isAvailable ProcessCanceledException", e);
            throw e;
        } catch (BitkylinException e) {
            log.error("IntentionAction isAvailable BitkylinException", e);
            NotificationUtils.notifyError(project, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("IntentionAction isAvailable error", e);
            NotificationUtils.notifyError(project, "IntentionAction isAvailable error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        try {
            doInvoke(project, editor, element);
        } catch (ProcessCanceledException e) {
            log.info("IntentionAction invoke ProcessCanceledException", e);
            throw e;
        } catch (BitkylinException e) {
            log.error("IntentionAction invoke BitkylinException", e);
            NotificationUtils.notifyError(project, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("IntentionAction invoke error", e);
            NotificationUtils.notifyError(project, "IntentionAction invoke BitkylinException: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        ActionLocalizationConfig actionLocalizationConfig = new ActionLocalizationConfig();
        return actionLocalizationConfig.fetchIntentionFamilyTitle(fetchIntentionFamilyEnum());
    }

    @Override
    public @IntentionName @NotNull String getText() {
        ActionLocalizationConfig actionLocalizationConfig = new ActionLocalizationConfig();
        return actionLocalizationConfig.fetchIntentionActionTitle(fetchActionEnum());
    }

    protected abstract boolean calcIsAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element);

    protected abstract void doInvoke(@NotNull Project project, Editor editor, @NotNull PsiElement element);

    protected abstract IntentionFamilyEnum fetchIntentionFamilyEnum();

    protected abstract ActionEnum fetchActionEnum();
}
