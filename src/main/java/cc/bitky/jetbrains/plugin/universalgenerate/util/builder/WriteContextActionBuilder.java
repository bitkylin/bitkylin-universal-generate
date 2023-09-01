package cc.bitky.jetbrains.plugin.universalgenerate.util.builder;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.SelectWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
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
public final class WriteContextActionBuilder {

    private final AnActionEvent anActionEvent;

    private WriteContextActionBuilder(AnActionEvent anActionEvent) {
        this.anActionEvent = anActionEvent;
    }

    public static WriteContext create(AnActionEvent anActionEvent) {
        return new WriteContextActionBuilder(anActionEvent).innerCreate();
    }

    public WriteContext innerCreate() {

        WriteContext writeContext = new WriteContext();
        WriteContext.PsiFileContext psiFileContext = new WriteContext.PsiFileContext();
        writeContext.setPsiFileContext(psiFileContext);

        psiFileContext.setLanguage(GlobalSettingsStateHelper.getInstance().getLanguage());

        Project project = anActionEvent.getProject();
        if (project == null) {
            return writeContext;
        }
        psiFileContext.setProject(project);
        psiFileContext.setElementFactory(JavaPsiFacade.getElementFactory(project));

        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return writeContext;
        }

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

        // 通过光标偏移量获取当前psi元素
        PsiElement currentElement = psiFile.findElementAt(editor.getCaretModel().getOffset());

        SelectWrapper selectWrapper = new SelectWrapper();
        selectWrapper.setCurrentElement(currentElement);
        writeContext.setSelectWrapper(selectWrapper);

        assembleUniversalClassInfo(0, psiClass, writeContext);

        return writeContext;
    }

}
