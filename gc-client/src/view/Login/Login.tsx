import { Card, Button, Form } from "react-bootstrap";
import "../style.css";
import { useState } from "react";
import { useNavigate } from "react-router";
import { loginUser, getUserData } from "../../services/UserService";
import { useDispatch } from "react-redux";
import { login, updateProfile } from "../../redux/userSlice";

const Login = () => {
    const [invalid, setInvalid] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [invalidCreds, setInvalidCreds] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleUsernameChange = (e: any) => {
        setUsername(e.target.value);
        setInvalidCreds(false);
    };

    const handlePasswordChange = (e: any) => {
        setPassword(e.target.value);
        setInvalidCreds(false);
    };

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            const data = { username: username, password: password };
            const response = await loginUser(data);
            let token;
            if (response?.data) {
                token = response.data.token;
                dispatch(
                    login({
                        token: token
                    })
                )

                localStorage.setItem('token', token);
                
                const user_data = await getUserData(data); //response from get request

                //if user successfully logs in, dispatch updateProfile action to set state of currently logged in user with currently logged in users' data.
                //need add these too based on github API ->  private int followers; private int stars; private int following;
                dispatch(
                    updateProfile({
                        userData: {username : user_data.data["username"], organization:  user_data.data["organization"], 
                                   location: user_data.data["location"], description: user_data.data["description"], 
                                   linkedin: user_data.data["linkedin"], 
                                   github: user_data.data["github"], resume: user_data.data["resume"], profilePicture: user_data.data["profilePicture"]}
                    })
                )

                if(user_data.data["profilePicture"] === undefined || user_data.data["profilePicture"] === null ){
                    dispatch(
                        updateProfile({
                            userData: {username : user_data.data["username"], organization:  user_data.data["organization"], 
                                       location: user_data.data["location"], description: user_data.data["description"], 
                                       linkedin: user_data.data["linkedin"], 
                                       github: user_data.data["github"], resume: user_data.data["resume"], 
                                       profilePicture: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"}
                        })
                    )
                }

            
                
                navigate('/dashboard');
            } else {
                event.stopPropagation();
                setInvalidCreds(true);
            }
        } else {
            event.stopPropagation();
            setInvalid(true);
        }
    };

    return (
        <Card className="auth-card">
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
                    {invalidCreds &&
                        <Form.Group className="mt-1" controlId="invalidCreds">
                            <Form.Label color="red">Invalid username or password.</Form.Label>
                        </Form.Group>
                    }
                    <Button variant="dark" type="submit">Sign In</Button>
                    <Form.Group className="mt-2" controlId="forgotPassword">
                        <Form.Label><a href="/forgot-password">Forgot password?</a></Form.Label>
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