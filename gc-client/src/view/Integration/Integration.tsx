import { useState } from "react";
import { Alert, Button, Container } from "react-bootstrap";
import { MDBDataTableV5 } from 'mdbreact';

const Integration = () => {
    const [authenticated, setAuthenticated] = useState(true);
    const clientId = "098e442d98a100074b33";   // TODO: handle env

    const handleGithubLogin = () => {
        window.location.assign("https://github.com/login/oauth/authorize?client_id=" + clientId + "&scope=public_repo");
    }

    const columns = [
        {
            label: 'Project Name',
            field: 'projectName',
        }
    ]

    const rows = [
        { projectName: "Gitcolab" },
        { projectName: "Text Classification" },
        { projectName: "Module Federation" },
    ]

    return (
        <>
            {authenticated ? (
                <Container>
                    <MDBDataTableV5 data={{ rows, columns }} bordered hover></MDBDataTableV5>
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