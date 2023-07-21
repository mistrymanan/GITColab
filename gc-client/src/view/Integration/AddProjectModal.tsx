import React, { useState } from 'react';
import { Modal, Button, Form, Dropdown } from 'react-bootstrap';

interface AddProjectModalProps {
    projectDetails: ProjectDetails;
    handleClose: () => void;
}

interface ProjectDetails {
    repositoryName: string;
    isAtlassian: boolean;
    jiraBoard?: string;
}

const AdddProjectModal: React.FC<AddProjectModalProps> = ({
    projectDetails,
    handleClose,
}) => {
    const [repositoryName, setRepositoryName] = useState("");
    const [isAtlassian, setIsAtlassian] = useState(false);
    const [jiraBoard, setJiraBoard] = useState("");
    const [invalid, setInvalid] = useState(false);

    const handRepositoryNameChange = (e: any) => {
        setRepositoryName(e.target.value);
    }

    const handleIsAtlassianChange = () => {
        setIsAtlassian(!isAtlassian);
    }

    const handleJiraBoardChange = (e: any) => {
        setJiraBoard(e.target.value);
    }

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            const data = { repositoryName: repositoryName, isAtlassian: isAtlassian, jiraBoard: jiraBoard };
            if (data) {
                console.log("working");
                handleClose();
            }
        } else {
            event.stopPropagation();
            setInvalid(true);
        }
    };

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Project Flow</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={invalid} onSubmit={handleSubmit}>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="repositoryName"
                            type="text"
                            placeholder="Repository Name"
                            required
                            value={repositoryName}
                            onChange={handRepositoryNameChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid repository name.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingInputCustom">Repository Name</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Check
                            type={"checkbox"}
                            id={'addAtlassian'}
                            label={'Want to integrate Atlasssian(JIRA, Confluence)?'}
                            onChange={handleIsAtlassianChange}
                        />
                    </Form.Floating>
                    {isAtlassian && (
                        <Form.Floating className="mb-3">
                            <Form.Control
                                id="jiraBoard"
                                type="text"
                                placeholder="JIRA Board Name"
                                value={jiraBoard}
                                onChange={handleJiraBoardChange}
                                required={isAtlassian}
                            />
                            <Form.Control.Feedback type="invalid">
                                Please enter valid JIRA board name.
                            </Form.Control.Feedback>
                            <label htmlFor="floatingInputCustom">JIRA Board Name</label>
                        </Form.Floating>
                    )}
                    <Button variant="primary" type="submit">
                        Add Project
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default AdddProjectModal;
