import projectMockData from '../../model/ProjectMockdata'
import {Container, Col, Row} from 'react-bootstrap';
import Project from '../../components/Project/Project';
import UserProfile from '../../components/UserProfile/UserProfile';
import profileMockData from '../../model/ProfileMockData';
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

//sm = {12} md={6} lg = {4}

const Profile = () => {
    return (
        <>  
        <Header/>
            {/*Fix UI later*/}
            <Container fluid >
                <h1 className='py-3' >Projects</h1>
            
  
                <Row >
                    <div className="d-flex">
                        <div className="">

                    {/*Loop through the mockdata and display each on a card*/}
                        {projectMockData.map((projectData) => (
                            <Col sm = {16} md={10} lg={6} >
                                <Project projectData={projectData}/>
                            </Col>
                        ))}
                        
                        </div>

                        <div>

                            <Col sm = {12} md={8} lg = {9} >
                                <UserProfile {...profileMockData} />
                            </Col>
                        </div>
                    </div>
                
                </Row>
                
            </Container>
        <Footer/>
        </>
    )
}

export default Profile;