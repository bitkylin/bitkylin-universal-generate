package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class WriteContext {

    private PsiClassWrapper filePsiClassWrapper;

    private WriteCommand writeCommand;

    private PsiFileContext psiFileContext;

    private SelectWrapper selectWrapper;

    private List<PsiClassWrapper> clzList;

    public Project fetchProject() {
        return psiFileContext.getProject();
    }

    public PsiClass fetchFilePsiClass() {
        return psiFileContext.getPsiClass();
    }

    public PsiFile fetchPsiFile() {
        return psiFileContext.getPsiFile();
    }

    public PsiClassWrapper.ClassRoleEnum fetchFilePsiClassRole() {
        return filePsiClassWrapper.getClassRole();
    }

    public void addClassWrapper(PsiClassWrapper psiClassWrapper) {
        if (clzList == null) {
            clzList = Lists.newArrayList(psiClassWrapper);
        } else {
            clzList.add(psiClassWrapper);
        }
    }

    @Getter
    @Setter
    public static class PsiFileContext {

        private Project project;

        private PsiFile psiFile;

        private PsiClass psiClass;

        private PsiElementFactory elementFactory;


    }

    @Getter
    @Setter
    public static class SelectWrapper {

        private boolean selected;

        private PsiElement currentElement;

        private PsiClass clz;

        private PsiFieldWrapper field;

        private PsiMethodWrapper method;

        /**
         * 被手动选中的类
         */
        private PsiClassWrapper selectedPsiClassWrapper;

    }

}
