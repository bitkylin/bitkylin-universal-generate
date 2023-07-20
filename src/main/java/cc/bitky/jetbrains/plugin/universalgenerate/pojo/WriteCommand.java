package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class WriteCommand {

    private Scope scope;

    private Set<Command> commandSet;

    public enum SwaggerOption{

    }

    public enum Command {

        WRITE_SWAGGER,

        WRITE_TAG,
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
