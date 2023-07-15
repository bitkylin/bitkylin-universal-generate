package cc.bitky.jetbrains.plugin.universalgenerate.service;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.FileTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl.SelectionProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.DecisionUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;
import com.google.common.base.Preconditions;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author bitkylin
 */
@Slf4j
public class BitkylinDemoAction extends AnAction {

    @SneakyThrows
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        Preconditions.checkNotNull(project);

        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        Preconditions.checkNotNull(editor);

        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        Preconditions.checkNotNull(psiFile);

        PsiClass psiClass = PsiTreeUtil.findChildOfAnyType(psiFile, PsiClass.class);
        Preconditions.checkNotNull(psiClass);

        // 通过光标偏移量获取当前psi元素
        PsiElement currentElement = psiFile.findElementAt(editor.getCaretModel().getOffset());

        WriteContext writeContext = new WriteContext();
        writeContext.setProject(project);
        writeContext.setPsiFile(psiFile);
        writeContext.setPsiClass(psiClass);
        writeContext.setElementFactory(JavaPsiFacade.getElementFactory(project));

        assemble(psiClass, currentElement, writeContext);


        WriteCommandAction.runWriteCommandAction(project, () -> {
            boolean selection = false;
//            if (StringUtils.isNotEmpty(selectionText)) {
//                selection = true;
//            }
            // 遍历当前对象的所有属性
            boolean isController = DecisionUtils.isController(psiClass);
            if (selection) {
                new SelectionProcessor().doWrite(writeContext, isController);
                return;
            }
            // 获取注释
            GenerateUtils.generateClassAnnotation(writeContext, isController);
            FileTypeProcessorFactory.decide(writeContext).doWrite(writeContext);
        });
    }

    private void assemble(PsiClass psiClass, PsiElement currentElement, WriteContext writeContext) {
        WriteContext.SelectWrapper selectWrapper = new WriteContext.SelectWrapper();
        selectWrapper.setCurrentElement(currentElement);
        writeContext.setSelectWrapper(selectWrapper);
        int depth = 0;
        query(depth, psiClass, currentElement, writeContext);


    }

    private void query(int depth, PsiClass psiClass, PsiElement currentElement, WriteContext writeContext) {

        Preconditions.checkArgument(depth < 5);

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (currentElement.getTextOffset() == psiClass.getTextOffset()) {
            selectWrapper.setClz(psiClass);
        }
        WriteContext.ClassWrapper classWrapper = WriteContext.createClassWrapper(psiClass);
        writeContext.addClassWrapper(classWrapper);

        for (PsiField field : classWrapper.getFieldList()) {
            if (currentElement.getTextOffset() == field.getTextOffset()) {
                selectWrapper.setField(field);
                break;
            }
        }
        for (PsiMethod method : classWrapper.getMethodList()) {
            if (currentElement.getTextOffset() == method.getTextOffset()) {
                selectWrapper.setMethod(method);
                break;
            }
        }

        if (CollectionUtils.isEmpty(classWrapper.getInnerClassList())) {
            return;
        }

        for (PsiClass innerClass : classWrapper.getInnerClassList()) {
            if (currentElement.getTextOffset() == innerClass.getTextOffset()) {
                selectWrapper.setClz(innerClass);
            }

            if (psiClass.isEnum()) {
                continue;
            }

            query(depth + 1, innerClass, currentElement, writeContext);
        }

    }

}
