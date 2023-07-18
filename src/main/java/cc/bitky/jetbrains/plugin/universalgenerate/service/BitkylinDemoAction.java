package cc.bitky.jetbrains.plugin.universalgenerate.service;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.FileTypeProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl.SelectionProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiMethodWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.BitkylinPsiParseUtils;
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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        WriteContext.PsiFileContext psiFileContext = new WriteContext.PsiFileContext();
        writeContext.setPsiFileContext(psiFileContext);

        psiFileContext.setProject(project);
        psiFileContext.setPsiFile(psiFile);
        psiFileContext.setPsiClass(psiClass);
        psiFileContext.setElementFactory(JavaPsiFacade.getElementFactory(project));

        WriteContext.SelectWrapper selectWrapper = new WriteContext.SelectWrapper();
        selectWrapper.setCurrentElement(currentElement);
        writeContext.setSelectWrapper(selectWrapper);

        assembleUniversalClassInfo(0, psiClass, writeContext);

        WriteCommandAction.runWriteCommandAction(project, () -> {

//
//            doWrite(writeContext.getPsiFileContext(), ModifierAnnotationUtils.createWrapperApi("一级类目ID列表"), psiClass);
//            doWrite(writeContext.getPsiFileContext(), ModifierAnnotationUtils.createWrapperTag(101), psiClass);
//
//            if (selectWrapper != null) {
//                return;
//            }


            // 遍历当前对象的所有属性
            if (selectWrapper.isSelected()) {
                new SelectionProcessor().doWrite(writeContext);
                return;
            }
            // 获取注释
            GenerateUtils.generateClassSwaggerAnnotation(writeContext);
            FileTypeProcessorFactory.decide(writeContext).doWrite(writeContext);
        });
    }

    private void assembleUniversalClassInfo(int depth, PsiClass psiClass, WriteContext writeContext) {

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        PsiElement currentElement = selectWrapper.getCurrentElement();

        Preconditions.checkArgument(depth < 5);


        PsiClassWrapper psiClassWrapper = createPsiClassWrapper(psiClass);
        writeContext.addClassWrapper(psiClassWrapper);

        if (depth == 0) {
            writeContext.setFilePsiClassWrapper(psiClassWrapper);
        }

        if (currentElement.getTextOffset() == psiClass.getTextOffset()) {
            selectWrapper.setClz(psiClass);
            selectWrapper.setSelectedPsiClassWrapper(psiClassWrapper);
            selectWrapper.setSelected(true);
        }

        for (PsiFieldWrapper field : psiClassWrapper.getFieldList()) {
            PsiField psiField = field.getPsiField();
            if (currentElement.getTextOffset() == psiField.getTextOffset()) {
                selectWrapper.setField(psiField);
                selectWrapper.setSelectedPsiClassWrapper(psiClassWrapper);
                selectWrapper.setSelected(true);
                break;
            }
        }
        for (PsiMethodWrapper method : psiClassWrapper.getMethodList()) {
            PsiMethod psiMethod = method.getPsiMethod();
            if (currentElement.getTextOffset() == psiMethod.getTextOffset()) {
                selectWrapper.setMethod(psiMethod);
                selectWrapper.setSelectedPsiClassWrapper(psiClassWrapper);
                selectWrapper.setSelected(true);
                break;
            }
        }

        if (psiClass.isEnum()) {
            return;
        }

        if (depth >= 1) {
            return;
        }

        psiClassWrapper.setInnerClassList(Arrays.stream(psiClass.getAllInnerClasses()).map(this::createPsiClassWrapper).collect(Collectors.toList()));

        if (CollectionUtils.isEmpty(psiClassWrapper.getInnerClassList())) {
            return;
        }

        for (PsiClassWrapper innerClass : psiClassWrapper.getInnerClassList()) {
            assembleUniversalClassInfo(depth + 1, innerClass.getPsiClass(), writeContext);
        }

    }

    @NotNull
    private PsiClassWrapper createPsiClassWrapper(PsiClass psiClass) {
        PsiClassWrapper psiClassWrapper = new PsiClassWrapper();
        psiClassWrapper.setPsiClass(psiClass);
        psiClassWrapper.setFieldList(Stream.of(psiClass.getFields()).map(BitkylinPsiParseUtils::parsePsiField).toList());
        psiClassWrapper.setMethodList(Stream.of(psiClass.getMethods()).map(BitkylinPsiParseUtils::parsePsiMethod).toList());
        return psiClassWrapper;
    }

}
