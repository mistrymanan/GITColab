import { Card, Button, Row, Col, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const ResetPassword = () => {

    return (
        <>
            
            <Container className="d-flex justify-content-center  "> 
                <Card style={{width:'45em', position:'absolute'}} >

                    <Card.Header style={{backgroundColor:"black"}}>

                        <Row>
                            <Col className="d-flex justify-content-center " >
                                <h1 style={{color:'white'}}>Reset Password</h1>
                            </Col>
                        </Row>
                    
                    </Card.Header>

                    <Card.Body>
                        <h1>New Password</h1>
                        <h1>Confirm Password</h1>
                        <Link to='/Landing' relative='path'>
                            <Button variant="dark" style={{marginLeft:'2em'}}>Submit</Button>
                        </Link>
                    </Card.Body>


                </Card>
            </Container>
        </>
    )
}

export default ResetPassword;