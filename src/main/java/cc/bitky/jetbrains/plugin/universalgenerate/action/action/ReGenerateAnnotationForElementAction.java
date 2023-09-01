package cc.bitky.jetbrains.plugin.universalgenerate.action.action;

import cc.bitky.jetbrains.plugin.universalgenerate.action.action.base.AbstractUniversalGenerateAction;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.CommandCommandTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteCommand;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.WriteCommandActionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.builder.WriteContextActionBuilder;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 在当前元素中强制重新生成所有注解
 *
 * @author bitkylin
 */
@Slf4j
public class ReGenerateAnnotationForElementAction extends AbstractUniversalGenerateAction {

    public ReGenerateAnnotationForElementAction(String text) {
        super(text);
    }

    public ReGenerateAnnotationForElementAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextActionBuilder.create(anActionEvent);

        WriteCommandActionUtils.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.RE_GENERATE_WRITE_SWAGGER).writeElement();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.RE_GENERATE_WRITE_TAG).writeElement();
        });
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.RE_GENERATE_ANNOTATION_FOR_ELEMENT;
    }
}
