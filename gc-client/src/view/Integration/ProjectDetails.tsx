import { MDBDataTableV5 } from "mdbreact";
import { useState } from "react";
import { Button, Container, Table } from "react-bootstrap"
import AddContributorModal from "./AddContributorModal";

const ProjectDetails = () => {

    const [openAddContributorModal, setAddContributorModal] = useState(false);

    const handleModalClose = () => {
        setAddContributorModal(false);
    };

    const handleModalOpen = () => {
        setAddContributorModal(true);
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
            <Container className="mt-3">
                <div className="details">
                    <h1 className='py-3' >Project Flow Details</h1>
                    <div className="w-100">
                        <Table striped hover>
                            <tbody>
                                <tr>
                                    <td>Repository:</td>
                                    <td><a target="_blank" href="https://www.github.com" rel="noreferrer">{'Gitcolab'} <i className="fa-solid fa-up-right-from-square"></i></a></td>
                                </tr>
                                <tr>
                                    <td>JIRA Board:</td>
                                    <td><a target="_blank" href="https://www.github.com" rel="noreferrer">{'Gitcolab JIRA'} <i className="fa-solid fa-up-right-from-square"></i></a></td>
                                </tr>
                                <tr>
                                    <td>Owner:</td>
                                    <td>{'keyurkhant'}</td>
                                </tr>
                                <tr>
                                    <td>Total Participants:</td>
                                    <td>{'23'}</td>
                                </tr>
                            </tbody>
                        </Table>
                    </div>
                </div>
                <div>
                    <div className="d-flex flex-row my-2 justify-content-between py-3">
                        <h1>Contributors</h1>
                        <Button variant="dark" type="button" onClick={handleModalOpen}>Add Contributor</Button>
                    </div>
                    <MDBDataTableV5 data={{ rows, columns }} bordered hover></MDBDataTableV5>
                </div>
                {openAddContributorModal && (
                    <AddContributorModal
                        handleClose={handleModalClose} contributorDetails={{ username: "" }} />
                )}
            </Container>
        </>
    )
}

export default ProjectDetails;