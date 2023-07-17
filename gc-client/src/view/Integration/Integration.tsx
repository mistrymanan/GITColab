import { Alert, Button } from "react-bootstrap";

const Integration = () => {
    const clientId = "098e442d98a100074b33";

    const handleGithubLogin = () => {
        window.location.assign("https://github.com/login/oauth/authorize?client_id=" + clientId);
    }

    return (
        <>
            <Alert key="warning" variant="warning">
                You are not authenticated with Github.
            </Alert>
            <Button variant="dark" type="button" onClick={handleGithubLogin}>Add Github</Button>
        </>

    )
}

export default Integration;