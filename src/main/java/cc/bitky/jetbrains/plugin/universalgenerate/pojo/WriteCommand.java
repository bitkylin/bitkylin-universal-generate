package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class WriteCommand {

    private Scope scope;

    private Command command;

    public enum Command {

        RENEW_WRITE_SWAGGER,
        PADDING_WRITE_SWAGGER,

        RENEW_WRITE_TAG,
        PADDING_WRITE_TAG,

        MERGE_SWAGGER_TO_JAVA_DOC,
        PADDING_SWAGGER_TO_JAVA_DOC,
        ;

    }

    /**
     * 命令范围
     */
    public enum Scope {

        /**
         * 当前文件
         */
        FILE,

        /**
         * 当前类
         */
        CLASS,

        /**
         * 元素本身
         */
        ELEMENT,
        ;
    }
}
