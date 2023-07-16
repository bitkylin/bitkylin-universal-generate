package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.common.exception.ExceptionMsgEnum;
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

    private static final String MAPPING_VALUE = "value";
    private static final String MAPPING_METHOD = "method";
    private static final String REQUEST_MAPPING_ANNOTATION = "org.springframework.web.bind.annotation.RequestMapping";
    private static final String POST_MAPPING_ANNOTATION = "org.springframework.web.bind.annotation.PostMapping";
    private static final String GET_MAPPING_ANNOTATION = "org.springframework.web.bind.annotation.GetMapping";
    private static final String DELETE_MAPPING_ANNOTATION = "org.springframework.web.bind.annotation.DeleteMapping";
    private static final String PATCH_MAPPING_ANNOTATION = "org.springframework.web.bind.annotation.PatchMapping";
    private static final String PUT_MAPPING_ANNOTATION = "org.springframework.web.bind.annotation.PutMapping";
    private static final String REQUEST_PARAM_TEXT = "org.springframework.web.bind.annotation.RequestParam";
    private static final String REQUEST_HEADER_TEXT = "org.springframework.web.bind.annotation.RequestHeader";
    private static final String PATH_VARIABLE_TEXT = "org.springframework.web.bind.annotation.PathVariable";
    private static final String REQUEST_BODY_TEXT = "org.springframework.web.bind.annotation.RequestBody";


    /**
     * 类是否为controller
     */
    public static void generateSelection(WriteContext writeContext) {
        PsiClass psiClass = writeContext.getPsiClass();
        String selectionText = null;//writeContext.getSelectionText();
        if (Objects.equals(selectionText, psiClass.getName())) {
            generateClassAnnotation(writeContext);
        }
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod psiMethod : methods) {
            if (Objects.equals(selectionText, psiMethod.getName())) {
                return;
            }
        }

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (selectWrapper.getField() != null) {
            generateFieldAnnotation(writeContext, selectWrapper.getField());
        }
    }

    /**
     * 生成类注解
     */
    public static void generateClassAnnotation(WriteContext writeContext) {
        boolean isController = writeContext.getSelectWrapper().selectedClassIsController();
        PsiClass psiClass = writeContext.getPsiClass();
        String commentDesc = RefCommentUtils.beautifyCommentFromJavaDoc(psiClass.getDocComment());
        if (isController) {
            String annotation = "Api";
            String qualifiedName = "io.swagger.annotations.Api";
            String annotationFromText = String.format("@%s(tags = {\"%s\"})", annotation, commentDesc);

            doWrite(writeContext, annotation, qualifiedName, annotationFromText, psiClass);
        } else {
            String annotation = "ApiModel";
            String qualifiedName = "io.swagger.annotations.ApiModel";
            String annotationFromText = String.format("@%s(description = \"%s\")", annotation, commentDesc);
            doWrite(writeContext, annotation, qualifiedName, annotationFromText, psiClass);
        }
    }

    /**
     * 生成属性注解
     *
     * @param psiField 类属性元素
     */
    public static void generateFieldAnnotation(WriteContext writeContext, PsiField psiField) {
        String commentDesc = RefCommentUtils.beautifyCommentFromJavaDoc(psiField.getDocComment());
        String apiModelPropertyText = String.format("@ApiModelProperty(value=\"%s\")", commentDesc);
        doWrite(writeContext, "ApiModelProperty", "io.swagger.annotations.ApiModelProperty", apiModelPropertyText, psiField);
    }

    /**
     * 写入到文件
     *
     * @param name                 注解名
     * @param qualifiedName        注解全包名
     * @param annotationText       生成注解文本
     * @param psiModifierListOwner 当前写入对象
     */
    public static void doWrite(WriteContext writeContext, String name, String qualifiedName, String annotationText, PsiModifierListOwner psiModifierListOwner) {
        PsiElementFactory elementFactory = writeContext.getElementFactory();

        PsiAnnotation psiAnnotationDeclare = elementFactory.createAnnotationFromText(annotationText, psiModifierListOwner);
        final PsiNameValuePair[] attributes = psiAnnotationDeclare.getParameterList().getAttributes();
        PsiModifierList modifierList = psiModifierListOwner.getModifierList();
        Preconditions.checkNotNull(modifierList);

        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(writeContext.getProject()).findClass(qualifiedName, GlobalSearchScope.allScope(writeContext.getProject()));
        if (waiteImportClass == null) {
            NotificationUtils.checks(writeContext.getProject(), ExceptionMsgEnum.CLASS_NOT_FOUND);
        }
        PsiAnnotation existAnnotation = modifierList.findAnnotation(qualifiedName);
        if (existAnnotation != null) {
            existAnnotation.delete();
        }
        addImport(writeContext, name, qualifiedName);
        PsiAnnotation psiAnnotation = modifierList.addAnnotation(name);
        for (PsiNameValuePair pair : attributes) {
            psiAnnotation.setDeclaredAttributeValue(pair.getName(), pair.getValue());
        }
    }

    /**
     * 导入类依赖
     */
    private static void addImport(WriteContext writeContext, String name, String qualifiedName) {
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
        PsiImportStatement psiImportStatement = importList.findSingleClassImportStatement(qualifiedName);
        if (psiImportStatement != null) {
            return;
        }
        // 先删掉已导入的同名类
        PsiImportStatementBase psiImportStatementBase = importList.findSingleImportStatement(name);
        if (psiImportStatementBase != null) {
            psiImportStatementBase.delete();
        }
        // 待导入类没有时 让用户自行处理
        PsiClass waiteImportClass = JavaPsiFacade.getInstance(project).findClass(qualifiedName, GlobalSearchScope.allScope(project));
        if (waiteImportClass == null) {
            return;
        }
        // 添加导入
        importList.add(elementFactory.createImportStatement(waiteImportClass));
    }
}
