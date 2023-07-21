import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

interface AddContributorModalProps {
    contributorDetails: ContributorDetails;
    handleClose: () => void;
}

interface ContributorDetails {
    username: string;
}

const AddContributorModal: React.FC<AddContributorModalProps> = ({
    contributorDetails,
    handleClose,
}) => {
    const [username, setUsername] = useState("");
    const [invalid, setInvalid] = useState(false);

    const handleUsernameChange = (e: any) => {
        setUsername(e.target.value);
    }

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            const data = { username: username };
            if (data) {
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
                <Modal.Title>Add Contributor</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={invalid} onSubmit={handleSubmit}>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="username"
                            type="text"
                            placeholder="Username"
                            required
                            value={username}
                            onChange={handleUsernameChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid username.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingInputCustom">Github Username</label>
                    </Form.Floating>
                    <Button variant="primary" type="submit">
                        Grant Access
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default AddContributorModal;
