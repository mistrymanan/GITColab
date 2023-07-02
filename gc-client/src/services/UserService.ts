import { postData } from "./utils";

export async function loginUser(userData: any) {
    if(userData && ( userData.username == null || userData.password == null )) return null;
    const data: any = {};
    data.username = userData.username;
    data.password = userData.password;
    const { token } = await postData(data, "/auth/login");
    return token ? token : null;
}