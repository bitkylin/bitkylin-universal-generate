package cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.ClassRoleProcessorFactory;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.ICommandScopeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiFieldWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;

/**
 * @author bitkylin
 */
public class CommandScopeFileProcessor implements ICommandScopeProcessor {

    private final WriteContext writeContext;

    public CommandScopeFileProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void process() {
        for (PsiClassWrapper psiClassWrapper : writeContext.getClzList()) {
            ClassRoleProcessorFactory.decideClassProcess(psiClassWrapper, writeContext.getPsiFileContext()).process();
            GenerateUtils.generateClassSwaggerAnnotation(writeContext.getPsiFileContext(), psiClassWrapper);

            if (psiClassWrapper.getClassRole() == PsiClassWrapper.ClassRoleEnum.POJO) {
                for (PsiFieldWrapper psiFieldWrapper : psiClassWrapper.getFieldList()) {
                    GenerateUtils.generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), psiFieldWrapper);
                }
            }
        }
    }
}
