import {ElMessage} from "element-plus";

/**
 * 消息提示
 * @param msg
 * @param type
 */
export function messageTip(msg, type) {
    ElMessage({
        showClose: true, //消息提示是否可以关闭
        duration: 3000,// 显示时间，单位为毫秒 0为永不关闭
        message: msg, // 消息内容
        type: type, // 消息类型 'success' | 'warning' | 'info' | 'error'
    })
}


/**
 * 获取token名称(jwt的名字)
 * @returns {string}
 */
export function getTokenName(){
    return "dlyk_token";
}

