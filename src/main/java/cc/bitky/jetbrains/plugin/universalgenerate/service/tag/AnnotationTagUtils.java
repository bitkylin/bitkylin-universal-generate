package cc.bitky.jetbrains.plugin.universalgenerate.service.tag;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.ModifierAnnotationUtils;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils.writeAnnotationForce;

/**
 * @author bitkylin
 */
public final class AnnotationTagUtils {

    private AnnotationTagUtils() {
    }


    /**
     * 生成属性的Swagger注解
     *
     * @param psiFieldWrapper 类属性元素
     */
    public static void generateFieldTagAnnotation(WriteContext.PsiFileContext psiFileContext, PsiFieldWrapper psiFieldWrapper, int currentNum) {
        writeAnnotationForce(psiFileContext, ModifierAnnotationUtils.createWrapperTag(currentNum), psiFieldWrapper.getPsiField());
    }

    public static int calcNextGroupBeginNum(int beginNum, int step, int currentNum) {
        return ((currentNum - beginNum) / step + 1) * step + beginNum;
    }

    public static int calcCurrentGroupBeginNum(int beginNum, int step, int currentNum) {
        return ((currentNum - beginNum) / step) * step + beginNum;
    }


}
