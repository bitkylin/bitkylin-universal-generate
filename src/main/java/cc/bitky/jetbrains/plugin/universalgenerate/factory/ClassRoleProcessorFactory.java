package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.classrole.ClassRoleClassControllerProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.classrole.ClassRoleClassPojoProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.classrole.IClassRoleClassProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.NotificationUtils;

/**
 * @author bitkylin
 */
public final class ClassRoleProcessorFactory {

    private ClassRoleProcessorFactory() {
    }

    public static IClassRoleClassProcessor decideClassProcess(PsiClassWrapper psiClassWrapper, WriteContext.PsiFileContext psiFileContext) {
        switch (psiClassWrapper.getClassRole()) {
            case CONTROLLER -> {
                return new ClassRoleClassControllerProcessor(psiClassWrapper, psiFileContext);
            }
            case POJO -> {
                return new ClassRoleClassPojoProcessor(psiClassWrapper, psiFileContext);
            }
            default ->
                    throw NotificationUtils.notifyAndNewException(psiClassWrapper.getPsiClass().getProject(), ExceptionMsgEnum.CLASS_ROLE_UNSUPPORTED);
        }
    }
}
