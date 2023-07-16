package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class WriteContext {

    private Project project;

    private PsiFile psiFile;

    private PsiClass psiClass;

    private PsiElementFactory elementFactory;


    private SelectWrapper selectWrapper;

    private List<ClassWrapper> clzList;

    @Data
    public static class SelectWrapper {

        private boolean selected;

        private PsiElement currentElement;

        private PsiClass clz;

        private PsiField field;

        private PsiMethod method;

        /**
         * 被手动选中的类
         */
        private ClassWrapper selectedClassWrapper;

        public boolean selectedClassIsController() {
            if (selectedClassWrapper == null) {
                return false;
            }
            return selectedClassWrapper.isController();
        }

    }

    @Data
    public static class ClassWrapper {

        private boolean controller;

        private PsiClass clz;

        private List<PsiField> fieldList;

        private List<PsiMethod> methodList;

        private List<PsiClass> innerClassList;

    }

    public void addClassWrapper(ClassWrapper classWrapper) {
        if (clzList == null) {
            clzList = Lists.newArrayList(classWrapper);
        } else {
            clzList.add(classWrapper);
        }
    }

    public static ClassWrapper createClassWrapper(PsiClass clz) {
        ClassWrapper wrapper = new ClassWrapper();
        wrapper.setClz(clz);
        wrapper.setFieldList(List.of(clz.getFields()));
        wrapper.setMethodList(List.of(clz.getMethods()));
        return wrapper;
    }

}
