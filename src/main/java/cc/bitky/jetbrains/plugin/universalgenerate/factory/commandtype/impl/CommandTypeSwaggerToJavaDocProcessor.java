package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.psi.PsiClass;
import com.intellij.psi.javadoc.PsiDocComment;

/**
 * 移除swagger注解，并转换为JavaDoc
 *
 * @author bitkylin
 */
public class CommandTypeSwaggerToJavaDocProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    public CommandTypeSwaggerToJavaDocProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void writeFile() {
        WriteContext.PsiFileContext psiFileContext = writeContext.getPsiFileContext();
        PsiDocComment psiDocCommentNew = psiFileContext.getElementFactory().createDocCommentFromText("/**\n" +
                " * 移除swagger注解，并转换为JavaDoc\n" +
                " *\n" +
                " * @author bitkylin\n" +
                " */");
        PsiClass psiClass = writeContext.fetchFilePsiClass();
        psiClass.addBefore(psiDocCommentNew, psiClass.getFirstChild());
    }

    @Override
    public void writeElement() {

    }


}
