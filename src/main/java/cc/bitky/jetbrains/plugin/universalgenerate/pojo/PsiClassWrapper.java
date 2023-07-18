package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import com.intellij.psi.PsiClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bitkylin
 */
@Getter
@Setter
public class PsiClassWrapper {

    private boolean controller;

    private PsiClass psiClass;

    private ClassLocationEnum classLocation;

    private ClassRoleEnum classRole;

    private List<PsiFieldWrapper> fieldList;

    private List<PsiMethodWrapper> methodList;

    private List<PsiClassWrapper> innerClassList;

    public enum ClassLocationEnum {

        /**
         * 接口层入口类，一般需要添加 swagger、protostuff 注解
         */
        INTERFACE_ENTRANCE,

        /**
         * 服务层入口类，一般需要添加 JavaDoc 注释
         */
        SERVICE_ENTRANCE,

        ;

    }

    public enum ClassRoleEnum {

        /**
         * controller
         */
        CONTROLLER,

        /**
         * 一般服务类
         */
        SERVICE,

        /**
         * pojo
         */
        POJO,
        ;

    }

}
