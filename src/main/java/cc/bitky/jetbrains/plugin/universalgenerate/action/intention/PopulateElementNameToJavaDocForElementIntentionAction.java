package cc.bitky.jetbrains.plugin.universalgenerate.action.intention;

import cc.bitky.jetbrains.plugin.universalgenerate.action.intention.base.AbstractUniversalGenerateIntentionAction;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.IntentionFamilyEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextIntentionBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * 通过当前元素名，在项目中匹配注释，并填充到JavaDoc中
 *
 * @author bitkylin
 */
@Slf4j
public class PopulateElementNameToJavaDocForElementIntentionAction extends AbstractUniversalGenerateIntentionAction {

    @Override
    protected boolean calcIsAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        WriteContext writeContext = WriteContextIntentionBuilder.create(project, editor, element);
        if (!writeContext.fetchSelected()) {
            return false;
        }
        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        return selectWrapper.getField() != null || selectWrapper.getMethod() != null;
    }

    @Override
    protected void doInvoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        WriteContext writeContext = WriteContextIntentionBuilder.create(project, editor, element);
        CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.POPULATE_ELEMENT_NAME_TO_JAVA_DOC).writeElement();
    }

    @Override
    protected IntentionFamilyEnum fetchIntentionFamilyEnum() {
        return IntentionFamilyEnum.SEARCH_ELEMENT_NAME_TO_JAVA_DOC;
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT;
    }
}