package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.ModifierAnnotationWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.google.common.base.Preconditions;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;

/**
 * @author bitkylin
 */
public final class GenerateUtils {

    private GenerateUtils() {
    }

    /**
     * 注解更新-原注解存在时不更新
     *
     * @param psiFileContext            文件上下文信息
     * @param modifierAnnotationWrapper 注解Wrapper
     * @param psiModifierListOwner      当前写入对象
     */
    public static void writeAnnotationOriginalPrimary(WriteContext.PsiFileContext psiFileContext,
                                                      ModifierAnnotationWrapper modifierAnnotationWrapper,
                                                      PsiModifierListOwner psiModifierListOwner) {
        String qualifiedName = modifierAnnotationWrapper.getAnnotationEnum().getQualifiedName();

        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);

        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(psiFileContext.getProject()).findClass(qualifiedName, GlobalSearchScope.allScope(psiFileContext.getProject()));
        if (waiteImportClass == null) {
            throw NotificationUtils.notifyAndNewException(psiFileContext.getProject(), ExceptionMsgEnum.CLASS_NOT_FOUND);
        }

        // 原注解存在时不更新
        String annotationTextOriginal = CommentParseUtils.parseAnnotationText(modifierAnnotationWrapper.getAnnotationEnum(), psiModifierListOwner);
        if (StringUtils.isNotBlank(annotationTextOriginal)) {
            return;
        }

        // 原注解删除
        PsiAnnotation existAnnotation = modifierList.findAnnotation(qualifiedName);
        if (existAnnotation != null) {
            existAnnotation.delete();
        }

        // 注解写入
        doWriteAnnotation(psiFileContext, modifierAnnotationWrapper, psiModifierListOwner);
    }

    /**
     * 注解强制更新
     *
     * @param psiFileContext            文件上下文信息
     * @param modifierAnnotationWrapper 注解Wrapper
     * @param psiModifierListOwner      当前写入对象
     */
    public static void writeAnnotationForce(WriteContext.PsiFileContext psiFileContext,
                                            ModifierAnnotationWrapper modifierAnnotationWrapper,
                                            PsiModifierListOwner psiModifierListOwner) {
        String qualifiedName = modifierAnnotationWrapper.getAnnotationEnum().getQualifiedName();

        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);

        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(psiFileContext.getProject()).findClass(qualifiedName, GlobalSearchScope.allScope(psiFileContext.getProject()));
        if (waiteImportClass == null) {
            throw NotificationUtils.notifyAndNewException(psiFileContext.getProject(), ExceptionMsgEnum.CLASS_NOT_FOUND);
        }

        // 原注解删除
        PsiAnnotation existAnnotation = modifierList.findAnnotation(qualifiedName);
        if (existAnnotation != null) {
            existAnnotation.delete();
        }

        // 注解写入
        doWriteAnnotation(psiFileContext, modifierAnnotationWrapper, psiModifierListOwner);
    }

    /**
     * 注解强制更新
     *
     * @param psiFileContext            文件上下文信息
     * @param modifierAnnotationWrapper 注解Wrapper
     * @param psiModifierListOwner      当前写入对象
     */
    public static void deleteAnnotation(WriteContext.PsiFileContext psiFileContext,
                                        ModifierAnnotationWrapper modifierAnnotationWrapper,
                                        PsiModifierListOwner psiModifierListOwner) {
        String qualifiedName = modifierAnnotationWrapper.getAnnotationEnum().getQualifiedName();

        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);

        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(psiFileContext.getProject()).findClass(qualifiedName, GlobalSearchScope.allScope(psiFileContext.getProject()));
        if (waiteImportClass == null) {
            throw NotificationUtils.notifyAndNewException(psiFileContext.getProject(), ExceptionMsgEnum.CLASS_NOT_FOUND);
        }

        // 原注解删除
        PsiAnnotation existAnnotation = modifierList.findAnnotation(qualifiedName);
        if (existAnnotation != null) {
            existAnnotation.delete();
        }
    }

    private static void doWriteAnnotation(WriteContext.PsiFileContext psiFileContext, ModifierAnnotationWrapper modifierAnnotationWrapper, PsiModifierListOwner psiModifierListOwner) {

        // 添加导入
        addImport(psiFileContext, modifierAnnotationWrapper.getAnnotationEnum());

        // 添加注解
        doAddAnnotation(psiFileContext, modifierAnnotationWrapper, psiModifierListOwner);
    }

    private static void doAddAnnotation(WriteContext.PsiFileContext psiFileContext,
                                        ModifierAnnotationWrapper modifierAnnotationWrapper,
                                        PsiModifierListOwner psiModifierListOwner) {
        PsiElementFactory elementFactory = psiFileContext.getElementFactory();
        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);

        String name = modifierAnnotationWrapper.getAnnotationEnum().getName();
        PsiAnnotation psiAnnotation = modifierList.addAnnotation(name);

        String annotationText = modifierAnnotationWrapper.getAnnotationText();
        PsiAnnotation psiAnnotationDeclare = elementFactory.createAnnotationFromText(annotationText, psiModifierListOwner);
        final PsiNameValuePair[] attributes = psiAnnotationDeclare.getParameterList().getAttributes();
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

    public static <T extends PsiModifierListOwner & PsiDocCommentOwner> void genJavaDoc(T psiElement, PsiElementFactory elementFactory) {
        PsiDocComment psiDocComment = psiElement.getDocComment();

        LinkedHashSet<String> commentSet = new LinkedHashSet<>();
        commentSet.addAll(CommentParseUtils.parseAnnotationTextList(ModifierAnnotationEnum.API, psiElement));
        commentSet.addAll(CommentParseUtils.parseAnnotationTextList(ModifierAnnotationEnum.API_OPERATION, psiElement));
        commentSet.addAll(CommentParseUtils.parseAnnotationTextList(ModifierAnnotationEnum.API_MODEL, psiElement));
        commentSet.addAll(CommentParseUtils.parseAnnotationTextList(ModifierAnnotationEnum.API_MODEL_PROPERTY, psiElement));
        commentSet.addAll(CommentParseUtils.parseCommentListFromJavaDoc(psiDocComment));


        PsiDocComment psiDocCommentNew = elementFactory.createDocCommentFromText("");
    }
}
