package cn.shop.enums;

/**
 * @author 赵铭涛
 * @creation time 2018/11/29 - 15:57
 */
public enum  O2oEnum {
    OFFLINE(-1, "非法操作"), SUCCESS(0, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(
            -1002, "信息为空");

    private int state;

    private String stateInfo;

    private O2oEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static O2oEnum stateOf(int index) {
        for (O2oEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
