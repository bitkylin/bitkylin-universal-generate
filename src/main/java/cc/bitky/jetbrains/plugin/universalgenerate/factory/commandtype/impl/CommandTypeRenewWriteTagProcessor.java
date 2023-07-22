package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ModifierAnnotationUtils;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;
import com.google.common.annotations.VisibleForTesting;
import com.intellij.psi.PsiField;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.doWriteAnnotationForce;

/**
 * Tag注解强制写处理器
 *
 * @author bitkylin
 */
public class CommandTypeRenewWriteTagProcessor implements ICommandTypeProcessor {

    private final WriteContext writeContext;

    /**
     * 初始值
     */
    private final int begin;

    /**
     * 步长
     */
    private final int step;

    public CommandTypeRenewWriteTagProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
        begin = 100;
        step = 100;
    }

    @Override
    public void writeFile() {
        int num = 100;
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            if (psiClassWrapper.getClassRole() == PsiClassWrapper.ClassRoleEnum.POJO) {
                for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                    generateFieldTagAnnotation(writeContext.getPsiFileContext(), psiFieldWrapper, num++);
                }
            }
            num = calcNextBeginNum(begin, step, num);
        }
    }

    @Override
    public void writeElement() {
        throw NotificationUtils.notifyAndNewException(writeContext.fetchProject(), ExceptionMsgEnum.COMMAND_SCOPE_UNSUPPORTED);
    }

    /**
     * 生成属性的Swagger注解
     *
     * @param psiFieldWrapper 类属性元素
     */
    private static void generateFieldTagAnnotation(WriteContext.PsiFileContext psiFileContext, PsiFieldWrapper psiFieldWrapper, int currentNum) {
        PsiField psiField = psiFieldWrapper.getPsiField();
        doWriteAnnotationForce(psiFileContext, ModifierAnnotationUtils.createWrapperTag(currentNum), psiField);
    }

    @VisibleForTesting
    static int calcNextBeginNum(int beginNum, int step, int currentNum) {
        return ((currentNum - beginNum) / step + 1) * step + beginNum;
    }

}
