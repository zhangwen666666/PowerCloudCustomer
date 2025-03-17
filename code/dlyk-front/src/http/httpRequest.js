import axios from "axios";
import {getTokenName, messageConfirm, messageTip} from "../util/util.js";
import {ElMessageBox} from "element-plus";

axios.defaults.baseURL = "http://localhost:8089"

export function doGet(url, params) {
    return axios({
        method: "get",
        url: url,
        params: params, // get请求参数的名字是params
        dataType: "json"
    })
}

export function doPost(url, data) {
    return axios({
        method: "post",
        url: url,
        data: data, // post请求参数的名字叫data
        dataType: "json"
    })
}

export function doPut(url, data) {
    return axios({
        method: "put",
        url: url,
        data: data, // put请求参数的名字叫data
        dataType: "json"
    })
}

export function doDel(url, params) {
    return axios({
        method: "delete",
        url: url,
        params: params, // delete请求参数的名字是params
        dataType: "json"
    })
}

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么, 在请求头中放一个token(jwt)，传给后端接口
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token){
        // token为空，说明是可能是存储在localStorage
        token = window.localStorage.getItem(getTokenName());
    }
    if (token){
        // token获取到了
        config.headers['Authorization'] = token;
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    if (response.data.code > 900){
        // code大于900说明token验证失败了
        // 前端响应的jwt有问题，清除这个jwt
        removeToken();
        messageConfirm(response.data.msg + ', 是否重新登录').then(() => { // 点击确定
            window.location.href = "/";
        }).catch(() => { // 点击取消
            messageTip("取消登录", "warning")
        })
        return ;
    }
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});


// 删除token
export function removeToken() {
    window.localStorage.removeItem(getTokenName())
    window.sessionStorage.removeItem(getTokenName())
}

export function getToken() {
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token){
        // token为空，说明是可能是存储在localStorage
        token = window.localStorage.getItem(getTokenName());
    }
    if (token){
        // token获取到了
        return token;
    }
    // 没拿到token，则跳转到登录页面
    messageTip("登录状态异常，请重新登录", 'error');
    removeToken();
    window.location.href = "/";
}