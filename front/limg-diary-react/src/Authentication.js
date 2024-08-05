import axios from "axios"

class RestApiHelper{

    static async sendRequest(uri, method, {header = {}, param = {}, body = {}}){
        let host = "http://localhost:8080"

        // set content type
        header["Content-Type"] = "application/json";
        header["authentication"] = localStorage.getItem("Authentication");
       
        try{
            switch (method){
                case "GET":
                    return await axios.get(host+uri, {
                        withCredentials: true,
                        headers: header,
                        params: param,
                    })
                    break;
                case "POST":
                    return await axios.post(host+uri, body, {
                        withCredentials: true,
                        headers: header,
                        params: param,
                    })
                    break;
                
            }
        } 
        catch(err){
            const response = err.response;

            // Refresh
            if (response != null && response.status == "401"){
                // re auth
                axios.post(host + "/user/refresh", null, {
                    withCredentials: true,
                    headers: header,
                    params: param,
                }).then(async (res)=>{

                    // set auth
                    localStorage.setItem("Authentication", res.headers['authentication'])
                    header["authentication"] = localStorage.getItem("Authentication");
                    
                    console.log("리트")
                    // re-request
                    switch (method){
                        case "GET":
                            return await axios.get(host+uri, {
                                withCredentials: true,
                                headers: header,
                                params: param,
                            })
                            console.log("리트 결과")
                            break;
                        case "POST":
                            return await axios.post(host+uri, body, {
                                withCredentials: true,
                                headers: header,
                                params: param,
                            })
                            break;
                    }
                })
            }
            return response;
        }
        
    
    }
    
    static async checkAuthorization(accessToken){

        const res = await this.sendRequest("/user/check", "GET", {});
        
        if (res != null && res.status == "200"){
            return true
        }
        else{
            return false;
        }
    }
}


export default RestApiHelper