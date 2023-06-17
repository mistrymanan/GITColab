import projectMockData from '../../model/ProjectMockdata'
import {Container, Col, Row} from 'react-bootstrap';
import Project from '../../components/Project/Project';
import UserProfile from '../../components/UserProfile/UserProfile';
import profileMockData from '../../model/ProfileMockData';


const Profile = () => {
    return (
        <>
            <h1 className='py-3'>Projects</h1>
            <Container fluid >
            {/*Loop through the mockdata and display each on a card*/}
  
                <Row >
                    <div className="d-flex">
                        <div className="p-2">
                            
                        {projectMockData.map((projectData) => (
                            <Col sm = {24} md={16} lg={12} >
                                <Project projectData={projectData}/>
                            </Col>
                        ))}
                        
                        </div>

                        <div className="p-2 ">

                            <Col sm = {12} md={8} lg = {9} className='ms-auto'>
                                <UserProfile {...profileMockData} />
                            </Col>
                        </div>
                    </div>
                
                </Row>
                
            </Container>
        </>
    )
}

export default Profile;