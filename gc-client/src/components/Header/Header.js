import {Container, Navbar, Nav} from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';

const Header = () => {

    return (
        <header>
            <Navbar bg="dark" variant="dark" expand="lg" >
                <Container className=''>

                    <LinkContainer to='/'>
                        <Navbar.Brand >Github Collab</Navbar.Brand>
                    </LinkContainer>

                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>

                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className='ms-auto'>

                            <LinkContainer to='/dashboard'>
                                <Nav.Link>Dashboard</Nav.Link>
                            </LinkContainer>

                            <LinkContainer to='/integration'>
                                <Nav.Link>Integration</Nav.Link>
                            </LinkContainer>
                            
                            <LinkContainer to='/explore'>
                                <Nav.Link>Explore</Nav.Link>
                            </LinkContainer>
                        </Nav>

                        <Nav className='ms-auto'>
                            <LinkContainer to='/landing'>
                                <Nav.Link><i className="fas fa-sign-out"></i>Sign Out</Nav.Link>
                            </LinkContainer>

                            <LinkContainer to='/profile'>
                                <Nav.Link><i className="fas fa-user"></i> Profile</Nav.Link>
                            </LinkContainer>
                        </Nav>

                    </Navbar.Collapse>

                </Container>
            </Navbar>
        </header>
    )
}

export default Header;