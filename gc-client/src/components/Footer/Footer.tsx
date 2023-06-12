
import {Container, Col, Row} from 'react-bootstrap';

const Footer = () => {

    return (
        <footer>
            <Container fluid >
                <Row>
                    <Col className='text-center py-3' >
                        Github Collab Project
                    </Col>
                </Row>
            </Container>
        </footer>
    )
}

export default Footer;