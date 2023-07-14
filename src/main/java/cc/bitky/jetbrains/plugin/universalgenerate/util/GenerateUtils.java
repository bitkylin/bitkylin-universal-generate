package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public static void generateSelection(WriteContext writeContext, boolean isController) {
        PsiClass psiClass = writeContext.getPsiClass();
        String selectionText = writeContext.getSelectionText();
        if (Objects.equals(selectionText, psiClass.getName())) {
            generateClassAnnotation(writeContext, isController);
        }
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod psiMethod : methods) {
            if (Objects.equals(selectionText, psiMethod.getName())) {
                generateMethodAnnotation(writeContext, psiMethod);
                return;
            }
        }

        WriteContext.SelectWrapper selectWrapper = writeContext.getSelectWrapper();
        if (selectWrapper.getField() != null) {
            generateFieldAnnotation(writeContext, selectWrapper.getField());
        }

//        PsiField[] field = psiClass.getAllFields();
//        for (PsiField psiField : field) {
//            if (Objects.equals(selectionText, psiField.getNameIdentifier().getText())) {
//                generateFieldAnnotation(writeContext, psiField);
//                return;
//            }
//        }
    }

    /**
     * 获取RequestMapping注解属性
     *
     * @param psiAnnotations 注解元素数组
     * @param attributeName  属性名
     * @return String 属性值
     */
    private static String getMappingAttribute(PsiAnnotation[] psiAnnotations, String attributeName) {
        for (PsiAnnotation psiAnnotation : psiAnnotations) {
            switch (Objects.requireNonNull(psiAnnotation.getQualifiedName())) {
                case REQUEST_MAPPING_ANNOTATION:
                    String attribute = getAttribute(psiAnnotation, attributeName, "");
                    if (Objects.equals("\"\"", attribute)) {
                        return "";
                    }
                    return attribute;
                case POST_MAPPING_ANNOTATION:
                    return "POST";
                case GET_MAPPING_ANNOTATION:
                    return "GET";
                case DELETE_MAPPING_ANNOTATION:
                    return "DELETE";
                case PATCH_MAPPING_ANNOTATION:
                    return "PATCH";
                case PUT_MAPPING_ANNOTATION:
                    return "PUT";
                default:
                    break;
            }
        }
        return "";
    }

    /**
     * 获取注解属性
     *
     * @param psiAnnotation 注解全路径
     * @param attributeName 注解属性名
     * @return 属性值
     */
    private static String getAttribute(PsiAnnotation psiAnnotation, String attributeName, String comment) {
        if (Objects.isNull(psiAnnotation)) {
            return "\"" + comment + "\"";
        }
        PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
        if (Objects.isNull(psiAnnotationMemberValue)) {
            return "\"" + comment + "\"";
        }
        return psiAnnotationMemberValue.getText();
    }

    /**
     * 生成类注解
     *
     * @param isController 是否为controller
     */
    public static void generateClassAnnotation(WriteContext writeContext, boolean isController) {
        PsiClass psiClass = writeContext.getPsiClass();
        PsiComment classComment = null;
        for (PsiElement tmpEle : psiClass.getChildren()) {
            if (tmpEle instanceof PsiComment) {
                classComment = (PsiComment) tmpEle;
                // 注释的内容
                String tmpText = classComment.getText();
                String commentDesc = RefCommentUtils.getCommentDesc(tmpText);
                String annotationFromText;
                String annotation;
                String qualifiedName;
                if (isController) {
                    annotation = "Api";
                    qualifiedName = "io.swagger.annotations.Api";
                    String fieldValue = getMappingAttribute(psiClass.getModifierList().getAnnotations(), MAPPING_VALUE);
                    annotationFromText = String.format("@%s(value = %s, tags = {\"%s\"})", annotation, fieldValue, commentDesc);
                } else {
                    annotation = "ApiModel";
                    qualifiedName = "io.swagger.annotations.ApiModel";
                    annotationFromText = String.format("@%s(description = \"%s\")", annotation, commentDesc);
                }
                doWrite(writeContext, annotation, qualifiedName, annotationFromText, psiClass);
            }
        }
        if (Objects.isNull(classComment)) {
            String annotationFromText;
            String annotation;
            String qualifiedName;
            if (isController) {
                annotation = "Api";
                qualifiedName = "io.swagger.annotations.Api";
                String fieldValue = getMappingAttribute(psiClass.getModifierList().getAnnotations(), MAPPING_VALUE);
                annotationFromText = String.format("@%s(value = %s)", annotation, fieldValue);
            } else {
                annotation = "ApiModel";
                qualifiedName = "io.swagger.annotations.ApiModel";
                annotationFromText = String.format("@%s", annotation);
            }
            doWrite(writeContext, annotation, qualifiedName, annotationFromText, psiClass);
        }
    }

    /**
     * 生成方法注解
     *
     * @param psiMethod 类方法元素
     */
    public static void generateMethodAnnotation(WriteContext writeContext, PsiMethod psiMethod) {
        String commentDesc = "";
        Map<String, String> methodParamCommentDesc = null;
        for (PsiElement tmpEle : psiMethod.getChildren()) {
            if (tmpEle instanceof PsiComment) {
                PsiComment classComment = (PsiComment) tmpEle;
                // 注释的内容
                String tmpText = classComment.getText();
                methodParamCommentDesc = RefCommentUtils.getCommentMethodParam(tmpText);
                commentDesc = RefCommentUtils.getCommentDesc(tmpText);
            }
        }

        PsiAnnotation[] psiAnnotations = psiMethod.getModifierList().getAnnotations();
        String methodValue = getMappingAttribute(psiAnnotations, MAPPING_METHOD);

        // 如果存在注解，获取注解原本的value和notes内容
        PsiAnnotation apiOperationExist = psiMethod.getModifierList().findAnnotation("io.swagger.annotations.ApiOperation");
        String apiOperationAttrValue = getAttribute(apiOperationExist, "value", commentDesc);
        String apiOperationAttrNotes = getAttribute(apiOperationExist, "notes", commentDesc);
        String apiOperationAnnotationText;
        if (StringUtils.isNotEmpty(methodValue)) {
            methodValue = methodValue.substring(methodValue.indexOf(".") + 1);
            apiOperationAnnotationText = String.format("@ApiOperation(value = %s, notes = %s, httpMethod = \"%s\")", apiOperationAttrValue, apiOperationAttrNotes, methodValue);
        } else {
            apiOperationAnnotationText = String.format("@ApiOperation(value = %s, notes = %s)", apiOperationAttrValue, apiOperationAttrNotes);
        }

        String apiImplicitParamsAnnotationText = null;
        PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
        List<String> apiImplicitParamList = new ArrayList<>(psiParameters.length);
        for (PsiParameter psiParameter : psiParameters) {
            PsiType psiType = psiParameter.getType();
            String dataType = RefCommentUtils.getDataType(psiType.getCanonicalText(), psiType);
            String paramType = "query";
            for (PsiAnnotation psiAnnotation : psiParameter.getModifierList().getAnnotations()) {
                if (StringUtils.isEmpty(psiAnnotation.getQualifiedName())) {
                    break;
                }
                switch (psiAnnotation.getQualifiedName()) {
                    case REQUEST_HEADER_TEXT:
                        paramType = "header";
                        break;
                    case REQUEST_PARAM_TEXT:
                        paramType = "query";
                        break;
                    case PATH_VARIABLE_TEXT:
                        paramType = "path";
                        break;
                    case REQUEST_BODY_TEXT:
                        paramType = "body";
                        break;
                    default:
                        break;
                }
            }
            if (Objects.equals(dataType, "file")) {
                paramType = "form";
            }
            String paramDesc = "";
            if (methodParamCommentDesc != null) {
                paramDesc = methodParamCommentDesc.get(psiParameter.getNameIdentifier().getText());
            }
            String apiImplicitParamText =
                    String.format("@ApiImplicitParam(paramType = \"%s\", dataType = \"%s\", name = \"%s\", value = \"%s\")",
                            paramType, dataType, psiParameter.getNameIdentifier().getText(), paramDesc == null ? "" : paramDesc);
//            String apiImplicitParamText =
//                    String.format("@ApiImplicitParam(paramType = \"%s\", dataType = \"%s\", name = \"%s\", value = \"\", required = %s)",paramType, dataType, psiParameter.getNameIdentifier().getText());
            apiImplicitParamList.add(apiImplicitParamText);
        }
        if (apiImplicitParamList.size() != 0) {
            apiImplicitParamsAnnotationText = apiImplicitParamList.stream().collect(Collectors.joining(",\n", "@ApiImplicitParams({\n", "\n})"));
        }

        doWrite(writeContext, "ApiOperation", "io.swagger.annotations.ApiOperation", apiOperationAnnotationText, psiMethod);
        if (StringUtils.isNotEmpty(apiImplicitParamsAnnotationText)) {
            doWrite(writeContext, "ApiImplicitParams", "io.swagger.annotations.ApiImplicitParams", apiImplicitParamsAnnotationText, psiMethod);
        }
        addImport(writeContext, "ApiImplicitParam");
    }

    /**
     * 生成属性注解
     *
     * @param psiField 类属性元素
     */
    public static void generateFieldAnnotation(WriteContext writeContext, PsiField psiField) {
        PsiComment classComment = null;
        for (PsiElement tmpEle : psiField.getChildren()) {
            if (tmpEle instanceof PsiComment) {
                classComment = (PsiComment) tmpEle;
                // 注释的内容
                String tmpText = classComment.getText();
                String commentDesc = RefCommentUtils.getCommentDesc(tmpText);
                String apiModelPropertyText = String.format("@ApiModelProperty(value=\"%s\")", commentDesc);
                doWrite(writeContext, "ApiModelProperty", "io.swagger.annotations.ApiModelProperty", apiModelPropertyText, psiField);
            }
        }
        if (Objects.isNull(classComment)) {
            doWrite(writeContext, "ApiModelProperty", "io.swagger.annotations.ApiModelProperty", "@ApiModelProperty(\"\")", psiField);
        }
    }

    /**
     * 写入到文件
     *
     * @param name                 注解名
     * @param qualifiedName        注解全包名
     * @param annotationText       生成注解文本
     * @param psiModifierListOwner 当前写入对象
     */
    private static void doWrite(WriteContext writeContext, String name, String qualifiedName, String annotationText, PsiModifierListOwner psiModifierListOwner) {
        PsiElementFactory elementFactory = writeContext.getElementFactory();

        PsiAnnotation psiAnnotationDeclare = elementFactory.createAnnotationFromText(annotationText, psiModifierListOwner);
        final PsiNameValuePair[] attributes = psiAnnotationDeclare.getParameterList().getAttributes();
        PsiAnnotation existAnnotation = psiModifierListOwner.getModifierList().findAnnotation(qualifiedName);
        if (existAnnotation != null) {
            existAnnotation.delete();
        }
        addImport(writeContext, name);
        PsiAnnotation psiAnnotation = psiModifierListOwner.getModifierList().addAnnotation(name);
        for (PsiNameValuePair pair : attributes) {
            psiAnnotation.setDeclaredAttributeValue(pair.getName(), pair.getValue());
        }
    }

    /**
     * 导入类依赖
     *
     * @param className 类名
     */
    private static void addImport(WriteContext writeContext, String className) {
        Project project = writeContext.getProject();
        PsiElementFactory elementFactory = writeContext.getElementFactory();
        PsiFile file = writeContext.getPsiFile();

        if (!(file instanceof PsiJavaFile)) {
            return;
        }
        final PsiJavaFile javaFile = (PsiJavaFile) file;
        // 获取所有导入的包
        final PsiImportList importList = javaFile.getImportList();
        if (importList == null) {
            return;
        }
        PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.allScope(project));
        // 待导入类有多个同名类或没有时 让用户自行处理
        if (psiClasses.length != 1) {
            return;
        }
        PsiClass waiteImportClass = psiClasses[0];
        for (PsiImportStatementBase is : importList.getAllImportStatements()) {
            String impQualifiedName = is.getImportReference().getQualifiedName();
            if (waiteImportClass.getQualifiedName().equals(impQualifiedName)) {
                // 已经导入
                return;
            }
        }
        importList.add(elementFactory.createImportStatement(waiteImportClass));
    }
}
