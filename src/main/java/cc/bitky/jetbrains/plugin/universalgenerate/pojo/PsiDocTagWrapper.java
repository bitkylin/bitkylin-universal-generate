package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bitkylin
 */
@Data
public class PsiDocTagWrapper {

    private String elementName;

    private String elementDesc;

    private String fullComment;

    public String fetchFullComment() {
        if (paramIsValid()) {
            return StringUtils.trimToEmpty("@param " + elementName + " " + elementDesc);
        }
        return StringUtils.trimToEmpty(fullComment);
    }

    /**
     * 是一个有效的param tag，也就是说elementName和elementDesc都不为空，例如下面的标准格式：
     * '@param elementName elementDesc'
     */
    public boolean paramIsValid() {
        return StringUtils.isNotBlank(elementName) && StringUtils.isNotBlank(elementDesc);
    }
}
