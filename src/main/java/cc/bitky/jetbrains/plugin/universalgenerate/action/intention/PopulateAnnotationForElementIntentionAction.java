package cc.bitky.jetbrains.plugin.universalgenerate.action.intention;

import cc.bitky.jetbrains.plugin.universalgenerate.action.intention.base.AbstractUniversalGenerateIntentionAction;
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
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * 在当前元素中填充所有注解
 *
 * @author bitkylin
 */
@Slf4j
public class PopulateAnnotationForElementIntentionAction extends AbstractUniversalGenerateIntentionAction {

    @Override
    protected boolean calcIsAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        SelectWrapper selectWrapper = DecisionUtils.parseSelectWrapper(project, element);
        return selectWrapper.isSelected();
    }

    @Override
    protected void doInvoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (!calcIsAvailable(project, editor, element)) {
            return;
        }
        WriteContext writeContext = WriteContextIntentionBuilder.create(project, editor, element);
        CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.POPULATE_WRITE_SWAGGER).writeElement();
        CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.POPULATE_WRITE_TAG).writeElement();
    }

    @Override
    protected IntentionFamilyEnum fetchIntentionFamilyEnum() {
        return IntentionFamilyEnum.GENERATE_ANNOTATION;
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.POPULATE_MISSING_ANNOTATION_FOR_ELEMENT;
    }
}
