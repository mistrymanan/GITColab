import { postData } from "./utils";

export async function getGithubAccessToken(githubData: any, token: string) {
    if(!githubData) return null;
    const response = await postData(githubData, "/github/getAccessToken", token);
    return response;
}