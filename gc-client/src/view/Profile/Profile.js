import projectMockData from '../../model/ProjectMockdata'
import {Container, Col, Row} from 'react-bootstrap';
import Project from '../../components/Project/Project';
import UserProfile from '../../components/UserProfile/UserProfile';
import profileMockData from '../../model/ProfileMockData';

//sm = {12} md={6} lg = {4}
const Profile = () => {
    return (
        <>
            <h1 className='py-3'>Projects</h1>
            <Container fluid = "sm">
            {/*Loop through the mockdata and display each on a card*/}
                <Row >
                    {projectMockData.map((projectData) => (
                        <Col sm = {6} md={3} lg = {2}>
                            <Project projectData={projectData}/>
                        </Col>
                    ))}

                
                    <Col sm = {12} md={6} lg = {4} className='ms-auto'>
                        <UserProfile {...profileMockData}/>
                    </Col>
                </Row>
            </Container>

       

     
        </>
    )
}

export default Profile;