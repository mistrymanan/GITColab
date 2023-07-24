import { postData } from "./utils";

export async function createProject(projectData: any, token: string) {
    if(!projectData) return null;
    const response = await postData(projectData, "/project/createProject", token);
    return response;
}