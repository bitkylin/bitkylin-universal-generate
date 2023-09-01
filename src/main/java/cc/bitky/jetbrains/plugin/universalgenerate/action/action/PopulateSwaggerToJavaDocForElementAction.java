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
 * 将当前元素中的Swagger注解转换为JavaDoc注释，并删除Protostuff注解
 *
 * @author bitkylin
 */
@Slf4j
public class PopulateSwaggerToJavaDocForElementAction extends AbstractUniversalGenerateAction {

    public PopulateSwaggerToJavaDocForElementAction(String text) {
        super(text);
    }

    public PopulateSwaggerToJavaDocForElementAction() {
        super();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        WriteContext writeContext = WriteContextActionBuilder.create(anActionEvent);

        WriteCommandActionUtils.runWriteCommandAction(writeContext.fetchProject(), () -> {
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.POPULATE_SWAGGER_TO_JAVA_DOC).writeElement();
            CommandCommandTypeProcessorFactory.decide(writeContext, WriteCommand.Command.DELETE_ANNOTATION_TAG).writeElement();
        });
    }

    @Override
    protected ActionEnum fetchActionEnum() {
        return ActionEnum.POPULATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT;
    }
}
