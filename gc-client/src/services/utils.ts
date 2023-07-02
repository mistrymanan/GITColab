import axios from "axios";

const API_URL = "http://localhost:8080/api";

export async function postData(body: string, url: string, token?: string) {
    try {
        var myHeaders: any = {};
        myHeaders["Content-Type"] = "application/json";
        myHeaders["Accept"] = "application/json";
        if (token) myHeaders["authorization"] = `Bearer ${token}`;

        var requestOptions = {
            headers: myHeaders
        };

        const response = await axios.post(API_URL + url, body, requestOptions);
        return response.data;
    } catch (error: any) {
        console.log("error", error.response.data)
    }
}