import {Container, Navbar, Nav} from 'react-bootstrap';
const Header = () => {

    return (
        <header>
            <Navbar bg="dark" variant="dark" expand="lg" >
                <Container className=''>
                    
                    <Navbar.Brand href="/">Github Collab</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>

                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className='ms-auto'>

                            <Nav.Link href="/dashboard">Dashboard</Nav.Link>
                            <Nav.Link href="/integration">Integration</Nav.Link>
                            <Nav.Link href="/explore">Explore</Nav.Link>


                        </Nav>

                        <Nav className='ms-auto'>
                            <Nav.Link href="/landing"><i className="fas fa-sign-out"></i>Sign Out</Nav.Link>
                            <Nav.Link href="/profile"><i className="fas fa-user"></i> Profile</Nav.Link>
                        </Nav>


                    </Navbar.Collapse>

                </Container>
            </Navbar>
        </header>
    )
}

export default Header;