import axios from "axios";

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