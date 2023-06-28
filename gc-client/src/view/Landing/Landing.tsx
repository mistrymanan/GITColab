import { Container,Row, Col, Button } from 'react-bootstrap';
import background from '../../images/BG_IMG.png';
import { Link } from 'react-router-dom';

const Landing = () => {

    return (
        <Container style={{ backgroundImage: `url(${background})`, backgroundRepeat:"no-repeat", backgroundSize:"contain", height:1920, width:1080}} >

            <Row>
                <Col style={{paddingTop:'35em'}} className='d-flex justify-content-center'>

                    <Link to='/Login' relative='path'>
                        <Button variant="dark" style={{borderRadius:'10px', marginRight:'2em'}}>Sign In</Button >
                    </Link>

                    <Link to='/Registration' relative='path'>
                        <Button variant="dark" style={{borderRadius:'10px', marginLeft:'2em'}}>Register</Button >
                    </Link>
                </Col>
            </Row>
            
            <Row>
                <Col className='d-flex justify-content-center'>
                    <Link to='/ForgotPassword' relative='path'>
                        <Button variant="link" style={{color:'blue', marginTop:'2em'}}>Forgot Password?</Button>
                    </Link>
                </Col>    
            </Row>
            
        </Container>
    )
}

export default Landing;