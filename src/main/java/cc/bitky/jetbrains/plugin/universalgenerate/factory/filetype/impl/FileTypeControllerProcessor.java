package cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.IFileTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;
import com.intellij.psi.PsiMethod;

/**
 * @author bitkylin
 */
public class FileTypeControllerProcessor implements IFileTypeProcessor {

    @Override
    public void doWrite(WriteContext writeContext) {
        // 类方法列表
        PsiMethod[] methods = writeContext.getPsiClass().getMethods();
        for (PsiMethod psiMethod : methods) {
            GenerateUtils.generateMethodAnnotation(writeContext, psiMethod);
        }
    }
}
