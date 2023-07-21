import { Container } from "react-bootstrap";
import { Card } from "react-bootstrap"

const Explore = () => {
    return (
        <>
            <Container>
                <Card className="my-3 p-3 rounded " border="primary" style={{ width: '18rem' }}>
                    <Card.Img src="/src/images/image-1.jpg" variant="top" />

                    <Card.Body>
                        <Card.Title><h4>Repo Title</h4></Card.Title>
                        <Card.Text className="my-1"><h5>Repo Name - Repo Owner</h5></Card.Text>
                        <Card.Text as='p'>Repo Description</Card.Text>
                        <Card.Text as='p'>
                            5 <i className="fas fa-solid fa-star" />'s
                            5 <i className="fas fa-solid fa-code-branch" />'s
                            5 <i className="fas fa-solid fa-exclamation-circle" />'s
                        </Card.Text>
                    </Card.Body>
                </Card>
            </Container>
        </>
    )
}

export default Explore;