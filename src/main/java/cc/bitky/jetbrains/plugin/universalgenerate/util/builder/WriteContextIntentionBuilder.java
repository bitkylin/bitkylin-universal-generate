package cc.bitky.jetbrains.plugin.universalgenerate.util.builder;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.SelectWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.WriteContextUtils.assembleUniversalClassInfo;

/**
 * @author bitkylin
 */
public final class WriteContextIntentionBuilder {

    private WriteContextIntentionBuilder() {
    }

    public static WriteContext create(Project project, Editor editor, PsiElement element) {

        WriteContext writeContext = new WriteContext();
        WriteContext.PsiFileContext psiFileContext = new WriteContext.PsiFileContext();
        writeContext.setPsiFileContext(psiFileContext);

        psiFileContext.setLanguage(GlobalSettingsStateHelper.getInstance().getLanguage());
        psiFileContext.setProject(project);
        psiFileContext.setElementFactory(JavaPsiFacade.getElementFactory(project));
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (psiFile == null) {
            return writeContext;
        }
        psiFileContext.setPsiFile(psiFile);

        PsiClass psiClass = PsiTreeUtil.findChildOfAnyType(psiFile, PsiClass.class);
        if (psiClass == null) {
            return writeContext;
        }
        psiFileContext.setPsiClass(psiClass);

        SelectWrapper selectWrapper = new SelectWrapper();
        selectWrapper.setCurrentElement(element);
        writeContext.setSelectWrapper(selectWrapper);

        assembleUniversalClassInfo(0, psiClass, writeContext);

        return writeContext;
    }

}
