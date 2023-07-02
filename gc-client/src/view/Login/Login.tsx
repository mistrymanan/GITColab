import { Card, Button, Form } from "react-bootstrap";
import "./login.css";
import { useState } from "react";
import { useNavigate } from "react-router";
import { loginUser } from "../../services/UserService";
import { useDispatch } from "react-redux";
import { login } from "../../redux/userSlice";

const Login = () => {
    const [invalid, setInvalid] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleUsernameChange = (e: any) => {
        setUsername(e.target.value);
    };

    const handlePasswordChange = (e: any) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            const data = {username: username, password: password};
            const token = await loginUser(data);
            if(token != null) {
                dispatch(
                    login({
                        token: token
                    })
                )
                localStorage.setItem('token', token);
                navigate('/dashboard');
            }
        } else {            
            event.stopPropagation();
            setInvalid(true);
        }
    };

    return (
        <Card className="login-card">
            <Card.Header><h1>Login</h1></Card.Header>
            <Card.Body>
                <Form noValidate validated={invalid} onSubmit={handleSubmit}>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="floatingInputCustom"
                            type="text"
                            placeholder="name@example.com"
                            required
                            value={username}
                            onChange={handleUsernameChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid username.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingInputCustom">Username</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="floatingPasswordCustom"
                            type="password"
                            placeholder="Password"
                            required
                            value={password}
                            onChange={handlePasswordChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid password.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingPasswordCustom">Password</label>
                    </Form.Floating>
                    <Button variant="dark" type="submit">Sign In</Button>
                    <Form.Group className="mt-2" controlId="forgotPassword">
                        <Form.Label><a href="/reset-password">Forgot password?</a></Form.Label>
                    </Form.Group>
                    <Form.Group className="" controlId="register">
                        <Form.Label><a href="/register">New user? Signup here first.</a></Form.Label>
                    </Form.Group>
                </Form>
            </Card.Body>
        </Card>
    )
}

export default Login;