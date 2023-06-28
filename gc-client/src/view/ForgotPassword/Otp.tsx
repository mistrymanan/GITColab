import { Card, Button, Row, Col, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const Otp = () => {

    return (
        <>
            <Container className="d-flex justify-content-center  "> 
                <Card style={{width:'45em', position:'absolute'}} >

                    <Card.Header style={{backgroundColor:"black"}}>
                        <Row>
                            <Col className="d-flex justify-content-center " >
                                <h1 style={{color:'white'}}>One Time Password</h1>
                            </Col>
                        </Row>
                    </Card.Header>

                    <Card.Body>
                        {/*Validation Event Handlers*/}
                        <h4>Enter the Unique One Time Password that was sent to your email to reset your password.</h4>
                        <h1>Unique OTP Field</h1>
                        <Link to='/ResetPassword' relative='path'>
                                <Button variant="dark" style={{marginLeft:'2em'}}>Submit</Button>
                        </Link>
                    </Card.Body>


                </Card>
            </Container>
        </>
    )
}

export default Otp;