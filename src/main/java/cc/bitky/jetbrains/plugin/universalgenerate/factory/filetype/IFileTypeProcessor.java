package cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import com.intellij.psi.PsiClass;

/**
 * @author bitkylin
 */
public interface IFileTypeProcessor {
    void doWrite(WriteContext writeContext);
}
