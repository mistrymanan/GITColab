import { Container } from "react-bootstrap";
import {
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBCardTitle,
    MDBCardText,
    MDBCol
  } from 'mdbreact';

const Explore = () => {
    // Use Card
    return (
        <div>
            <Container>
                <MDBCol>
                    <MDBCard style={{ width: "22rem" }}>
                        <MDBCardBody>
                            <MDBCardTitle> 
                                Card Title
                            </MDBCardTitle>
                            <MDBCardText>
                                Card Textssssssssssss    
                            </MDBCardText>
                            <MDBBtn>
                                Card Button    
                            </MDBBtn>
                            <MDBCardImage src="src\images\image-1.jpg" />
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
            </Container>
        </div>
    )
}

export default Explore;