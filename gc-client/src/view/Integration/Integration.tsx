import { useState } from "react";
import { Button, Container } from "react-bootstrap";
import { MDBDataTableV5 } from 'mdbreact';
import AdddProjectModal from "./AddProjectModal";

const Integration = () => {
    const [authenticated, setAuthenticated] = useState(true);
    const [openAddProjectModal, setOpenAddProjectModal] = useState(false);
    const clientId = "098e442d98a100074b33";   // TODO: handle env

    const handleGithubLogin = () => {
        window.location.assign("https://github.com/login/oauth/authorize?client_id=" + clientId + "&scope=public_repo");
    }

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

    const rows: any[] = [
        { projectName: "Gitcolab" },
        { projectName: "Text Classification" },
        { projectName: "Module Federation" },
    ]

    return (
        <>
            {authenticated ? (
                <Container className="mt-3">
                    <div className="d-flex flex-row my-2 justify-content-between">
                        <h1 className='py-3' >Projects</h1>
                        <Button variant="dark" type="button" onClick={addProjectFlowHandler}>Add New Project Flow</Button>
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