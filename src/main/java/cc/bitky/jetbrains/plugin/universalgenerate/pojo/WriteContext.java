package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsState;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

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

    private List<PsiClassWrapper> clzList = Lists.newArrayList();

    public Project fetchProject() {
        return psiFileContext.getProject();
    }

    public PsiClass fetchFilePsiClass() {
        return psiFileContext.getPsiClass();
    }

    public PsiFile fetchPsiFile() {
        return psiFileContext.getPsiFile();
    }

    public boolean fetchSelected() {
        if (selectWrapper == null) {
            return false;
        }
        return selectWrapper.isSelected();
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

        private GlobalSettingsState.LanguageEnum language;

        public boolean valid() {
            return project != null
                    && psiFile != null
                    && psiClass != null
                    && elementFactory != null
                    && language != null
                    ;
        }

    }

    @Getter
    @Setter
    public static class SelectWrapper {

        private boolean selected;

        @Nullable
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
