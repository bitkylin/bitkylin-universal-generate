package cc.bitky.jetbrains.plugin.universalgenerate.action.intention;

import cc.bitky.jetbrains.plugin.universalgenerate.action.intention.base.AbstractUniversalGenerateIntentionAction;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.IntentionFamilyEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.SelectWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.DecisionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextIntentionBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * 在当前元素中强制重新生成所有注解
 *
 * @author bitkylin
 */
@Slf4j
public class ReGenerateAnnotationForElementIntentionAction extends AbstractUniversalGenerateIntentionAction {

    @Override
    public boolean calcIsAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (!GlobalSettingsStateHelper.getInstance().isIntentionReGenerateShowed()) {
            return false;
        }
        SelectWrapper selectWrapper = DecisionUtils.parseSelectWrapper(project, element);
        return selectWrapper.isSelected();
    }

    @Override
    public void doInvoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        WriteContext writeContext = WriteContextIntentionBuilder.create(project, editor, element);
        if (!writeContext.fetchSelected()) {
            log.warn("未选择任何元素 : {}", getClass().getName());
            return;
        }
        CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.RE_GENERATE_WRITE_SWAGGER).writeElement();
        CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.RE_GENERATE_WRITE_TAG).writeElement();
    }

    @Override
    protected IntentionFamilyEnum fetchIntentionFamilyEnum() {
        return IntentionFamilyEnum.GENERATE_ANNOTATION;
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.RE_GENERATE_ANNOTATION_FOR_ELEMENT;
    }

}
