package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.ModifierAnnotationWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper.ClassRoleEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.base.Preconditions;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.Objects;

/**
 * @author bitkylin
 */
public final class GenerateUtils {

    private GenerateUtils() {
    }

    /**
     * 类是否为controller
     */
    public static void generateSelection(WriteContext writeContext) {
        PsiClass psiClass = writeContext.fetchFilePsiClass();
        String selectionText = null;//writeContext.getSelectionText();
        if (Objects.equals(selectionText, psiClass.getName())) {
            generateClassSwaggerAnnotation(writeContext);
        }
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod psiMethod : methods) {
            if (Objects.equals(selectionText, psiMethod.getName())) {
                return;
            }
        }

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (selectWrapper.getField() != null) {
            generateFieldSwaggerAnnotation(writeContext.getPsiFileContext(), selectWrapper.getField());
        }
    }

    /**
     * 生成类的Swagger注解
     */
    public static void generateClassSwaggerAnnotation(WriteContext writeContext, WriteContext.PsiFileContext psiFileContext, PsiClassWrapper psiClassWrapper) {
        PsiClass psiClass = psiClassWrapper.getPsiClass();
        ClassRoleEnum classRole = psiClassWrapper.getClassRole();
        String commentDesc = RefCommentUtils.beautifyCommentFromJavaDoc(psiClass.getDocComment());

        if (ClassRoleEnum.CONTROLLER == classRole) {
            doWrite(psiFileContext, ModifierAnnotationUtils.createWrapperApi(commentDesc), psiClass);
        } else if (ClassRoleEnum.POJO == classRole) {
            doWrite(psiFileContext, ModifierAnnotationUtils.createWrapperApiModel(commentDesc), psiClass);
        } else {
            NotificationUtils.checks(psiFileContext.getProject(), ExceptionMsgEnum.CLASS_ROLE_UNSUPPORTED);
        }
    }

    /**
     * 生成属性的Swagger注解
     *
     * @param psiField 类属性元素
     */
    public static void generateFieldSwaggerAnnotation(WriteContext.PsiFileContext psiFileContext, PsiField psiField) {
        String commentDesc = RefCommentUtils.beautifyCommentFromJavaDoc(psiField.getDocComment());
        doWrite(psiFileContext, ModifierAnnotationUtils.createWrapperApiModelProperty(commentDesc), psiField);
    }

    /**
     * 写入到文件
     *
     * @param psiFileContext            文件上下文信息
     * @param modifierAnnotationWrapper 注解Wrapper
     * @param psiModifierListOwner      当前写入对象
     */
    public static void doWrite(WriteContext.PsiFileContext psiFileContext,
                               ModifierAnnotationWrapper modifierAnnotationWrapper,
                               PsiModifierListOwner psiModifierListOwner) {
        String name = modifierAnnotationWrapper.getAnnotationEnum().getName();
        String qualifiedName = modifierAnnotationWrapper.getAnnotationEnum().getQualifiedName();
        String annotationText = modifierAnnotationWrapper.getAnnotationText();

        PsiElementFactory elementFactory = psiFileContext.getElementFactory();

        PsiAnnotation psiAnnotationDeclare = elementFactory.createAnnotationFromText(annotationText, psiModifierListOwner);
        final PsiNameValuePair[] attributes = psiAnnotationDeclare.getParameterList().getAttributes();
        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);

        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(psiFileContext.getProject()).findClass(qualifiedName, GlobalSearchScope.allScope(psiFileContext.getProject()));
        if (waiteImportClass == null) {
            NotificationUtils.checks(psiFileContext.getProject(), ExceptionMsgEnum.CLASS_NOT_FOUND);
        }
        PsiAnnotation existAnnotation = modifierList.findAnnotation(qualifiedName);
        if (existAnnotation != null) {
            existAnnotation.delete();
        }
        addImport(psiFileContext, modifierAnnotationWrapper.getAnnotationEnum());
        PsiAnnotation psiAnnotation = modifierList.addAnnotation(name);
        for (PsiNameValuePair pair : attributes) {
            psiAnnotation.setDeclaredAttributeValue(pair.getName(), pair.getValue());
        }
    }

    /**
     * 导入类依赖
     */
    private static void addImport(WriteContext.PsiFileContext writeContext, ModifierAnnotationEnum annotationEnum) {
        Project project = writeContext.getProject();
        PsiElementFactory elementFactory = writeContext.getElementFactory();
        PsiFile psiFile = writeContext.getPsiFile();

        if (!(psiFile instanceof PsiJavaFile psiJavaFile)) {
            return;
        }
        // 获取所有导入的包
        final PsiImportList importList = psiJavaFile.getImportList();
        if (importList == null) {
            return;
        }
        // 已经导入，直接返回
        PsiImportStatement psiImportStatement = importList.findSingleClassImportStatement(annotationEnum.getQualifiedName());
        if (psiImportStatement != null) {
            return;
        }
        // 先删掉已导入的同名类
        PsiImportStatementBase psiImportStatementBase = importList.findSingleImportStatement(annotationEnum.getName());
        if (psiImportStatementBase != null) {
            psiImportStatementBase.delete();
        }
        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(project).findClass(annotationEnum.getQualifiedName(), GlobalSearchScope.allScope(project));
        if (waiteImportClass == null) {
            return;
        }
        // 添加导入
        importList.add(elementFactory.createImportStatement(waiteImportClass));
    }
}
