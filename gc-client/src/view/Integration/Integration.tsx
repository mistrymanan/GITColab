import { useEffect, useState } from "react";
import { Button, Container } from "react-bootstrap";
import { MDBDataTableV5 } from 'mdbreact';
import AdddProjectModal from "./AddProjectModal";
import { useNavigate } from "react-router";
import { GITHUB_CLIENT_ID, GITHUB_SCOPE } from "../../credentials";
import { useDispatch, useSelector } from "react-redux";
import { login, selectUser } from "../../redux/userSlice";
import { getGithubAccessToken } from "../../services/GithubService";

const Integration = () => {
    const [authenticated, setAuthenticated] = useState(false);
    const [githubAuthenticated, setGithubAuthenticated] = useState("");
    const [openAddProjectModal, setOpenAddProjectModal] = useState(false);
    // const clientId = "098e442d98a100074b33";   // TODO: handle env
    const clientId = GITHUB_CLIENT_ID;
    const scope = GITHUB_SCOPE;
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const userDataStore = useSelector(selectUser);

    const handleGithubLogin = () => {
        window.location.assign(`https://github.com/login/oauth/authorize?client_id=${clientId}&scope=${scope}`);
    }

    useEffect(() => {
        if (githubAuthenticated !== "") {
            const fetchData = async () => {
                const data = {
                    "email": "parthmehta2016@gmail.com",
                    "code": "685f0b0404159829e9a1"
                };
                const response = await getGithubAccessToken(data, userDataStore.token);
                console.log("HERE WORK===>", response);
            }

            fetchData();

            // TODO: get accesstoken
            // console.log("HERE+===>", userDataStore);
            // dispatch(
            //     login({
            //         ...userDataStore,
            //         github: {
            //             code: githubAuthenticated,
            //             token: null
            //         }
            //     })
            // )
            // console.log("BEFORE===>", userDataStore);
            // navigate('/integration');
            // console.log("AFTER===>", userDataStore);
        }
        const url = new URL(window.location.href);
        const githubCode = url.search.replace("?code=", "");
        console.log("GITHUB TOKEN====>", githubCode);
        if (githubCode) {
            setGithubAuthenticated(githubCode);
        }
    }, [githubAuthenticated, userDataStore.token]);

    const handleModalClose = () => {
        setOpenAddProjectModal(false);
    };

    const addProjectFlowHandler = () => {
        setOpenAddProjectModal(true);
    }

    const columns = [
        {
            label: 'Project Name',
            field: 'projectName',
        }
    ]

    const handleClick = (id: any) => {
        navigate(`/integration/${id}`);
    }

    const rows: any[] = [
        { projectName: "Gitcolab", clickEvent: () => handleClick(1) },
        { projectName: "Text Classification", clickEvent: () => handleClick(2) },
        { projectName: "Module Federation", clickEvent: () => handleClick(3) },
    ]

    return (
        <>
            {authenticated ? (
                <Container className="mt-3">
                    <div className="d-flex flex-row my-2 justify-content-between py-3">
                        <h1>Projects</h1>
                        <Button variant="dark" type="button" onClick={addProjectFlowHandler}>Add Project Flow</Button>
                    </div>
                    <MDBDataTableV5 data={{ rows, columns }} bordered hover></MDBDataTableV5>
                    {openAddProjectModal && (
                        <AdddProjectModal
                            handleClose={handleModalClose} projectDetails={{ repositoryName: "", isAtlassian: false, jiraBoard: "" }} />
                    )}
                </Container>
            ) : (
                <div style={{ display: "flex", alignItems: "center", justifyContent: "center", minHeight: "calc(100vh - 88px)", flexDirection: "column" }}>
                    <h4>You are not authenticated with github and atlassian.</h4>
                    <div className="d-flex">
                        <Button variant="dark" type="button" onClick={handleGithubLogin} className="mx-2">Add Github</Button>
                        <Button variant="dark" type="button" onClick={handleGithubLogin} className="mx-2">Add Atlassian</Button>
                    </div>
                </div>
            )}
        </>

    )
}

export default Integration;
