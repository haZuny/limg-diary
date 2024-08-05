import axios from "axios"

class RestApiHelper{
    static sendRequest(uri, method, {header, param, body}){
        let host = "http://localhost:8080"
       
        switch (method){
            case "GET":
                axios.get(host+uri, {
                    headers: header,
                    params: param,
                    body: body
                }).then(response =>{
                    return true;

                }).catch(error => {
                    return false;
                });
                break;
        }
    
    }
    
    static checkAuthorization(accessToken){
        const header = {Authentication: accessToken};
        const res = this.sendRequest("/user/check", "GET", {header:header});
        return res;
    }
}


export default RestApiHelper