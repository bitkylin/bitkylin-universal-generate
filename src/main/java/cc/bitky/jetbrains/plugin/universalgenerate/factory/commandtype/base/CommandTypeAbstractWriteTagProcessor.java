package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsState;
import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsStateHelper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.AnnotationTagUtils;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.bitky.jetbrains.plugin.universalgenerate.util.AnnotationTagUtils.calcCurrentGroupBeginNum;

/**
 * @author bitkylin
 */
public abstract class CommandTypeAbstractWriteTagProcessor extends AbstractCommandTypeProcessor {

    protected WriteContext writeContext;

    /**
     * 初始值
     */
    protected int beginNumValue;

    /**
     * 步长
     */
    protected int stepNumValue;

    protected AtomicInteger initAvailableBeginNum(int groupTagNum, Set<Integer> tagExistedSet) {
        int groupBeginNum = calcCurrentGroupBeginNum(beginNumValue, stepNumValue, groupTagNum);

        AtomicInteger currentNum = new AtomicInteger(groupBeginNum);
        updateCurrentNum(currentNum, beginNumValue, tagExistedSet);
        return currentNum;
    }

    protected void updateCurrentNum(AtomicInteger currentNum, Integer psiFieldTagValue, Set<Integer> tagExistedSet) {
        currentNum.set(Math.max(currentNum.get(), psiFieldTagValue));
        while (tagExistedSet.contains(currentNum.get())) {
            currentNum.incrementAndGet();
        }
    }

    protected int calcNextGroupBeginNum(int beginNum, int step, int currentNum) {
        GlobalSettingsState.ProtostuffTagAssignEnum tagAssignEnum = GlobalSettingsStateHelper.getInstance().getProtostuffTagAssign();
        if (GlobalSettingsState.ProtostuffTagAssignEnum.FROM_START_VALUE == tagAssignEnum) {
            return beginNum;
        }
        return AnnotationTagUtils.calcNextGroupBeginNum(beginNum, step, currentNum);
    }

    protected boolean canWriteAnnotationTag(PsiClassWrapper psiClassWrapper) {
        if (psiClassWrapper.getClassRole() != PsiClassWrapper.ClassRoleEnum.POJO) {
            return false;
        }
//        if (psiClassWrapper.getClassLocation() != PsiClassWrapper.ClassLocationEnum.INTERFACE_ENTRANCE) {
//            return false;
//        }
        if (psiClassWrapper.getPsiClass().isEnum()) {
            return false;
        }
        return true;
    }
}
