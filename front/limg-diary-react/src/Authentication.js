import axios from "axios"

class RestApiHelper {

    static async sendRequest(uri, method, { header = {}, param = {}, body = {} }) {
        // let host = "http://192.168.1.13:8080"
        const host = "http://localhost:8080"

        // set content type
        header["Content-Type"] = "application/json";
        header["authentication"] = localStorage.getItem("Authentication");
        header["Access-Control-Allow-Origin"] = '*';
        // header["Access-Control-Allow-Credentials"] = 'true'

        return await axios({
            method: method,
            url: host + uri,
            params: param,
            headers: header,
            data: body,
            withCredentials: true
        }).then((res) => {
            return res;
        }).catch(async (err) => {
            // refresh
            if (err.response != null && err.response.status == '401') {
                return await axios({
                    method: 'POST',
                    url: host + '/user/refresh',
                    headers: header,
                    withCredentials: true
                }).then(async (res) => {
                    console.log("[post] user/refresh", res)
                    // set header
                    localStorage.setItem("Authentication", res.headers['authentication']);
                    header["authentication"] = localStorage.getItem("Authentication");

                    // re-request
                    return await axios({
                        method: method,
                        url: host + uri,
                        params: param,
                        headers: header,
                        data: body,
                        withCredentials: true
                    }).then((res) => {
                        return res;
                    }).catch((err) => {
                        return err;
                    })
                }).catch((err) => {
                    console.log("[post] user/refresh", err)
                    return err;
                })
            }
            return err;
        })
    }



    static async checkAuthorization(accessToken) {

        const res = await this.sendRequest("/user/check", "GET", {});
        console.log("[get] user/check", res)
        if (res != null && res.status == "200") return true
            else    return false;
    }
}


export default RestApiHelper