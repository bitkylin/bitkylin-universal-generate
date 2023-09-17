package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * @author limingliang
 */
@Getter
@Setter
public class SelectWrapper {

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

