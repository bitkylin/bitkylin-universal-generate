package cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.IFileTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;

/**
 * @author bitkylin
 */
public class FileTypeEntrancePojoProcessor implements IFileTypeProcessor {

    @Override
    public void doWrite(WriteContext writeContext) {
        PsiClass[] innerClasses = writeContext.getPsiClass().getInnerClasses();
        if (innerClasses.length > 0) {
            for (PsiClass innerClass : innerClasses) {
                GenerateUtils.generateClassAnnotation(writeContext);
                // 类属性列表
                PsiField[] field = innerClass.getAllFields();
                for (PsiField psiField : field) {
                    GenerateUtils.generateFieldAnnotation(writeContext, psiField);
                }
            }
        }
        // 类属性列表
        PsiField[] field = writeContext.getPsiClass().getAllFields();
        for (PsiField psiField : field) {
            GenerateUtils.generateFieldAnnotation(writeContext, psiField);
        }
    }
}
