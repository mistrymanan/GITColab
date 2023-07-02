import { Card, Button, Form } from "react-bootstrap";
import "./registration.css";

const Registration = () => {
    return (
        <Card className="register-card">
            <Card.Header><h1>Registration</h1></Card.Header>
            <Card.Body>
                <Form.Floating className="mb-3">
                    <Form.Control
                        id="firstname"
                        type="text"
                        placeholder="First Name"
                    />
                    <label htmlFor="firstname">First name</label>
                </Form.Floating>
                <Form.Floating className="mb-3">
                    <Form.Control
                        id="lastname"
                        type="text"
                        placeholder="Last name"
                    />
                    <label htmlFor="lastname">Last name</label>
                </Form.Floating>
                <Form.Floating className="mb-3">
                    <Form.Control
                        id="email"
                        type="email"
                        placeholder="name@example.com"
                    />
                    <label htmlFor="email">Email address</label>
                </Form.Floating>
                <Form.Floating className="mb-3">
                    <Form.Control
                        id="password"
                        type="password"
                        placeholder="Password"
                    />
                    <label htmlFor="password">Password</label>
                </Form.Floating>
                <Button variant="dark">Sign up</Button>
            </Card.Body>
        </Card>
    )
}

export default Registration;