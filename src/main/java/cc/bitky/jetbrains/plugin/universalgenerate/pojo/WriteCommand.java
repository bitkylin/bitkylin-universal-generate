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

        RE_GENERATE_WRITE_SWAGGER,
        POPULATE_WRITE_SWAGGER,

        RE_GENERATE_WRITE_TAG,
        POPULATE_WRITE_TAG,

        RE_GENERATE_SWAGGER_TO_JAVA_DOC,
        POPULATE_SWAGGER_TO_JAVA_DOC,
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
