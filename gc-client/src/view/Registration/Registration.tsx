import { Card, Button, Row, Col, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const Registration = () => {

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
                                <Link to='/Login' relative='path'>
                                    <Button variant="dark" style={{marginRight:'2em'}}>Sign In</Button>
                                </Link>
                                <div style={{width:'2px', backgroundColor:'white', height:'2em', minHeight:'100%'}}></div>
                                <Link to='/Registration' relative='path'>
                                    <Button variant="dark" style={{marginLeft:'2em'}}>Register</Button>
                                </Link>
                            </Col>
                        </Row>
                    
                    </Card.Header>

                    <Card.Body>
                        <h1>First Name</h1>
                        <h1>Last Name</h1>
                        <h1>Email</h1>
                        <h1>Password</h1>
                    </Card.Body>

                    <Card.Footer>

                        <Link to='/ForgotPassword' relative='path'>
                            <Button variant="link" style={{color:"blue"}}>Forgot Password?</Button>
                        </Link>



                    </Card.Footer>
                </Card>
            </Container>
        </>
    )
}

export default Registration;