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
                    console.log('실패')
                    console.log(error.response)
                    return false;
                });
                break;
        }
    
    }
    
    static checkAuthorization(accessToken){
        // const header = {Authentication: `${accessToken}`}
        const header = {Authentication: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsInVzZXJuYW1lIjoiMTIzNCIsImlhdCI6MTcyMjg0NTc2NSwiZXhwIjoxNzIyODQ1NzY1fQ.PS8DKyOAEbOyY94hOocd6BBEdsLSPsqHOGByD8xSFpk`}

        console.log("전송")
        this.sendRequest("/user/check", "GET", {header:header})
    }
}


export default RestApiHelper