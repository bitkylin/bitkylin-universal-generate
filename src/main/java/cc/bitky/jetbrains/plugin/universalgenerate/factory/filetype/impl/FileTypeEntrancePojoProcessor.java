package cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.IFileTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;

import java.util.List;

/**
 * @author bitkylin
 */
public class FileTypeEntrancePojoProcessor implements IFileTypeProcessor {

    @Override
    public void doWrite(WriteContext writeContext) {
        List<PsiClassWrapper> innerClasses = writeContext.getClzList();
        if (innerClasses.size() > 0) {
            for (PsiClassWrapper innerClass : innerClasses) {
                GenerateUtils.generateClassSwaggerAnnotation(writeContext,writeContext.getPsiFileContext(), innerClass);
                // 类属性列表
                List<PsiFieldWrapper> fieldList = innerClass.getFieldList();
                for (PsiFieldWrapper psiField : fieldList) {
                    GenerateUtils.generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), psiField.getPsiField());
                }
            }
        }
        // 类属性列表
        PsiField[] field = writeContext.fetchFilePsiClass().getAllFields();
        for (PsiField psiField : field) {
            GenerateUtils.generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), psiField);
        }
    }
}
