import { Card, Button, Row, Col, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const ForgotPassword = () => {

    return (
        <>
            <Link to='/Landing' relative='path' style={{padding:'5em'}}>
                <i className="fas fa-sharp fa-regular fa-arrow-left fa-2xl" ></i>
            </Link>
            
            <Container className="d-flex justify-content-center  "> 
                <Card style={{width:'45em', position:'absolute'}} >

                    <Card.Header style={{backgroundColor:"black"}}>
                        <Row>
                            <Col className="d-flex justify-content-center " >
                                <h1 style={{color:'white'}}>Forgot Password?</h1>
                            </Col>
                        </Row>
                    </Card.Header>

                    <Card.Body>
                        <h4>Enter the email address associated with your account and we will send a link to reset your password.</h4>
                        <h1>Email</h1>
                        <Link to='/ResetPassword' relative='path'>
                                <Button variant="dark" style={{marginLeft:'2em'}}>Submit</Button>
                        </Link>
                    </Card.Body>


                </Card>
            </Container>
        </>
    )
}

export default ForgotPassword;