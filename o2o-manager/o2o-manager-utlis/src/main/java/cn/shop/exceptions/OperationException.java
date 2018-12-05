package cn.shop.exceptions;

import org.apache.ibatis.javassist.SerialVersionUID;

/**
 * @author zmt
 * @date 2018/11/20 - 21:36
 */
public class OperationException extends RuntimeException{
    public OperationException(String msg){
        super(msg);
    }
}
