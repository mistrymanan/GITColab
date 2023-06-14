
import {Container, Col, Row} from 'react-bootstrap';

const Footer = () => {

    return (
        <footer>
            <Container fluid >
                <Row>
                    <Col className='text-center py-3' >
                        <hr style={{height:2, color:'black', backgroundColor:'black', borderColor : 'black',}} ></hr>
                        Github Collab Project<br/>CSCI 5308
                    </Col>
                </Row>
            </Container>
        </footer>
    )
}

export default Footer;